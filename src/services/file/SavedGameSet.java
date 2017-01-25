package services.file;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ahmed Yakout on 1/25/17.
 */
public class SavedGameSet implements Iterable<String> {
    private final String PATH = "save";
    private final String EXTENSION = ".json";

    private List<String> savedGames;

    public SavedGameSet() {
        savedGames = listFiles(PATH, EXTENSION);
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

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return savedGames.iterator();
    }
}
