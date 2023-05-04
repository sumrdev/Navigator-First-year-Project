package marp.color;
import java.util.HashMap;

import javafx.scene.paint.Color;

public class ElementColor {

    private HashMap<String, Color> activeColors = new HashMap<>();
    private HashMap<String, Color> defaultColors = new HashMap<>();
    private HashMap<String, Color> darkColors = new HashMap<>();
    private HashMap<String, Color> colorBlindColors = new HashMap<>();
    
    private static ElementColor instance = null;
    private ElementColor(){}
    public static ElementColor getInstance(){
        if(instance == null){
            instance = new ElementColor();
        }
        return instance;
    }
}
