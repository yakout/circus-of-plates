package models.shapes.util;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ShapeLoader {

    // public static void main(String[] args) {
    // ShapeLoader s = new ShapeLoader();
    // s.loadShapes(new File("----------"));
    // }
    private static Logger logger = LogManager.getLogger(ShapeLoader.class);

    public static void loadShapes(File source) {

        String path = new File("").getAbsolutePath()
                + File.separatorChar +
                "models" + File.separatorChar + "shapes"
                + File.separatorChar;
        new File(path).mkdirs();

        File src = new File(source.toPath().toString() + File.separatorChar);
        File dest = new File(path);

        try {
            FileUtils.copyDirectory(src, dest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        URL url;
        try {
            url = new File(new File("").getAbsolutePath()).toURI().toURL();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        URL[] urls = new URL[]{url};

        // load this folder into Class loader
        ClassLoader cl = new URLClassLoader(urls);

        File[] files = dest.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                Class<?> cls = null;
                try {
                    cls = cl.loadClass("models.shapes." + file.getName()
                            .substring(0, file.getName().indexOf('.')));
                    Field keyField = cls.getDeclaredField("KEY");
                    keyField.setAccessible(true);
                    logger.info("Loaded Class: \"" + cls.getName() + "\" "
                            + "Which has the KEY: \"" + keyField.get(null) +
                            "\"");
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println(dest.getParentFile() + "\n" + dest);
        try {
            FileUtils.deleteDirectory(dest.getParentFile());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
