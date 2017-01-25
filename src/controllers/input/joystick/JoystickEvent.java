package controllers.input.joystick;

import javafx.beans.NamedArg;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class JoystickEvent extends InputEvent {
    private final JoystickCode joystickCode;
    private final JoystickType joystickType;

    /**
     * Creates new instance of InputEvent.
     *
     * @param eventType Type of the event
     */
    public JoystickEvent(@NamedArg("eventType") EventType<? extends
            InputEvent> eventType, @NamedArg("code") JoystickCode joystickCode,
            JoystickType joystickType) {
        // // TODO: 1/9/17 add parameter for player type
        super(eventType);
        this.joystickCode = joystickCode;
        this.joystickType = joystickType;
    }

    /**
     * @return the joystick code.
     */
    public JoystickCode getJoystickCode() {
        return joystickCode;
    }

    /**
     *
     * @return the joystick type.
     */
    public JoystickType getJoystickType() {
        return joystickType;
    }
}
