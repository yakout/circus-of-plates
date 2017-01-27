package models;

import javafx.beans.property.DoubleProperty;
import models.states.Orientation;

import java.io.File;
import java.net.MalformedURLException;

public class Platform {
    private static final String URL = "assets/images/platforms/rod.png";
    private Point center;
    private DoubleProperty width;
    private DoubleProperty height;
    private Orientation orientation;
    private String url;

    public Platform(Point center, Orientation orientation) {
        this.orientation = orientation;
        this.center = center;
        this.url = URL;
    }

    public DoubleProperty getWidth() {
        return width;
    }

    public void setWidth(DoubleProperty width) {
        this.width = width;
    }

    public DoubleProperty getHeight() {
        return height;
    }

    public void setHeight(DoubleProperty height) {
        this.height = height;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public String getUrl() {
        return ClassLoader.getSystemResource(url).toString();
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
