package marp.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class DefaultPath {
    public static URL getDefaultPath() throws MalformedURLException{
        String dir = "/data/maps/";
        Paths.get("").toAbsolutePath().toString();
        URL path = new URL(new File(Paths.get("").toAbsolutePath().toString() + dir).toURI().toString());
        return path;
    }
}
