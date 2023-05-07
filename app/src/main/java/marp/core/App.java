package marp.core;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import marp.controller.Controller;
import marp.model.Model;
import marp.view.View;
public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String defaultFilename = "bornholm.osm";
        InputStream defaultFile = getClass().getResourceAsStream("/maps/"+defaultFilename);
        Model model = Model.createModel(defaultFile, defaultFilename);
        View view = new View(primaryStage, model);
        Controller controller = new Controller(view, model);

        //if no data/maps folder exists, create it
        File mapsFolder = Paths.get("data/maps").toFile();
        if(!mapsFolder.exists()){
            mapsFolder.mkdirs();
        }
    }
}