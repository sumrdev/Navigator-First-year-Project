package marp.utilities;

import java.io.File;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOFunctions {
    IOFunctions() {

    }
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
}
