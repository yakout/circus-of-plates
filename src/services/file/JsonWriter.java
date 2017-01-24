package services.file;

import com.google.gson.*;
import javafx.beans.property.DoubleProperty;
import models.data.ModelDataHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Moham on 24-Jan-17.
 */
class JsonWriter implements FileWriter {
    @Override
    public void write(ModelDataHolder dataHolder, String path, String
            fileName) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DoubleProperty
                .class, new DoublePropertySerializer())
                .setPrettyPrinting().create();
        String json = gson.toJson(dataHolder);
        File jsonFile = new File(path + File.separator +
                fileName + ".json");
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(jsonFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                outputStream.write(json.getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DoublePropertySerializer
            implements JsonSerializer<DoubleProperty> {
        @Override
        public JsonElement serialize(final DoubleProperty doubleProperty,
                                     final Type type,
                                     final JsonSerializationContext
                                             jsonSerializationContext) {
            final JsonElement element
                    = jsonSerializationContext.serialize(
                    doubleProperty.doubleValue(), double.class);
            return element;
        }
    }
}
