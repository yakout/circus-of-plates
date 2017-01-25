package controllers.input.keyboard;

import controllers.input.Input;
import controllers.input.InputAction;
import controllers.input.InputType;
import controllers.input.UserAction;
import controllers.input.joystick.JoystickEvent;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ahmedyakout on 1/20/17.
 */
public class Keyboard extends Input {
    private static Keyboard instance;
    private List<UserAction> actions;
    private Map<Class<?>, Method> onActionMethods;
    private Map<Class<?>, Method> onActionBeginMethods;
    private Map<Class<?>, Method> onActionEndMethods;
    private Thread thread;
    private Map<Class<?>, Object> registeredClasses;
    private EventType<? extends InputEvent> eventType;

    private Keyboard() {
        actions = new ArrayList<>();
        onActionMethods = new HashMap<>();
        onActionBeginMethods = new HashMap<>();
        onActionEndMethods = new HashMap<>();
        registeredClasses = new HashMap<>();
        eventType = new EventType<JoystickEvent>("Keyboard");
    }

    public static synchronized Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }

    @Override
    public void addAction(UserAction userAction) {
        actions.add(userAction);
    }

    @Override
    public void registerClassForInputAction(Class<?> clazz, Object instance) {
        registeredClasses.put(clazz, instance);
    }

    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

                // First controller of the desired type.
                Controller firstController = searchForController(controllers);

                if (firstController == null) {
                    // Couldn't find a controller
                    System.out.println("Found no desired controller!");
                    return;
                }

                System.out.println("First controller of a desired type is: " + firstController.getName()
                        + firstController.getPortType() + " number " + firstController.getPortNumber());

                findAnnotatedMethods();
                poll(firstController);
            }
        });
        thread.start();
        // thread.setDaemon(true);
    }

    public void stop() {
        thread.interrupt();
    }

    private Controller searchForController(Controller[] controllers) {
        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.KEYBOARD) {
                // Found a controller
                return controller;
            }
        }
        return null;
    }

    private void poll(Controller controller) {
        Component prevComponent = null;
        Component currentComponent;
        KeyboardEvent keyboard = null;
        while (true) {
            try {
                controller.poll();
                Component[] components = controller.getComponents();
                keyboard = null;
                for (int i = 0; i < components.length; i++) {
                    currentComponent = components[i];
                    switch (currentComponent.getName()) {
                        case "A":
                            if (currentComponent.getPollData() == 1.0f) {
                                keyboard = new KeyboardEvent(eventType, KeyboardCode.A);
                            }
                            break;
                        case "D":
                            if (currentComponent.getPollData() == 1.0f) {
                                keyboard = new KeyboardEvent(eventType, KeyboardCode.D);
                            }
                            break;
                        case "Left":
                            if (currentComponent.getPollData() == 1.0f) {
                                keyboard = new KeyboardEvent(eventType, KeyboardCode.LEFT);
                            }
                            break;
                        case "Right":
                            if (currentComponent.getPollData() == 1.0f) {
                                keyboard = new KeyboardEvent(eventType, KeyboardCode.RIGHT);
                            }
                            break;
                    }
                    if (keyboard != null) {
                        invokeOnActionBeginMethods(keyboard);
                    }
                }

                Thread.sleep(50);
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void findAnnotatedMethods() {
        for (Map.Entry<Class<?>, Object> entry : registeredClasses.entrySet()) {
            Class<?> _class = entry.getKey();
            // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            Class<?> klass = _class;
            while (klass != Object.class) {
                // iterate though the list of methods declared in the class represented by klass variable,
                // and add those annotated with the specified annotation
                final List<Method> allMethods =
                        new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
                for (final Method method : allMethods) {
                    if (method.isAnnotationPresent(InputAction.class)) {
                        Annotation annotInstance = method.getAnnotation(InputAction.class);
                        if (((InputAction) annotInstance).INPUT_TYPE() != InputType.KEYBOARD_SECONDARY
                                && ((InputAction) annotInstance).INPUT_TYPE() != InputType.KEYBOARD_PRIMARY) {
                            return;
                        }
                        // process annotInstance
                        switch (((InputAction) annotInstance).ACTION_TYPE()) {
                            case BEGIN:
                                onActionBeginMethods.put(_class, method);
                                break;
                            case ONACTION:
                                onActionMethods.put(_class, method);
                                break;
                            case END:
                                onActionEndMethods.put(_class, method);
                        }
                        System.out.println(method.getName());
                    }
                }
                // move to the upper class in the hierarchy in search for more methods
                klass = klass.getSuperclass();
            }
        }
    }

    private void invokeOnActionBeginMethods(KeyboardEvent keyboardEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionBeginMethods.entrySet()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, keyboardEvent);
        }
    }

    private void invokeOnActionMethods(KeyboardEvent keyboardEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionMethods.entrySet()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, keyboardEvent);
        }
    }

    private void invokeOnActionEndMethods(KeyboardEvent keyboardEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionEndMethods.entrySet()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, keyboardEvent);
        }
    }
}
