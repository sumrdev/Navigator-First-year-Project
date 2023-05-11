package marp.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import marp.model.Model;
import marp.view.gui.NearestRoadInfo;
import marp.view.gui.ZoomMenu;
import marp.view.gui.menugui.MapMenu;

public class View {
    private Stage primaryStage;
    private Model model;
    private MapScene mapScene;
    private MapMenu mapMenu;
    private ZoomMenu zoomMenu;
    private Canvas canvas;
    private ChooseMapScene chooseMapScene;
    private ListView<String> listView;
    private NearestRoadInfo nearestRoadInfo;

    public View(Stage primaryStage, Model model) {
        this.primaryStage = primaryStage;
        this.model = model;

        listView = new ListView<>();

        Text appName = new Text("Navigator");
        appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));
        appName.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(appName, Pos.TOP_CENTER);
        

        chooseMapScene = new ChooseMapScene(model, listView, this);
        primaryStage.setTitle("Navigator");
        primaryStage.setScene(chooseMapScene);
        primaryStage.show();
    }

    public void creatMenusForMapScene(Model model){
        this.mapMenu = new MapMenu(model);
        this.canvas = new Canvas(1000, 700);
        this.zoomMenu = new ZoomMenu(1000);
        this.nearestRoadInfo = new NearestRoadInfo();
        createNewMapScene(model);
        
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
    }

    public void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public MapScene getMapScene() {
        return mapScene;
    }

    public ChooseMapScene getChooseMapScene() {
        return chooseMapScene;
    }

    public void createNewMapScene(Model model) {
        mapScene = new MapScene(model, mapMenu, zoomMenu, nearestRoadInfo, canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ZoomMenu getZoomMenu() {
        return zoomMenu;
    }

    public MapMenu getMapMenu() {
        return mapMenu;
    }
    public NearestRoadInfo getNearestRoadInfo() {
        return nearestRoadInfo;
    }

    public ListView<String> getListView() {
        return listView;
    }

    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    public void setChooseMapScene(ChooseMapScene chooseMapScene) {
        this.chooseMapScene = chooseMapScene;
    }

    public void setMapScene(MapScene mapScene) {
        this.mapScene = mapScene;
    }

    public void setMapMenu(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public void setZoomMenu(ZoomMenu zoomMenu) {
        this.zoomMenu = zoomMenu;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setNearestRoadInfo(NearestRoadInfo nearestRoadInfo) {
        this.nearestRoadInfo = nearestRoadInfo;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
