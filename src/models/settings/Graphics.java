package models.settings;

import com.sun.javafx.geom.Dimension2D;

final class Graphics {
    public enum Quality {
        ULTRA, HIGH, MEDIUM, LOW
    }

    private boolean isFullScreen;
    private Quality quality;
    private Dimension2D dimension;
    
    public Graphics() {
        this.quality = Quality.MEDIUM;
        this.dimension = new Dimension2D(800, 600);
        this.isFullScreen = false;
    }

    public Quality getQuality() {
        return this.quality;
    }

    public void setQuality(Quality newQuality) {
        this.quality = newQuality;
    }

    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    public Dimension2D getResolution() {
        return this.dimension;
    }

    public void setDimension(Dimension2D newDimension) {
        this.dimension = newDimension;
    }
}
