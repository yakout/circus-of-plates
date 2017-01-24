package services.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.data.ModelDataHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Moham on 24-Jan-17.
 */
class JsonWriter implements FileWriter {
    @Override
    public void write(ModelDataHolder dataHolder, String path, String
            fileName) {
        Gson gson = new GsonBuilder()
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
}
