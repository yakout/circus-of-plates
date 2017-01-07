package controllers.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InputAction {
    ActionType ACTION_TYPE() default ActionType.BEGIN;
    InputType INPUT_TYPE() default InputType.JOYSTICK;
}