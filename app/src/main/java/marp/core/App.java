package marp.core;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import marp.controller.Controller;
import marp.model.Model;
import marp.utilities.DefaultPath;
import marp.view.View;
public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        try {
            String defaultFilename = "/bornholm.osm";
            URL defaultFileURL = new URL(DefaultPath.getDefaultPath()+defaultFilename);
            model = Model.createModel(defaultFileURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = new View(primaryStage, model);
        Controller controller = new Controller(view, model);
    }
}