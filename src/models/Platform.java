package models;

import javafx.beans.property.DoubleProperty;
import models.states.Orientation;

import java.io.File;
import java.net.MalformedURLException;

public class Platform {
    private Point center;
    private DoubleProperty width;
    private DoubleProperty height;
    private Orientation orientation;
    private static final String URL = "src/assets/images/platforms/rod.png";
    private String url;

    public Platform(Point center, Orientation orientation) {
        this.orientation = orientation;
        this.center = center;
        this.url = URL;
    }

    public void setWidth(DoubleProperty width) {
        this.width = width;
    }

    public void setHeight(DoubleProperty height) {
        this.height = height;
    }

    public DoubleProperty getWidth() {
        return width;
    }

    public DoubleProperty getHeight() {
        return height;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    public String getUrl() {
        try {
            return new File(url).toURI().toURL()
                    .toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
