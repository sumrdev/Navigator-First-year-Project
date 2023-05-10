package marp.color;
import java.util.HashMap;

import javafx.scene.paint.Color;

public class ElementColor {

    private HashMap<String, Color> activeColors = new HashMap<>();
    private HashMap<String, Color> defaultColors = new HashMap<>();
    private HashMap<String, Color> darkColors = new HashMap<>();
    private HashMap<String, Color> colorBlindColors = new HashMap<>();
    
    private static ElementColor instance = null;
    private ElementColor(){
        defaultColors.put("water", Color.web("#9cc0f9"));
        defaultColors.put("building", Color.web("#e5e7e9"));
        defaultColors.put("grass", Color.web("#bde2c8"));
        defaultColors.put("coastline", Color.web("#eceef0"));
        defaultColors.put("forest", Color.web("#94d2a5"));
        defaultColors.put("cement", Color.web("#e8eaed"));
        defaultColors.put("commercial", Color.web("#fef7e0"));
        defaultColors.put("farmland", Color.web("#34a853"));
        defaultColors.put("motorway", Color.web("#fde293"));
        defaultColors.put("primary", Color.web("#ffffff"));
        defaultColors.put("primary_outline", Color.web("#e6e7ea"));
        defaultColors.put("tertiary", Color.web("#cff1ff"));
        defaultColors.put("tertiary_outline", Color.web("#a5c0cc"));
        defaultColors.put("residential", Color.web("#ffe2de"));
        defaultColors.put("residential_outline", Color.web("#ffe2de"));
        defaultColors.put("pedestrian", Color.web("#33a351"));
        defaultColors.put("footpath", Color.web("#2ea14d"));
;
        darkColors.put("water", Color.web("#000000"));
        darkColors.put("building", Color.web("#1e1e1e"));
        darkColors.put("grass", Color.web("#105a5b"));
        darkColors.put("coastline", Color.web("#222222"));
        darkColors.put("forest", Color.web("#0b645c"));
        darkColors.put("cement", Color.web("#222222"));
        darkColors.put("commercial", Color.web("#222222"));
        darkColors.put("farmland", Color.web("#222222"));
        darkColors.put("motorway", Color.web("#fde293"));
        darkColors.put("primary", Color.web("#ffffff"));
        darkColors.put("primary_outline", Color.web("#e6e7ea"));
        darkColors.put("tertiary", Color.web("#cff1ff"));
        darkColors.put("tertiary_outline", Color.web("#a5c0cc"));
        darkColors.put("residential", Color.web("#ffe2de"));
        darkColors.put("residential_outline", Color.web("#ffe2de"));
        darkColors.put("pedestrian", Color.web("#33a351"));
        darkColors.put("footpath", Color.web("#2ea14d"));
        darkColors.put("path", Color.web("#2ea14d"));

        activeColors = defaultColors;
    }   

    public void setTheme(String theme){
        switch (theme) {
            case "dark":
                activeColors = darkColors;
                break;
            
            case "default":
                activeColors = defaultColors;
                break;
            default:
                break;
        }
    }

    public static ElementColor getInstance(){
        if(instance == null){
            instance = new ElementColor();
        }
        return instance;
    }
}
