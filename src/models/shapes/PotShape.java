package models.shapes;

import models.levels.LevelFive;
import models.levels.LevelFour;
import models.levels.LevelThree;
import models.levels.LevelTwo;
import models.shapes.util.ShapeFactory;
import models.states.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Ahmed Khaled on 25/01/2017.
 */
public class PotShape extends Shape {

    private static final String URL = "src/assets/images/plates/";
    private static final String FILE_NAME = "PotShape.png";
    private static final double HORIZONTAL_VELOCITY = 1.8;
    private static final double VERTICAL_VELOCITY = 2.0;
    private static final String KEY = PotShape.class.getName();
    private static Logger logger = LogManager.getLogger();

    static {
        ShapeFactory.registerShape(KEY, PotShape.class);
        LevelTwo.registerShape(KEY);
        LevelThree.registerShape(KEY);
        LevelFour.registerShape(KEY);
        LevelFive.registerShape(KEY);
        logger.debug("Class " + KEY + " initialized");
    }

    public PotShape() {
        super();
        setKey(KEY);
        setHorizontalVelocity(HORIZONTAL_VELOCITY);
        setVerticalVelocity(VERTICAL_VELOCITY);
    }

    @Override
    public String getShapeURL() {
        String colorString = getColorName(color);
        try {
            return new File(URL + colorString + FILE_NAME)
                    .toURI().toURL().toString();
        } catch (MalformedURLException e) {
            logger.error("Couldn't find " + KEY + " with this color");
            e.printStackTrace();
            return null;
        }
    }

    protected String getColorName(Color color) {
        return color.toString().toLowerCase();
    }
}
