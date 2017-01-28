package controllers.shape.util;

public interface ShapeFallingObserver {
    public void shapeShouldStopFalling();
    public void shapeFellOnTheStack();
    public boolean checkIntersection();
}
