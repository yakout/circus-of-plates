package services.file;

import com.google.gson.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import models.ShapeFactory;
import models.data.ModelDataHolder;
import models.levels.Level;
import models.levels.LevelOne;
import models.levels.LevelTwo;
import models.levels.util.LevelFactory;
import models.shapes.Shape;
import models.states.Color;
import models.states.ShapeState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Moham on 24-Jan-17.
 */
class JsonReader implements FileReader {
    @Override
    public ModelDataHolder read(String path, String fileName) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DoubleProperty
                .class, new DoublePropertyDeserializer()).registerTypeAdapter
                (Level.class, new LevelDeserializer()).registerTypeAdapter
                (Shape.class, new ShapeDeserializer())
                .create();
        File jsonFile = new File(path
                + File.separator + fileName + ".json");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(jsonFile));
        } catch (FileNotFoundException e) {
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
        return gson.fromJson(json.toString(), ModelDataHolder.class);
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
            return new SimpleDoubleProperty(value);
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
            return ShapeFactory.getShape(color, shapeKey);
        }
    }
}