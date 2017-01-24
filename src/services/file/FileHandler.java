package services.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

import models.data.ModelDataHolder;
import models.players.Player;
import models.shapes.PlateShape;
import models.shapes.Shape;
import models.states.Color;
import org.apache.commons.io.IOUtils;

/**
 * Created by Moham on 24-Jan-17.
 */
public class FileHandler implements FileWriter, FileReader {
    private FileWriter writer;
    private FileReader reader;
    private int savedGamesCnt;

    public FileHandler() {
        writer = new JsonWriter();
        reader = new JsonReader();
        savedGamesCnt = 0;
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
        return reader.read(path, fileName);
    }
    
    private void addSaveEntry(String path, String fileName) {
        this.writeDataFile(path,fileName + writer.getExtension() + "\n");
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
                e.printStackTrace();
            }
        }
            try {
                Files.write(saveData.toPath(), data.getBytes(StandardCharsets
                        .UTF_8), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public List<String> getFileList(String path) {
        try {
            String saveFileContents = IOUtils.toString(new File(path + File
                    .separator + "save.ini").toURI(), "UTF-8");
            List<String> fileList = new ArrayList<>();
            for (String file : saveFileContents.split("\n")) {
                if (file.contains(reader.getExtension())) {
                    fileList.add(file.replaceAll(reader.getExtension(), ""));
                }
            }
            return fileList;
        } catch (IOException e) {
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
