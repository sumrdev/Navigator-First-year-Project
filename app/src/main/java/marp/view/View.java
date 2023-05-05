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
import javafx.stage.Stage;
import marp.model.Model;
import marp.view.gui.*;

public class View {
    public Stage primaryStage;
    public Model model;

    StackPane backgroundStackPane = new StackPane();

    public Scene viewScene;

   public ListView<String> listView;


    public Canvas canvas = new Canvas(1000, 700);
    public GraphicsContext gc = canvas.getGraphicsContext2D();
    public Affine trans = new Affine();
    
    public View(Stage primaryStage, Model model){
        this.primaryStage = primaryStage;
        this.model = model;

        this.listView = new ListView<String>();
        
        Text appName = new Text("MARP");
        primaryStage.setTitle("MARP");
        appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));
        appName.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(appName, Pos.TOP_CENTER);

        Scene mainScene = new Scene(backgroundStackPane, 1000, 700);
        ChooseMapScene chooseMapScene = new ChooseMapScene(primaryStage, mainScene, listView, this, model);
        chooseMapScene.start();
    }

    public void pan(float dx, float dy){

    }

    public void zoom(double dx, double dy){

    }

    public Point2D mouseToModel(float x, float y){
        return null;
    }

    public Canvas getCanvas(){
        return null;

    }

    public MapMenu getMapMenu(){
        return null;
    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }

    public Scene getViewScene(){
        return this.viewScene;
    }

}
