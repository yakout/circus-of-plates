package models;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ShapeLoader {

    private static List<Class<?>> loadedShapeClasses;
    
//    public static void main(String[] args) {
//        ShapeLoader s = new ShapeLoader();
//        s.loadShapes(new File("----------"));
//    }

    public static void loadShapes(File source) {

        try {

            String path = new File("").getAbsolutePath() + File.separatorChar + "models" + File.separatorChar + "shapes"
                    + File.separatorChar;
            new File(path).mkdirs();

            File src = new File(source.toPath().toString() + File.separatorChar);
            File dest = new File(path);

            FileUtils.copyDirectory(src, dest);

            java.net.URL url = new File(new File("").getAbsolutePath()).toURI().toURL();
            java.net.URL[] urls = new java.net.URL[] { url };

            // load this folder into Class loader
            ClassLoader cl = new URLClassLoader(urls);

            File[] files = dest.listFiles();
            loadedShapeClasses = new ArrayList<>();
            for (File file : files) {
                if (file.isFile()) {
                    Class<?> cls = cl
                            .loadClass("models.shapes" + file.getName().substring(0, file.getName().indexOf('.')));
                    loadedShapeClasses.add(cls);
                }
            }
            System.out.println(dest.getParentFile() + "\n" + dest);
            FileUtils.deleteDirectory(dest.getParentFile());

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
