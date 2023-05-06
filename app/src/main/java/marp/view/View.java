package marp.view;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;
import marp.mapelements.Point;
import marp.model.Model;
import marp.view.gui.MapMenu;

public class View {
    public Model model;

    StackPane backgroundStackPane = new StackPane();

    public Scene viewScene;

    public ListView<String> listView;


    public Canvas canvas = new Canvas(860, 600);
    public GraphicsContext gc = canvas.getGraphicsContext2D();
    Affine trans = new Affine();
    
    public View(Stage primaryStage, Model model){
        this.model = model;

        this.listView = new ListView<String>();
        
        Text appName = new Text("MARP");
        primaryStage.setTitle("MARP");
        appName.setFont(Font.font("Montserrat", FontWeight.SEMI_BOLD, 36));
        appName.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(appName, Pos.TOP_CENTER);
        
        BorderPane pane = new BorderPane(canvas);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);


        primaryStage.show();

        redraw();
        pan(-0.56f*model.getMapObjects().getMinX(), model.getMapObjects().getMaxY());
        zoom(0f, 0f,(float) canvas.getHeight() / (model.getMapObjects().getMaxY() - model.getMapObjects().getMinY()));
    }

    public void pan(float dx, float dy){
        trans.prependTranslation(dx, dy);
        redraw();
    }

    public void redraw(){
        gc.setTransform(new Affine());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setTransform(trans);
        gc.setLineWidth(1/Math.sqrt(trans.determinant()));
        Bounds bounds;
        try{
            bounds = trans.inverseTransform(canvas.getLayoutBounds());
        }  catch(NonInvertibleTransformException e){
            throw new RuntimeException(e);
        }
        
        for (Point point :  model.getMapObjects().pointTree.getElementsInRange(bounds)) {
            point.draw(gc, 0, 0);
        }
    }

    public void zoom(float dx, float dy, float factor){
        pan(-dx, -dy);
        trans.prependScale(factor, factor);
        pan(dx, dy);
        redraw();
    }

    public Point2D mousetoModel(float lastX, float lastY) {
        try {
            return trans.inverseTransform(lastX, lastY);
        } catch (NonInvertibleTransformException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

    }

    public Canvas getCanvas(){
        return null;

    }

    public MapMenu getMapMenu(){
        return null;
    }


    public Scene getViewScene(){
        return this.viewScene;
    }

}
