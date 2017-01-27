package controllers.input.joystick;


import controllers.input.Input;
import controllers.input.InputAction;
import controllers.input.InputType;
import controllers.input.UserAction;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Joystick extends Input {
    private static Joystick instance;
    private List<UserAction> actions;
    private Map<Class<?>, Method> onActionMethods;
    private Map<Class<?>, Method> onActionBeginMethods;
    private Map<Class<?>, Method> onActionEndMethods;
    private Thread thread;
    private Map<Class<?>, Object> registeredClasses;
    private EventType<? extends InputEvent> eventType;

    private Joystick() {
        actions = new ArrayList<>();
        onActionMethods = new HashMap<>();
        onActionBeginMethods = new HashMap<>();
        onActionEndMethods = new HashMap<>();
        registeredClasses = new HashMap<>();
        eventType = new EventType<JoystickEvent>("Joystick");
    }

    /**
     * Gets the instance using Singleton.
     * @return returns the instance of the current class.
     */
    public static synchronized Joystick getInstance() {
        if (instance == null) {
            instance = new Joystick();
        }
        return instance;
    }

    public static void main(String[] args) {
        //   getMethodsAnnotatedWith();
    }

    /**
     * Starts the thread of the input controller.
     */
    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Controller[] controllers = ControllerEnvironment
                        .getDefaultEnvironment().getControllers();

                // First controller of the desired type.
                Controller firstController = searchForController(controllers);

                if (firstController == null) {
                    // Couldn't find a controller
                    System.out.println("Found no desired controller!");
                    return;
                }

                System.out.println("First controller of a desired type is: "
                        + firstController.getName()
                        + firstController.getPortType() + " number " +
                        firstController.getPortNumber());

                findAnnotatedMethods();
                poll(firstController);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Stops the thread of input controller.
     */
    public void stop() {
        thread.interrupt();
    }

    private Controller searchForController(Controller[] controllers) {
        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.STICK) {
                // Found a controller
                return controller;
            }
        }
        return null;
    }

    private void poll(Controller controller) {
        Component currentComponent;
        JoystickEvent joystick = null;
        while (true) {
            try {
                controller.poll();
                Component[] components = controller.getComponents();
                joystick = null;
                for (int i = 0; i < components.length; i++) {
                    if (components[i].isAnalog()) {
                        currentComponent = components[i];
                        if (currentComponent.getName().equals("y")) {
                            if (components[i].getPollData() == 1.0f) {
//                                System.out.println("down" + " done by
// controller: "
//                                        + (i > 11 ? "player 2" : "player 1"));
                                joystick = new JoystickEvent(eventType,
                                        JoystickCode.DOWN,
                                        i > 11 ? JoystickType.SECONDARY :
                                                JoystickType.PRIMARY);
                            } else if (currentComponent.getPollData() ==
                                    -1.0f) {
                                joystick = new JoystickEvent(eventType,
                                        JoystickCode.UP,
                                        i > 11 ? JoystickType.SECONDARY :
                                                JoystickType.PRIMARY);
//                                System.out.println("up" + " done by
// controller: "
//                                        + (i > 11 ? "player 2" : "player 1"));
                            }
                        } else {
                            if (currentComponent.getPollData() == 1.0f) {
                                joystick = new JoystickEvent(eventType,
                                        JoystickCode.RIGHT,
                                        i > 11 ? JoystickType.SECONDARY :
                                                JoystickType.PRIMARY);
//                                System.out.println("right" + " done by
// controller: "
//                                        + (i > 11 ? "player 2" : "player 1"));

                            } else if (currentComponent.getPollData() ==
                                    -1.0f) {
                                joystick = new JoystickEvent(eventType,
                                        JoystickCode.LEFT,
                                        i > 11 ? JoystickType.SECONDARY :
                                                JoystickType.PRIMARY);
//                                System.out.println("left" + " done by
// controller: "
//                                        + (i > 11 ? "player 2" : "player 1"));
                            }
                        }

                        // ======================================================================


//                        if (joystick != null) { //&& !currentComponent
// .equals(prevComponent)) {
//                            invokeOnActionBeginMethods(joystick);
//                            // invokeOnActionMethods(joystick);
//                        }
//                        else if (joystick != null && currentComponent
// .equals(prevComponent)) {
//                            invokeOnActionMethods(joystick);
//                            System.out.println("on");
//                        }


                        // component is not analog
                    } else {
                        if (components[i].getName().equals("2") &&
                                components[i].getPollData() == 1.0f) {
//                            System.out.println( i > 11 ? JoystickType
// .SECONDARY : JoystickType.PRIMARY);
                            joystick = new JoystickEvent(eventType,
                                    JoystickCode.PRESS,
                                    i > 11 ? JoystickType.SECONDARY :
                                            JoystickType.PRIMARY);
                        }
                    }

                    if (joystick != null) {
                        invokeOnActionBeginMethods(joystick);
                    }
//                    if (joystick != null && prevComponent != null &&
// !prevComponent.equals(components[i])) {
//                        invokeOnActionEndMethods(joystick);
//                        System.out.println("end");
//                    }
//                    prevComponent = components[i];
                }

                Thread.sleep(50);
            } catch (InterruptedException | IllegalAccessException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void invokeOnActionMethods(JoystickEvent joystickEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionMethods.entrySet()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, joystickEvent);
        }
    }

    private void invokeOnActionBeginMethods(JoystickEvent joystickEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionBeginMethods
                .entrySet()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, joystickEvent);
        }
    }

    private void invokeOnActionEndMethods(JoystickEvent joystickEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<Class<?>, Method> entry : onActionEndMethods.entrySet
                ()) {
            Object instance = registeredClasses.get(entry.getKey());
            entry.getValue().invoke(instance, joystickEvent);
        }
    }

    private void findAnnotatedMethods() {
        for (Map.Entry<Class<?>, Object> entry : registeredClasses.entrySet()) {
            Class<?> _class = entry.getKey();
            // need to iterated thought hierarchy in order to retrieve
            // methods from above the current instance
            Class<?> klass = _class;
            while (klass != Object.class) {
                // iterate though the list of methods declared in the class
                // represented by klass variable,
                // and add those annotated with the specified annotation
                final List<Method> allMethods =
                        new ArrayList<Method>(Arrays.asList(klass
                                .getDeclaredMethods()));
                for (final Method method : allMethods) {
                    if (method.isAnnotationPresent(InputAction.class)) {
                        Annotation annotInstance = method.getAnnotation
                                (InputAction.class);
                        if (((InputAction) annotInstance).INPUT_TYPE() !=
                                InputType.JOYSTICK
                                && ((InputAction) annotInstance).INPUT_TYPE()
                                != InputType.JOYSTICK_PRIMARY
                                && ((InputAction) annotInstance).INPUT_TYPE()
                                != InputType.KEYBOARD_SECONDARY) {
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
                                break;
                            default:
                                break;
                        }
                        System.out.println(method.getName());
                    }
                }
                // move to the upper class in the hierarchy in search for
                // more methods
                klass = klass.getSuperclass();
            }
        }
    }

    @Override
    public void addAction(UserAction userAction) {
        actions.add(userAction);
    }

    @Override
    public void registerClassForInputAction(Class<?> clazz, Object instance) {
        registeredClasses.put(clazz, instance);
    }

}
