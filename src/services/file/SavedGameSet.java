package services.file;


import models.settings.FileConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Ahmed Yakout on 1/25/17.
 */
public class SavedGameSet implements Iterable<String> {
    private final String EXTENSION = ".json";

    private List<String> savedGames;

    public SavedGameSet() {
        savedGames = listFiles(FileConstants.SAVE_PATH, EXTENSION);
    }

    /**
     * Finds and returns the names of all
     * the files that end with a given extension in a
     * specific folder.
     * @param path          the folder path
     * @param fileExtension the extension of the file
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
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return new SavedGameIterator();
    }

    private class SavedGameIterator implements Iterator<String> {
        private int counter = 0;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return counter < savedGames.size();
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public String next() {
            return savedGames.get(counter++);
        }
    }
}
