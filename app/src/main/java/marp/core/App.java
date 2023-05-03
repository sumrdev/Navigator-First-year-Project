package marp.core;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import marp.model.Model;
public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String defaultFilename = "bornholm.osm";
        URL defaultFileURL = getClass().getClassLoader().getResource(defaultFilename);
        Model model = new Model(defaultFileURL);
    }
}