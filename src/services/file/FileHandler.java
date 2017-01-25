package services.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

import models.data.ModelDataHolder;
import models.players.Player;
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
    private static FileHandler instance;

    private FileHandler() {
        writer = new JsonWriter();
        reader = new JsonReader();
        savedGamesCnt = 0;
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
        return reader.read(path, fileName);
    }

    private void addSaveEntry(String path, String fileName) {
        this.writeDataFile(path, fileName + writer.getExtension() + "\n");
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
            File saveData = new File(path +
                    File.separator + "save.ini");
            if (!saveData.exists()) {
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds and returns the names of all
     * the files that end with a given extension in a
     * specific folder.
     *
     * @param path          the folder path
     * @param fileExtension the extension of the file
     *
     * @return The names of the files
     */
    private List<String> listFiles(final String path, final String
            fileExtension) {
        final File database = new File(path);
        final File[] files = database.listFiles();
        final ArrayList<String> filesList = new ArrayList<>();
        if (files == null) {
            return filesList;
        }
        for (int i = 0; i < files.length; i++) {
            final String fileName = files[i].getName();
            if (fileName.endsWith(fileExtension)) {
                filesList.add(fileName.replaceAll(fileExtension, ""));
            }
        }
        return filesList;
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
