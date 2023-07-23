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
import marp.utilities.IOFunctions;
import marp.view.gui.MapLabel;
import marp.view.gui.buttons.MapTextButton;

public class ChooseMapScene extends Scene {
    private MapTextButton loadDefaultBinaryButton;
    private MapTextButton chooseOwnFileButton;
    private Model model;
    private ListView<String> filelist;

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
            String trimURL = "";
            if (dragBoard.hasUrl()) {
                trimURL = dragBoard.getUrl().substring(6);
                System.out.println("Received file: " + trimURL);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            try {
                File file = new File(trimURL);
                URL fileToURL = file.toURI().toURL();
                Model.updateModel(fileToURL);
                filelist.getItems().add(file.getName().replace(".osm", ".bin"));
                // view.creatMenusForMapScene(Model.getInstance());
                // view.setScene(view.getMapScene());
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorMsg = new Alert(AlertType.ERROR);
                errorMsg.setTitle("Unable to read file!");
                errorMsg.setContentText("The file you tried to drag and drop could not be added! Please try again.");

                errorMsg.showAndWait();
            }

        });

        // Load button
        this.loadDefaultBinaryButton = new MapTextButton("Load default binary file");
        this.chooseOwnFileButton = new MapTextButton("Choose your own OSM file");
        this.chooseOwnFileButton.setMinWidth(200);
        HBox buttonContainer = new HBox(this.loadDefaultBinaryButton, this.chooseOwnFileButton);
        buttonContainer.setSpacing(50);
        buttonContainer.setAlignment(Pos.CENTER);
        loadDefaultBinaryButton.setMinWidth(200);

        // add all elements to a VBox
        VBox sceneContents = new VBox(header, filelist, buttonContainer);
        sceneContents.setSpacing(50);
        sceneContents.setAlignment(Pos.CENTER);
        sceneContents.setStyle(
                "-fx-background-color:  linear-gradient(from 0%  0% to 100% 100%, #6c9ed1, #ececec, #6d9fd2);");
        sceneContents.setPrefWidth(1000);
        sceneContents.setPrefHeight(700);

        // add contents to scene
        this.setRoot(sceneContents);
    }

    public MapTextButton getLoadDefaultBinaryButton() {
        return loadDefaultBinaryButton;
    }

    public MapTextButton getChooseOwnFileButton() {
        return chooseOwnFileButton;
    }
}
