package marp.view;

import java.io.File;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.model.Model;
import marp.utilities.DefaultPath;
import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapTextButton;

public class ChooseMapScene extends Scene{
    public MapTextButton loadButton;
    private Model model;

    public ChooseMapScene(Model model, View view) {
        super(new VBox());
        this.model = model;


        // Header
        MapLabel header = new MapLabel("Select a map");

        // Listview
        ListView<String> fileList = new ListView<>();
        //fileList.getItems().addAll(model.getFileList());
        fileList.setMaxWidth(400);
        fileList.getStylesheets().add("CSS/stylesheet.css");
        fileList.getStyleClass().add("file-list");

        fileList.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getDragboard().hasUrl()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });
        
        fileList.setOnDragDropped((DragEvent event) -> {
            Dragboard dragBoard = event.getDragboard();
            if (dragBoard.hasUrl()) {
                System.out.println("Received file: " + dragBoard.getUrl());
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
            try {
                String dragBoardUrl = dragBoard.getUrl().replaceAll("file\\:", "");
                File file = new File(dragBoardUrl);
                URL fileToURL = file.toURI().toURL();
                Model.createModel(fileToURL);
                view.createNewMapScene();
                view.setScene(view.getMapScene());
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorMsg = new Alert(AlertType.ERROR);
                errorMsg.setTitle("Unable to load file!");
                errorMsg.setContentText("The file you tried to drag and drop could not be loaded! Please try again.");
                
                errorMsg.showAndWait();
            }
        });

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
