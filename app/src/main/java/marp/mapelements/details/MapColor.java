package marp.mapelements.details;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class MapColor {
    private static MapColor instance = new MapColor();
    private HashMap<String, Color> defaultColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> darkColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> deuteranopiaBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> protanopiaBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> tritanBlindColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> monochromaBlindColorMap = new HashMap<String, Color>();
    public HashMap<String, Color> colorMap = new HashMap<String, Color>();

    private MapColor() {
        // Default colors
        defaultColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        defaultColorMap.put("WATER", Color.rgb(176, 211, 232,1));
        defaultColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        defaultColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        defaultColorMap.put("GRASSY", Color.rgb(206, 234, 214,1));
        defaultColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        defaultColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        defaultColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        defaultColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        defaultColorMap.put("SECONDARY", Color.WHITE);
        defaultColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("MOTORWAY", Color.YELLOW);
        defaultColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        defaultColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        defaultColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        defaultColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        defaultColorMap.put("FOOTPATH", Color.GREEN);
        defaultColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        defaultColorMap.put("RESIDENTIAL", Color.WHITE);
        defaultColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("TERTIARY", Color.WHITE);
        defaultColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        
        // Dark mode
        darkColorMap.put("BACKGROUND", Color.rgb(40, 40, 40,1));
        darkColorMap.put("WATER", Color.rgb(1, 8, 107,1));
        darkColorMap.put("BUILDING", Color.rgb(51, 51, 51,1));
        darkColorMap.put("COASTLINE", Color.rgb(52, 109, 83,1));
        darkColorMap.put("GRASSY", Color.rgb(117, 151, 126,1));
        darkColorMap.put("FOREST", Color.rgb(45, 98, 64,1));
        darkColorMap.put("CEMENT", Color.rgb(180, 180, 180,1));
        darkColorMap.put("FARMLAND", Color.web("#687F4B"));
        darkColorMap.put("COMMERCIAL_GROUND", Color.web("#AFAF8D"));
        darkColorMap.put("SECONDARY", Color.BLACK);
        darkColorMap.put("SECONDARY_OUTLINE", Color.DARKGRAY);
        darkColorMap.put("MOTORWAY", Color.web("#414100"));
        darkColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        darkColorMap.put("PEDESTRIAN", Color.web("#AFAFAF"));
        darkColorMap.put("PEDESTRIAN_OUTLINE", Color.DARKGRAY);
        darkColorMap.put("PRIMARY", Color.web("#414100"));
        darkColorMap.put("PRIMARY_OUTLINE", Color.web("#B4B400"));
        darkColorMap.put("FOOTPATH", Color.web("#003400"));
        darkColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        darkColorMap.put("RESIDENTIAL", Color.LIGHTGRAY);
        darkColorMap.put("RESIDENTIAL_OUTLINE", Color.DARKGRAY);
        darkColorMap.put("TERTIARY", Color.LIGHTGRAY);
        darkColorMap.put("TERTIARY_OUTLINE", Color.DARKGRAY);

        // Green blindness
        deuteranopiaBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        deuteranopiaBlindColorMap.put("WATER", Color.rgb(176, 211, 232,1));
        deuteranopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("COASTLINE", Color.web("#CEBCD6"));
        deuteranopiaBlindColorMap.put("GRASSY", Color.web("#CEC4D6"));
        deuteranopiaBlindColorMap.put("FOREST", Color.web("#917A9F"));
        deuteranopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        deuteranopiaBlindColorMap.put("FARMLAND", Color.web("#CCABAF"));
        deuteranopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        deuteranopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("MOTORWAY", Color.YELLOW);
        deuteranopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        deuteranopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        deuteranopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        deuteranopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        deuteranopiaBlindColorMap.put("FOOTPATH", Color.PURPLE);
        deuteranopiaBlindColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        deuteranopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        deuteranopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);

        // Red blindness
        protanopiaBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        protanopiaBlindColorMap.put("WATER", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("GRASSY", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        protanopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        protanopiaBlindColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        protanopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        protanopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        protanopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("MOTORWAY", Color.YELLOW);
        protanopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.PURPLE);
        protanopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        protanopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        protanopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        protanopiaBlindColorMap.put("FOOTPATH", Color.GREEN);
        protanopiaBlindColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        protanopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        protanopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        protanopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);

        // Blue blindness
        tritanBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        tritanBlindColorMap.put("WATER", Color.web("#D5D3DD"));
        tritanBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("GRASSY", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        tritanBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        tritanBlindColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        tritanBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("SECONDARY", Color.WHITE);
        tritanBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("MOTORWAY", Color.YELLOW);
        tritanBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        tritanBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        tritanBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        tritanBlindColorMap.put("FOOTPATH", Color.GREEN);
        tritanBlindColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        tritanBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        tritanBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TERTIARY", Color.WHITE);
        tritanBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);

        // Grey-scale
        monochromaBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("WATER", Color.web("#D3D3D3"));
        monochromaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("COASTLINE", Color.web("#E4E4E4"));
        monochromaBlindColorMap.put("GRASSY", Color.web("#C1C1C1"));
        monochromaBlindColorMap.put("FOREST", Color.web("#6E6E6E"));
        monochromaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        monochromaBlindColorMap.put("FARMLAND", Color.web("#D0CFCF"));
        monochromaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("SECONDARY", Color.WHITE);
        monochromaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("MOTORWAY", Color.web("#F2F1F1"));
        monochromaBlindColorMap.put("MOTORWAY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        monochromaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("FOOTPATH", Color.web("#5E5E5E"));
        monochromaBlindColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        monochromaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        monochromaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TERTIARY", Color.WHITE);
        monochromaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);

        colorMap = defaultColorMap;
    }

    public void changeTheme(String theme) {
        switch (theme) {
            case "default":
                colorMap = defaultColorMap;
                break;
            case "dark":
                colorMap = darkColorMap;
                break;
            case "deuteranopia":
                colorMap = deuteranopiaBlindColorMap;
                break;
            case "protanopia":
                colorMap = protanopiaBlindColorMap;
                break;
            case "tritanopia":
                colorMap = tritanBlindColorMap;
                break;
            case "monochromia":
                colorMap = monochromaBlindColorMap;
                break;
            default:
                colorMap = defaultColorMap;
                break;
        }
    }

    public static MapColor getInstance() {
        return instance;
    }

}
