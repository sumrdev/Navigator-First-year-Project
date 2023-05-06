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
    public ChooseMapScene chooseMapScene;

    public View(Stage primaryStage, Model model){
        this.primaryStage = primaryStage;
        this.model = model;

        Text appName = new Text("Navigator");
        appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));
        appName.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(appName, Pos.TOP_CENTER);

        mapScene = new MapScene(model, this);
        chooseMapScene = new ChooseMapScene(model);
        primaryStage.setTitle("Navigator");
        primaryStage.setScene(mapScene);
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
}
