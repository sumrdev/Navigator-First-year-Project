package marp.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marp.model.Model;
import marp.utilities.IOFunctions;
import marp.view.View;

public class Controller {
    Model model;
    View view;

    float lastX;
    float lastY;

    List<String> fileList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();

    Stage stage;
    Scene scene;

    double zoomFactor = 1.0;

    private float lastPressedX;
    private float lastPressedY;

    public Controller(View view, Model model) throws MalformedURLException{
        this.model = model;
        this.view = view;
        
        view.canvas.setOnMousePressed(e -> {
            lastX = (float) e.getX();
            lastY = (float) e.getY();
        });
        view.canvas.setOnMouseDragged(e -> {
            float dx = (float) (e.getX() - lastX);
            float dy = (float) (e.getY() - lastY);
            view.pan(dx, dy);

            lastX = (float) e.getX();
            lastY = (float) e.getY();
        });
        view.canvas.setOnScroll(e -> {
            double factor = e.getDeltaY();
            view.zoom( (float) e.getX(), (float) e.getY(), (float) Math.pow(1.01, factor));
        });
    }
  

    private void setFileChooser(){
        this.fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("OSM files", "*.osm"),
            new FileChooser.ExtensionFilter("BIN files", "*.bin"),
            new FileChooser.ExtensionFilter("ZIP files", "*.zip"),
            new FileChooser.ExtensionFilter("All files", "*.*"));
    }
}
