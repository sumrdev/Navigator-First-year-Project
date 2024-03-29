package marp.core;
import java.io.File;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.image.Image;
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
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/logo.png")));
        Model model = Model.getInstance();
        View view = new View(primaryStage, model);
        Controller controller = new Controller(view, model);

        File mapsFolder = Paths.get("data/maps").toFile();
        if(!mapsFolder.exists()){
            mapsFolder.mkdirs();
        }
    }
}