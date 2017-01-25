package services.file;

import com.google.gson.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import models.Point;
import models.shapes.util.ShapeFactory;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.levels.util.LevelFactory;
import models.shapes.Shape;
import models.states.Color;
import models.states.ShapeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Moham on 24-Jan-17.
 */
class JsonReader implements FileReader {

    private static Logger logger = LogManager.getLogger(ModelDataHolder
            .class);
    @Override
    public ModelDataHolder read(String path, String fileName) {

        Gson gson = new GsonBuilder().registerTypeAdapter(Point.class,
                new PointDeserializer()).registerTypeAdapter
                (Level.class, new LevelDeserializer()).registerTypeAdapter
                (Shape.class, new ShapeDeserializer()).registerTypeAdapter
                (DoubleProperty.class, new DoublePropertyDeserializer())
                .create();
        File jsonFile = new File(path
                + File.separator + fileName + ".json");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(jsonFile));
        } catch (FileNotFoundException e) {
            logger.error("File is not found at directory: "
                    + jsonFile.getAbsolutePath());
            e.printStackTrace();
        }
        final StringBuilder json = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
        } catch (IOException e) {
            return null;
        }
        logger.debug("Data is parsed from JSON file successfully.");
        return gson.fromJson(json.toString(), ModelDataHolder.class);
    }

    @Override
    public String getExtension() {
        return ".json";
    }

    private class DoublePropertyDeserializer
            implements JsonDeserializer<DoubleProperty> {

        @Override
        public DoubleProperty deserialize(final JsonElement json,
                                          final Type type,
                                          final JsonDeserializationContext
                                                  jsonDeserializationContext)
                throws JsonParseException {
            final double value
                    = json.getAsDouble();
            DoubleProperty doubleProperty = new SimpleDoubleProperty(0);
            doubleProperty.set(value);
            return doubleProperty;
        }
    }

    private class PointDeserializer
            implements JsonDeserializer<Point> {

        @Override
        public Point deserialize(final JsonElement json, final Type type,
                                 final JsonDeserializationContext
                                         jsonDeserializationContext)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            double x = jsonObject.get("propX").getAsDouble();
            double y = jsonObject.get("propY").getAsDouble();
//            System.out.println(new Point(x, y));
            return new Point(x, y);
        }
    }

    private class LevelDeserializer
            implements JsonDeserializer<Level> {

        @Override
        public Level deserialize(final JsonElement json,
                                 final Type type,
                                 final JsonDeserializationContext
                                         jsonDeserializationContext)
                throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final int levelNumber = jsonObject.get("currentLevel").getAsInt();
            double minX = jsonObject.get("minX").getAsDouble();
            double minY = jsonObject.get("minY").getAsDouble();
            double maxX = jsonObject.get("maxX").getAsDouble();
            double maxY = jsonObject.get("maxY").getAsDouble();
            Level level = LevelFactory.getInstance().createLevel(levelNumber,
                    minX, minY, maxX, maxY);
            return level;
        }
    }

    private class ShapeDeserializer
            implements JsonDeserializer<Shape> {

        @Override
        public Shape deserialize(final JsonElement json,
                                 final Type type,
                                 final JsonDeserializationContext
                                         jsonDeserializationContext)
                throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();
            final String shapeKey = jsonObject.get("key").getAsString();
            final Color color = Color.valueOf(jsonObject.get("color")
                    .getAsString());
            Shape shape = ShapeFactory.getShape(color, shapeKey);
            Point position = new PointDeserializer().deserialize(jsonObject
                    .get("position"), type, jsonDeserializationContext);
            Point initialPosition = new PointDeserializer().deserialize
                    (jsonObject.get("initialPosition"), type,
                            jsonDeserializationContext);
            final ShapeState state = ShapeState.valueOf(jsonObject.get("state")
                    .getAsString());
            shape.setPosition(position);
            shape.setInitialPosition(initialPosition);
            shape.setState(state);
            return shape;
        }
    }
}
