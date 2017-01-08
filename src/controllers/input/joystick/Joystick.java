package controllers.input.joystick;


import controllers.input.Input;
import controllers.input.InputAction;
import controllers.input.UserAction;
import controllers.main.GameController;
import javafx.event.EventType;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Joystick extends Input {
    private List<UserAction> actions;
    private List<Method> onActionMethods = new ArrayList<>();
    private List<Method> onActionBeginMethods = new ArrayList<>();
    private List<Method> onActionEndMethods = new ArrayList<>();
    private Thread thread;
    private List<Class<?>> listeners;
    private EventType eventType = new EventType<JoystickEvent>("Joystick");

    public Joystick() {
        listeners = new ArrayList<>();

        start();
    }

    public Joystick(Class<?> clazz) {
        this();
        listeners.add(clazz);
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

                getAnnotatedMethods();
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
            controller.poll();
            Component[] components = controller.getComponents();
            for(int i = 0; i < components.length; i++) {
                if(components[i].isAnalog()) {
                    JoystickEvent joystick = null;

                    if (components[i].getName().equals("y")) {
                        if (components[i].getPollData() == 1.0f) {
                            System.out.println("down"+ " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                            joystick = new JoystickEvent(eventType, JoystickCode.DOWN);
                            try {
                                onActionBeginMethods.get(0).invoke(GameController.getInstance(), joystick);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else if (components[i].getPollData() == -1.0f) {
                            joystick = new JoystickEvent(eventType, JoystickCode.UP);
                            System.out.println("up"+ " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                            try {
                                onActionBeginMethods.get(0).invoke(GameController.getInstance(), joystick);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        if (components[i].getPollData() == 1.0f) {
                            joystick = new JoystickEvent(eventType, JoystickCode.RIGHT);
                            System.out.println("right" + " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                            try {
                                onActionBeginMethods.get(0).invoke(GameController.getInstance(), joystick);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else if (components[i].getPollData() == -1.0f) {
                            joystick = new JoystickEvent(eventType, JoystickCode.LEFT);
                            System.out.println("left"+ " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                            try {
                                onActionBeginMethods.get(0).invoke(GameController.getInstance(), joystick);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        thread.interrupt();
    }

    public List<Method> getAnnotatedMethods() {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = listeners.get(0);
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(GameController.getInstance().getClass().getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(InputAction.class)) {
                    // Annotation annotInstance = method.getAnnotation(InputAction.class);
                    // TODO process annotInstance
                    onActionBeginMethods.add(method);
                    System.out.println(method.getName());
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }

    @Override
    public void addAction(UserAction userAction) {

    }

    @Override
    public void registerClassForInputAction(Class<?> clazz) {

    }


    public static void main(String[] args) {
     //   getMethodsAnnotatedWith();
    }

}
