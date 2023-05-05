package marp.view;
import java.io.File;
import javax.xml.parsers.FactoryConfigurationError;
import marp.model.Model;
import marp.view.GUIElements.MapButtonText;
import marp.view.GUIElements.MapLabel;
import marp.view.GUIElements.SearchBar;
import marp.datastructures.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChooseMapScene {

    StackPane backgroundStackPane = new StackPane();
    Button backButton;
    Stage stage;
    SearchBar searchBar;
    SimpleTrie simpleTrie;
    public ListView<String> fileList;
    public MapButtonText loadButton;
    MapButtonText defaultButton;
    FileChooser fileChooser;
    Model model;
    View view;

    public Scene loadOSMScene;

    public ChooseMapScene(Stage stage, Scene prevScene, ListView<String> listView, View view, Model model) {
        this.view = view;
        this.model = model;

        // Back button
        this.backButton = new Button("BACK");
        this.backButton.setMinSize(50, 50);
        this.backButton.setPrefSize(50, 50);
        this.backButton.setMaxSize(50, 50);
        this.backButton.getStylesheets().add("/CSS/settings.css");
        StackPane.setMargin(this.backButton, new Insets(5, 0, 0, 5));
        StackPane.setAlignment(this.backButton, Pos.TOP_LEFT);

        // App name
        MapLabel appName = new MapLabel("Select a map");
        // appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));

        // Load button
        this.loadButton = new MapButtonText("Load a new .osm file...");
        loadButton.setMinWidth(200);

        // Default button
        this.defaultButton = new MapButtonText("Load Denmark");
        defaultButton.setMinWidth(150);

        // Listview
        this.fileList = listView;
        fileList.setMaxWidth(400);
        fileList.getStylesheets().add("CSS/stylesheet.css");
        fileList.getStyleClass().add("file-list");

        
        // add all elements to a VBox
        VBox sceneContents = new VBox(appName, fileList, loadButton, defaultButton);
        sceneContents.setSpacing(50);
        sceneContents.setAlignment(Pos.CENTER);
        sceneContents.setStyle(
                "-fx-background-color:  linear-gradient(from 0%  0% to 100% 100%, #6c9ed1, #ececec, #6d9fd2);");
        sceneContents.setPrefWidth(1000);
        sceneContents.setPrefHeight(700);
        
        // Stackpane (background)
        // this.backgroundStackPane.setStyle("-fx-background-color: linear-gradient(from
        // 0% 0% to 100% 100%, #ff00ea, #fcfcfc, #878787);");
        // this.backgroundStackPane.getChildren().addAll(appName, this.backButton,
        // this.loadButton, this.fileList);
        loadOSMScene = new Scene(sceneContents);
        
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
              //  String getFilePath = dragBoard.getUrl().replaceAll("file\\:", "");
               //File file = new File(getFilePath);
                //view.resetAffine();
                //this.model = model.receiveFile(file);
                view.model = this.model;
                //view.setSceneToCanvas(loadOSMScene.getWidth(), loadOSMScene.getHeight());
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorMsg = new Alert(AlertType.ERROR);
                errorMsg.setTitle("Unable to load file!");
                errorMsg.setContentText("The file you tried to drag and drop could not be loaded! Please try again.");
                
                errorMsg.showAndWait();
            }
        });

        this.defaultButton.setOnAction(e -> {
           // File file = new File("/Denmark/cph.osm");
            //view.resetAffine();
            
            view.model = this.model;
            //view.setSceneToCanvas(loadOSMScene.getWidth(), loadOSMScene.getHeight());
        });
        
        this.backButton.setOnAction(e -> {
            System.out.println("Pressed back button");
            this.stage.setTitle("MARP");
            this.stage.setScene(loadOSMScene);
            this.stage.show();
        });
        
        this.loadButton.setOnAction(e -> {
            fileChooser = new FileChooser();
            try {
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("OSM files", "*.osm"),
                    new FileChooser.ExtensionFilter("BIN files", "*.bin"),
                    new FileChooser.ExtensionFilter("All files", "*.*"));
                    
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile == null) {

                } else {
                    try {
                        System.out.println(selectedFile.getAbsolutePath());
                        //view.resetAffine();
                        //this.model = model.receiveFile(selectedFile);
                        view.model = this.model;
                        //view.setSceneToCanvas(loadOSMScene.getWidth(), loadOSMScene.getHeight());
                    } catch (FactoryConfigurationError e2) {
                        
                        e2.printStackTrace();
                    }
                }
            } catch (Exception e1) {
                // TODO: handle exception
                System.out.println("Error getting items from listview: ");
                e1.printStackTrace();
            }
        });
        
        // StackPane.setMargin(this.searchBar,
        // new Insets(150, loadOSMScene.getWidth() / 3, 0, loadOSMScene.getWidth() /
        // 3));
        // StackPane.setMargin(enterSearch, new Insets(137, loadOSMScene.getWidth() / 2
        // - 240, 0, 0));
        // StackPane.setMargin(this.fileList,
        // new Insets(loadOSMScene.getHeight() * 0.6, 5, 5, loadOSMScene.getWidth() *
        // 0.75));
        // StackPane.setMargin(this.loadButton, new Insets(loadOSMScene.getHeight() *
        // 0.4, 0, 0, 0));
        
        this.stage = stage;
    }

    public void start(){
        this.stage.setTitle("Choose how to load map");
        this.stage.setScene(loadOSMScene);
        this.stage.show();
    }
}