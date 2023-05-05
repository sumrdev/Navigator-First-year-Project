package marp.utilities;

import java.io.File;
import java.nio.file.Paths;

public class DefaultPath {
    public static String getDefaultPath(){
        String dir = "/data/maps/";
        Paths.get("").toAbsolutePath().toString();
        String path = Paths.get("").toAbsolutePath().toString() + dir;
        path = new File(path).toURI().toString();
        
        return path;
    }
}
