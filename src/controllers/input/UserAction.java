package controllers.input;

public interface UserAction {
    /**
     * Called once an action has triggered
     */
     void onActionBegin();

    /**
     * Called while the action is still triggered at very little time intervals.
     * (i.e as long as the trigger is begin held (pressed))
     */
    void onAction();

    /**
     * Called when action end (released)
     */
    void onActionEnd();
}
