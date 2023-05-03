package marp;

import javafx.scene.paint.Color;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

public class View {
    Canvas canvas = new Canvas(640,480);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    
    Affine translation = new Affine();

    Model model;
    
    View(Model model, Stage primaryStage){
        this.model = model;
        primaryStage.setTitle("marp map");
        BorderPane pane = new BorderPane(canvas);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void draw(){
        gc.setTransform(new Affine());
        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1/translation.getMxx());

        
    }
}
