package controllers.input.joystick;


import controllers.input.Input;
import controllers.input.InputAction;
import controllers.input.UserAction;
import controllers.main.GameController;
import javafx.event.EventType;
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
    private List<Method> onActionMethods = new ArrayList<>();
    private List<Method> onActionBeginMethods = new ArrayList<>();
    private List<Method> onActionEndMethods = new ArrayList<>();
    private Thread thread;
    private Map<Class<?>, Object> listeners = new HashMap<>();
    private EventType eventType = new EventType<JoystickEvent>("Joystick");

    private Joystick() {
        start();
    }

    public static synchronized Joystick getInstance() {
        if (instance == null) {
            instance = new Joystick();
        }
        return instance;
    }

    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

                // First controller of the desired type.
                Controller firstController = searchForController(controllers);

                if(firstController == null) {
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
        while(true) {
            try {
                controller.poll();
                Component[] components = controller.getComponents();
                JoystickEvent joystick = null;
                for(int i = 0; i < components.length; i++) {
                    if(components[i].isAnalog()) {
                        if (components[i].getName().equals("y")) {
                            if (components[i].getPollData() == 1.0f) {
                                System.out.println("down" + " done by controller: "
                                        + (i > 11 ? "player 2" : "player 1"));
                                joystick = new JoystickEvent(eventType, JoystickCode.DOWN,
                                        i > 11 ? PlayerType.PLAYER_TWO : PlayerType.PLAYER_ONE);
                            } else if (components[i].getPollData() == -1.0f) {
                                joystick = new JoystickEvent(eventType, JoystickCode.UP,
                                        i > 11 ? PlayerType.PLAYER_TWO : PlayerType.PLAYER_ONE);
                                System.out.println("up" + " done by controller: "
                                        + (i > 11 ? "player 2" : "player 1"));
                            }
                        } else {
                            if (components[i].getPollData() == 1.0f) {
                                joystick = new JoystickEvent(eventType, JoystickCode.RIGHT,
                                        i > 11 ? PlayerType.PLAYER_TWO : PlayerType.PLAYER_ONE);
                                System.out.println("right" + " done by controller: "
                                        + (i > 11 ? "player 2" : "player 1"));

                            } else if (components[i].getPollData() == -1.0f) {
                                joystick = new JoystickEvent(eventType, JoystickCode.LEFT,
                                        i > 11 ? PlayerType.PLAYER_TWO : PlayerType.PLAYER_ONE);
                                System.out.println("left" + " done by controller: "
                                        + (i > 11 ? "player 2" : "player 1"));
                            }
                        }
                        if (joystick != null) invokeOnActionBeginMethods(joystick);
                    }
                }
                // // TODO: 1/9/17 invoke onActionMethods
                Thread.sleep(50);
                if (joystick != null) invokeOnActionEndMethods(joystick);
            } catch (InterruptedException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    private void invokeOnActionMethods(JoystickEvent joystickEvent)
        throws InvocationTargetException, IllegalAccessException {
            for (Method method : onActionMethods) {
                method.invoke(GameController.getInstance(), joystickEvent);
            }
    }

    private void invokeOnActionBeginMethods(JoystickEvent joystickEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : onActionBeginMethods) {
            method.invoke(GameController.getInstance(), joystickEvent);
        }
    }

    private void invokeOnActionEndMethods(JoystickEvent joystickEvent)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : onActionEndMethods) {
            method.invoke(GameController.getInstance(), joystickEvent);
        }
    }


    public void stop() {
        thread.interrupt();
    }

    public void findAnnotatedMethods() {
//        for (Class<?> klass : listeners) {
//            // need to iterated thought hierarchy in order to retrieve methods from above the current instance
//            while (klass != Object.class) {
//                // iterate though the list of methods declared in the class represented by klass variable,
//                // and add those annotated with the specified annotation
//                final List<Method> allMethods =
//                        new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
//                for (final Method method : allMethods) {
//                    if (method.isAnnotationPresent(InputAction.class)) {
//                        Annotation annotInstance = method.getAnnotation(InputAction.class);
//                        // process annotInstance
//                        switch (((InputAction) annotInstance).ACTION_TYPE()) {
//                            case BEGIN:
//                                onActionBeginMethods.add(method);
//                                break;
//                            case ONACTION:
//                                onActionMethods.add(method);
//                                break;
//                            case END:
//                                onActionEndMethods.add(method);
//                        }
//                        System.out.println(method.getName());
//                    }
//                }
//                // move to the upper class in the hierarchy in search for more methods
//                klass = klass.getSuperclass();
//            }
//        }
    }

    @Override
    public void addAction(UserAction userAction) {
        actions.add(userAction);
    }

    @Override
    public void registerClassForInputAction(Class<?> clazz, Object instance) {
        listeners.put(clazz, instance);
    }

    public static void main(String[] args) {
        //   getMethodsAnnotatedWith();
    }

}
