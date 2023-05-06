package marp.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

import javax.swing.DefaultBoundedRangeModel;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import marp.parser.OSMParser;
import marp.utilities.DefaultPath;;

public class Model implements Serializable{
    private MapObjects mapObjects;

    public MapObjects getMapObjects(){
        return mapObjects;
    }

    public static Model createModel(URL fileURL) throws URISyntaxException, XMLStreamException,
            FactoryConfigurationError, ClassNotFoundException, IOException {
        File file = Paths.get(fileURL.toURI()).toFile();
        return findLoadType(file.getAbsolutePath());
    }

    private static Model findLoadType(String filepath)
            throws XMLStreamException, FactoryConfigurationError, ClassNotFoundException, IOException {
        OSMParser osmParser = new OSMParser();
        System.out.println(filepath);
        File file = new File(filepath);
        String filename = file.getName();
        file = null;
        
        String filetype = filepath.split("\\.")[1];
        System.out.println("Filetype: " + filetype + "\nFilename: " + filename);

        switch (filetype) {
            case "bin":
                return loadBIN(new FileInputStream(filepath));
            case "zip":
                return loadZIP(filepath, filename);
            case "osm":
                MapObjects mapObjects = osmParser.parseOSM(new FileInputStream(filepath));
                return new Model(mapObjects, filename);
            default:
                throw new IOException("Filetype not supported");
        }
    }

    private static Model loadZIP(String filepath, String filename) throws IOException, XMLStreamException, FactoryConfigurationError {
        ZipInputStream input = new ZipInputStream(new FileInputStream(filepath));
        input.getNextEntry();
        OSMParser osmParser = new OSMParser();
        MapObjects mapObjects = osmParser.parseOSM(input);
        input.close();
        return new Model(mapObjects,filename);
    }

    private Model(MapObjects mapObjects, String filename) throws FileNotFoundException, IOException {
        this.mapObjects = mapObjects;
        save(filename);
    }

    private static Model loadBIN(FileInputStream fileInputStream) throws IOException, ClassNotFoundException {
        try (var bin = new ObjectInputStream(new BufferedInputStream(fileInputStream))) {
            return (Model) bin.readObject();
        }
    }

    private void save(String filename) throws FileNotFoundException, IOException {
        new Thread(() -> {
            String fn = filename.split("\\.")[0] + ".bin";
            try {
                try (var out = new ObjectOutputStream(
                        new FileOutputStream(new URL(DefaultPath.getDefaultPath() + fn).getPath()))) {
                    out.writeObject(this);
                    System.out.println("Saved as: " + fn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
