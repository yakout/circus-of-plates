package services.file;

import models.data.ModelDataHolder;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moham on 24-Jan-17.
 */
public class FileHandler implements FileWriter, FileReader {

    private static Logger logger = LogManager.getLogger(FileHandler.class);
    private FileWriter writer;
    private FileReader reader;
    private static FileHandler instance;

    private FileHandler() {
        writer = new JsonWriter();
        reader = new JsonReader();
    }

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    @Override
    public void write(ModelDataHolder dataHolder, String path, String fileName) {
        this.addSaveEntry(path, fileName);
        writer.write(dataHolder, path, fileName);
    }

    @Override
    public String getExtension() {
        return writer.getExtension();
    }

    @Override
    public ModelDataHolder read(String path, String fileName) {
        logger.info("Data is loaded successfully.");
        return reader.read(path, fileName);
    }

    private void addSaveEntry(String path, String fileName) {
        this.writeDataFile(path, fileName + writer.getExtension() + "\n");
        logger.info("Data is saved successfully.");
    }

    private void writeDataFile(String path, String data) {
        File saveData = new File(path + File.separatorChar + "save.ini");
        if (!saveData.getParentFile().exists()) {
            new File("save" + File.separatorChar).mkdirs();
        }
        if (!saveData.exists()) {
            try {
                saveData.createNewFile();
                Files.write(saveData.toPath(), "[Save Files]\n".getBytes
                        (StandardCharsets
                                .UTF_8), StandardOpenOption.APPEND);
            } catch (IOException e) {
                logger.error("Error occurred while writing data to file.");
                e.printStackTrace();
            }
        }
        try {
            Files.write(saveData.toPath(), data.getBytes(StandardCharsets
                    .UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Error occurred while writing data to file.");
            e.printStackTrace();
        }
        logger.debug("Data is written to the file successfully.");
    }

    public List<String> getFileList(String path) {
        try {
            File saveData = new File(path +
                    File.separator + "save.ini");
            if (!saveData.exists()) {
                logger.debug("File .ini doesn't exist.");
                return new ArrayList<>();
            }
            String saveFileContents = IOUtils.toString(saveData.toURI(), "UTF-8");
            List<String> fileList = new ArrayList<>();
            for (String file : saveFileContents.split("\n")) {
                if (file.contains(reader.getExtension())) {
                    fileList.add(file.replaceAll(reader.getExtension(), ""));
                }
            }
            return fileList;
        } catch (IOException e) {
            logger.error("Error file doesn't exist.");
            e.printStackTrace();
        }
        return null;
    }

 /*   public static void main(String[] args) {
        ModelDataHolder obj = new ModelDataHolder();
        Shape shape = new PlateShape();
        shape.setColor(Color.CYAN);
        shape.setHeight(119);
        obj.addShape(shape);

        Player player = new Player("Ahmed");
        player.setSpeed(5.0);
        obj.addPlayer(player);

//        Level lvl = new LevelOne(1, 2, 3, 4);
//        obj.setActiveLevel(lvl);

        FileHandler fh = new FileHandler();
        fh.write(obj, "save", "firstTest");
        for (String s : fh.getFileList("save/")) {
            System.out.println(s);
        }
    }*/
}
