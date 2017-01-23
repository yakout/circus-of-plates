package models.shapes.util;

import models.states.Color;

/**
 * Created by Moham on 23-Jan-17.
 */
public class ColorNameAdapter {
    public String getColorName(Color color) {
        switch (color) {
            case GREEN:
                return "green";
            case ORANGE:
                return "orange";
            case YELLOW:
                return "yellow";
            case CYAN:
                return "cyan";
            default:
                return "";
        }
    }
}
