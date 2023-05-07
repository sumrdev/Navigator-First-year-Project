package marp.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IOFunctions {
    IOFunctions() {

    }

    /**
     * Returns a list of files available under 'data/maps'
     * 
     * @return List<String> of file names
     */
    public static List<String> getFiles() {
        File folder = new File("data/maps");
        File[] arrayOfFiles = folder.listFiles();
        ArrayList<String> filenames = new ArrayList<>();
        if (arrayOfFiles != null) {
            for (File arrayOfFile : arrayOfFiles) {
                if (arrayOfFile.isFile()) {
                    filenames.add(arrayOfFile.getAbsolutePath());
                }
            }
        }
        return filenames;
    }

    public static List<String> getFileNames() {
        File folder = new File("data/maps");
        File[] arrayOfFiles = folder.listFiles();
        ArrayList<String> filenames = new ArrayList<>();
        if (arrayOfFiles != null) {
            for (File arrayOfFile : arrayOfFiles) {
                if (arrayOfFile.isFile()) {
                    filenames.add(arrayOfFile.getName());
                }
            }
        }
        return filenames;
    }
}
