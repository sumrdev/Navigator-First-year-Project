package marp.view;

import java.io.File;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import marp.model.Model;
import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapTextButton;

public class ChooseMapScene extends Scene{
    public MapTextButton loadButton;
    public MapTextButton chooseOwnFileButton;
    private Model model;
    ListView<String> filelist;

    public ChooseMapScene(Model model, ListView<String> listView, View view) {
        super(new VBox());
        this.model = model;
        
        // Header
        MapLabel header = new MapLabel("Select a map");
        
        // Listview
        this.filelist = listView;
        filelist.setMaxWidth(400);
        filelist.getStylesheets().add("CSS/stylesheet.css");
        filelist.getStyleClass().add("file-list");

        filelist.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getDragboard().hasUrl()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });
        
        filelist.setOnDragDropped((DragEvent event) -> {
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
                this.model = Model.createModel(fileToURL);
                view.creatMenusForMapScene();
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
        this.loadButton = new MapTextButton("Load default binary file");
        this.chooseOwnFileButton = new MapTextButton("Choose your own OSM file");
        this.chooseOwnFileButton.setMinWidth(200);
        HBox buttonContainer = new HBox(this.loadButton, this.chooseOwnFileButton);
        buttonContainer.setSpacing(50);
        buttonContainer.setAlignment(Pos.CENTER);
        loadButton.setMinWidth(200);


        // add all elements to a VBox
        VBox sceneContents = new VBox(header, filelist, buttonContainer);
        sceneContents.setSpacing(50);
        sceneContents.setAlignment(Pos.CENTER);
        sceneContents.setStyle("-fx-background-color:  linear-gradient(from 0%  0% to 100% 100%, #6c9ed1, #ececec, #6d9fd2);");
        sceneContents.setPrefWidth(1000);
        sceneContents.setPrefHeight(700);

        // add contents to scene
        this.setRoot(sceneContents);
    }
}
