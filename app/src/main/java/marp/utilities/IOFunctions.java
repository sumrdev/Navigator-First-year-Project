package marp.utilities;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOFunctions {
    public static List<String> getFiles(){
        // directory is resources
        String dir = IOFunctions.class.getClass().getClassLoader().getResource("maps").getPath();
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }   
}
