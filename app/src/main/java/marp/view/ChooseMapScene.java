package marp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapTextButton;

public class ChooseMapScene extends Scene{
    public MapTextButton loadButton;
    private Model model;

    public ChooseMapScene(Model model) {
        super(new VBox());
        this.model = model;

        // Header
        MapLabel header = new MapLabel("Select a map");

        // Listview
        ListView<String> fileList = new ListView<>();
        fileList.getItems().addAll(model.getFileList());
        fileList.setMaxWidth(400);
        fileList.getStylesheets().add("CSS/stylesheet.css");
        fileList.getStyleClass().add("file-list");

        // Load button
        this.loadButton = new MapTextButton("Load a new .osm file...");
        loadButton.setMinWidth(200);

        // add all elements to a VBox
        VBox sceneContents = new VBox(header, fileList, loadButton);
        sceneContents.setSpacing(50);
        sceneContents.setAlignment(Pos.CENTER);
        sceneContents.setStyle("-fx-background-color:  linear-gradient(from 0%  0% to 100% 100%, #6c9ed1, #ececec, #6d9fd2);");
        sceneContents.setPrefWidth(1000);
        sceneContents.setPrefHeight(700);

        // add contents to scene
        this.setRoot(sceneContents);
    }
}
