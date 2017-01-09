package controllers.input.joystick;

import javafx.beans.NamedArg;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class JoystickEvent extends InputEvent {
    private final JoystickCode joystickCode;
    private final PlayerType playerType;

    /**
     * Creates new instance of InputEvent.
     *
     * @param eventType Type of the event
     */
    public JoystickEvent(@NamedArg("eventType") EventType<? extends InputEvent> eventType,
                         @NamedArg("code") JoystickCode joystickCode, PlayerType playerType) {
        // // TODO: 1/9/17 add parameter for player type
        super(eventType);
        this.joystickCode = joystickCode;
        this.playerType = playerType;
    }

    public JoystickCode getJoystickCode() {
        return joystickCode;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
