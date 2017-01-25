package controllers.input;

public abstract class Input {
    public abstract void addAction(UserAction userAction);

    public abstract void registerClassForInputAction(Class<?> clazz, Object instance);
}
