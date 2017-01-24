package services.file;

import models.data.ModelDataHolder;

/**
 * Created by Moham on 24-Jan-17.
 */
public class FileHandler implements FileWriter, FileReader {
    private FileWriter writer;
    private FileReader reader;

    public FileHandler() {
        writer = new JsonWriter();
        reader = new JsonReader();
    }

    @Override
    public void write(ModelDataHolder dataHolder, String path, String
            fileName) {
        writer.write(dataHolder, path, fileName);
    }

    @Override
    public ModelDataHolder read(String path, String fileName) {
        return reader.read(path, fileName);
    }
}
