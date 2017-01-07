package controllers.input.joystick;

import javafx.beans.NamedArg;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public class JoystickEvent extends InputEvent {

    /**
     * Creates new instance of InputEvent.
     *
     * @param eventType Type of the event
     */
    public JoystickEvent(@NamedArg("eventType") EventType<? extends InputEvent> eventType) {
        super(eventType);
    }

    /**
     * Creates new instance of InputEvent.
     *
     * @param source    Event source
     * @param target    Event target
     * @param eventType Type of the event
     */
    public JoystickEvent(@NamedArg("source") Object source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<? extends InputEvent> eventType) {
        super(source, target, eventType);
    }
}
