package marp.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOFunctions {
    IOFunctions() {

    }

    public static List<String> getFiles() throws MalformedURLException {
        // directory is resources
        URL dir = DefaultPath.getDefaultPath();
        System.out.println("getDefaultPath: " + dir.toString());
        List<String> end = Stream.of(new File(dir.getFile()).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());

                System.out.println("Available files (" + end.size() + "):");
                for (String item : end){
                    System.out.println(item);
                }
                return end;
    }
}
