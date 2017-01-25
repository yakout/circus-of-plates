package controllers.input.joystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class JoystickTest {
    /**
     * Prints controllers components and its values.
     *
     * @param controllerType Desired type of the controller.
     */
    public void pollControllerAndItsComponents(Controller.Type controllerType) {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        // First controller of the desired type.
        Controller firstController = null;

        for (Controller controller : controllers) {
            if (controller.getType() == controllerType) {
                // Found a controller
                firstController = controller;
                break;
            }

        }

        if (firstController == null) {
            // Couldn't find a controller
            System.out.println("Found no desired controller!");
            System.exit(0);
        }

        System.out.println("First controller of a desired type is: " + firstController.getName()
                + firstController.getPortType() + " number " + firstController.getPortNumber());

        while (true) {
            firstController.poll();
            Component[] components = firstController.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i].isAnalog()) {
                    if (components[i].getName().equals("y")) {
                        if (components[i].getPollData() == 1.0f) {
                            System.out.println("down" + " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                        } else if (components[i].getPollData() == -1.0f) {
                            System.out.println("up" + " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                        }
                    } else {
                        if (components[i].getPollData() == 1.0f) {
                            System.out.println("right" + " done by controller: " + (i > 11 ? "player 2" : "player 1"));
                        } else if (components[i].getPollData() == -1.0f) {
                            System.out.println("left" + " done by controller: " + (i > 11 ? "player 2" : "player 1"));
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

    public static void main(String[] args) {
        new JoystickTest().pollControllerAndItsComponents(Controller.Type.STICK);
    }

}
