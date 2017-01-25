package controllers.shape.util;

/**
 * Created by Moham on 24-Jan-17.
 */
public interface ShapeState {

    public void nextState();

    public boolean hasNextState();

    public void pauseCurrentState();

    public void resumeCurrentState();

}
