package marp.view;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;
import marp.model.Model;
import marp.view.gui.ZoomMenu;
import marp.view.gui.menugui.MapMenu;

public class View {
    public Stage primaryStage;
    public Model model;
    private MapScene mapScene;
    private final MapMenu mapMenu;
    private final ZoomMenu zoomMenu;
    private final Canvas canvas;
    public ChooseMapScene chooseMapScene;

    public View(Stage primaryStage, Model model){
        this.primaryStage = primaryStage;
        this.model = model;

        Text appName = new Text("Navigator");
        appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));
        appName.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(appName, Pos.TOP_CENTER);
        mapMenu = new MapMenu(model);
        canvas = new Canvas(1000, 700);
        zoomMenu = new ZoomMenu(100);
        //bind canvas width + height to screen size
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        chooseMapScene = new ChooseMapScene(model);
        primaryStage.setTitle("Navigator");
        primaryStage.setScene(chooseMapScene);
        primaryStage.show();
        
        this.primaryStage.show();
    }
    public void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }
    public Stage getPrimaryStage(){
        return this.primaryStage;
    }

    public MapScene getMapScene() {
        return mapScene;
    }
    public ChooseMapScene getChooseMapScene() {
        return chooseMapScene;
    }

    public void createNewMapScene() {
        mapScene = new MapScene(model, mapMenu, canvas);
        // Bind the size of the map to the size of the window and check if window is resized, then redraw

    }
    public Canvas getCanvas(){
        return canvas;
    }
    public ZoomMenu getZoomMenu() {
        return zoomMenu;
    }
    public MapMenu getMapMenu(){
        return mapMenu;
    }
}
