package controllers.input;

public abstract class Input {
    /**
     * Responsible for adding user actions.
     * @param userAction {@link UserAction} upcoming action of the user.
     */
    public abstract void addAction(UserAction userAction);

    /**
     * Required for registering the given class for Keyboard input controller.
     * @param clazz {@link Class}class is required to ger registered.
     * @param instance instance of the upcoming class.
     */
    public abstract void registerClassForInputAction(Class<?> clazz, Object instance);
}
