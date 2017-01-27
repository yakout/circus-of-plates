package controllers.input.keyboard;

import javafx.beans.NamedArg;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class KeyboardEvent extends InputEvent {
    private final KeyboardCode keyboardCode;

    /**
     * Creates new instance of InputEvent.
     * @param eventType Type of the event
     */
    public KeyboardEvent(@NamedArg("eventType") EventType<? extends
            InputEvent> eventType,
                         @NamedArg("code") KeyboardCode keyboardCode) {
        // // TODO: 1/9/17 add parameter for player type
        super(eventType);
        this.keyboardCode = keyboardCode;
    }

    public KeyboardCode getKeyboardCode() {
        return keyboardCode;
    }
}
