package services.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.data.ModelDataHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Moham on 24-Jan-17.
 */
class JsonReader implements FileReader {
    @Override
    public ModelDataHolder read(String path, String fileName) {
        Gson gson = new GsonBuilder().create();
        File jsonFile = new File(path
                + File.separator + fileName);
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
}
