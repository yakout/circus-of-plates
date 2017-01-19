package models;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;


import org.apache.commons.io.FileUtils;

public class ShapeLoader {

    // public static void main(String[] args) {
    // ShapeLoader s = new ShapeLoader();
    // s.loadShapes(new File("----------"));
    // }

    public static void loadShapes(File source) {

        String path = new File("").getAbsolutePath() + File.separatorChar + "models" + File.separatorChar + "shapes"
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

        java.net.URL url;
        try {
            url = new File(new File("").getAbsolutePath()).toURI().toURL();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        java.net.URL[] urls = new java.net.URL[] { url };

        // load this folder into Class loader
        ClassLoader cl = new URLClassLoader(urls);

        File[] files = dest.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                Class<?> cls = null;
                try {
                    cls = cl.loadClass("models.shapes" + file.getName().substring(0, file.getName().indexOf('.')));
                    ShapeFactory.register(cls);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
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
