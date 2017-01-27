package controllers.input.keyboard;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Created by ahmedyakout on 1/20/17.
 */
public class KeyboardTest {
    public static void main(String[] args) {
        new KeyboardTest().pollControllerAndItsComponents(Controller.Type
                .KEYBOARD);
    }

    /**
     * Prints controllers components and its values.
     * @param controllerType Desired type of the controller.
     */
    public void pollControllerAndItsComponents(Controller.Type controllerType) {
        Controller[] controllers = ControllerEnvironment
                .getDefaultEnvironment().getControllers();

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

        System.out.println("First controller of a desired type is: " +
                firstController.getName()
                + firstController.getPortType() + " number " +
                firstController.getPortNumber());

        while (true) {
            System.out.println("STARRRRRRT");
            firstController.poll();
            Component[] components = firstController.getComponents();
            for (int i = 0; i < components.length; i++) {
                // System.out.println(components[i]);
                if (components[i].getName().equals("A") || components[i]
                        .getName().equals("D")) {
                    if (components[i].getPollData() == 1.0f) {
                        System.out.println(components[i].getName());
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

}
