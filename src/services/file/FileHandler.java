package services.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import models.data.ModelDataHolder;
import models.players.Player;
import models.shapes.PlateShape;
import models.shapes.Shape;
import models.states.Color;

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
        this.setupSaveDirectory(path);
        writer.write(dataHolder, path, fileName);
        this.addSaveEntry(path, fileName);
    }

    @Override
    public ModelDataHolder read(String path, String fileName) {
        this.setupSaveDirectory(path);
        return reader.read(path, fileName);
    }

    private void setupSaveDirectory(String path) {
        this.writeDataFile(path, "[Saves]");
    }
    
    private void addSaveEntry(String path, String fileName) {
        this.writeDataFile(path, "Save" + ++this.savedGamesCnt + "=" + fileName + ".sav" + "\n");
    }
    
    private void writeDataFile(String path, String data) {
        File saveData = new File(path + File.separatorChar + "save.ini");
        if (!saveData.getParentFile().exists())
            new File("save" + File.separatorChar).mkdir();
        if (!saveData.exists()) {
            try {
                Files.write(saveData.toPath(), data.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
//    public static void main(String[] args) {
//        ModelDataHolder obj = new ModelDataHolder();
//        Shape shape = new PlateShape();
//        shape.setColor(Color.CYAN);
//        shape.setHeight(119);
//        obj.addShape(shape);
//        
//        Player player = new Player("Ahmed");
//        player.setSpeed(5.0);
//        obj.addPlayer(player);
//        
////        Level lvl = new LevelOne(1, 2, 3, 4);
////        obj.setActiveLevel(lvl);
//        
//        FileHandler fh = new FileHandler();
//        fh.write(obj, "save", "firstTest");
//    }
}
