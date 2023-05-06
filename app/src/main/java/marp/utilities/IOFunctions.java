package marp.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOFunctions {
    IOFunctions() {

    }
    public static List<String> getFiles(){
        // directory is resources
        // String dir = IOFunctions.class.getClassLoader().getResource("maps").getPath();
        // System.out.println(dir);
        //return Stream.of(new File(dir).listFiles())
        //        .filter(file -> !file.isDirectory())
        //        .map(File::getName)
        //        .collect(Collectors.toList());
        return new ArrayList<>();
    }   
}
