package controllers.input.joystick;

import javafx.beans.NamedArg;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class JoystickEvent extends InputEvent {
     private final JoystickCode joystickCode;

    /**
     * Creates new instance of InputEvent.
     *
     * @param eventType Type of the event
     */
    public JoystickEvent(@NamedArg("eventType") EventType<? extends InputEvent> eventType,
                         @NamedArg("code") JoystickCode joystickCode) {
        super(eventType);
        this.joystickCode = joystickCode;
    }

    public JoystickCode getJoystickCode() {
        return joystickCode;
    }
}
