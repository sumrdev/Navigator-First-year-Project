package marp.mapelements.details;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class MapColor {
    private static MapColor instance = new MapColor();
    private HashMap<String, Color> defaultColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> darkColorMap = new HashMap<String, Color>();
    private HashMap<String, Color> colorBlindColorMap = new HashMap<String, Color>();
    public HashMap<String, Color> colorMap = new HashMap<String, Color>();

    private MapColor() {
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
        colorMap = defaultColorMap;

        darkColorMap.put("BACKGROUND", Color.rgb(44, 44, 46,1));
        darkColorMap.put("WATER", Color.rgb(45, 99, 140,1));
        darkColorMap.put("BUILDING", Color.rgb(87, 87, 87,1));
        darkColorMap.put("COASTLINE", Color.rgb(52, 90, 68,1));
        darkColorMap.put("GRASSY", Color.rgb(52, 90, 68,1));
        darkColorMap.put("FOREST", Color.rgb(41, 69, 55,1));
        darkColorMap.put("CEMENT", Color.rgb(67, 67, 69,1));
        darkColorMap.put("FARMLAND", Color.rgb(94, 116, 63,1));
        darkColorMap.put("COMMERCIAL_GROUND", Color.rgb(104, 98, 73,1));
        darkColorMap.put("SECONDARY", Color.rgb(166, 166, 168,1));
        darkColorMap.put("SECONDARY_OUTLINE", Color.rgb(102, 102, 102,1));
        darkColorMap.put("MOTORWAY", Color.rgb(221, 206, 66,1));
        darkColorMap.put("MOTORWAY_OUTLINE", Color.rgb(180, 93, 0,1));
        darkColorMap.put("PEDESTRIAN", Color.rgb(72, 72, 72,1));
        darkColorMap.put("PEDESTRIAN_OUTLINE", Color.rgb(102, 102, 102,1));
        darkColorMap.put("PRIMARY", Color.rgb(174, 165, 63,1));
        darkColorMap.put("PRIMARY_OUTLINE", Color.rgb(194, 175, 66,1));
        darkColorMap.put("FOOTPATH", Color.rgb(85, 125, 73,1));
        darkColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        darkColorMap.put("RESIDENTIAL", Color.rgb(166, 166, 168,1));
        darkColorMap.put("RESIDENTIAL_OUTLINE", Color.rgb(102, 102, 102,1));
        darkColorMap.put("TERTIARY", Color.rgb(166, 166, 168,1));
        darkColorMap.put("TERTIARY_OUTLINE", Color.rgb(102, 102, 102,1));

        colorBlindColorMap.put("BACKGROUND",  Color.rgb(255, 255, 255,1));
        colorBlindColorMap.put("WATER", Color.rgb(53, 104, 161, 1));
        colorBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        colorBlindColorMap.put("COASTLINE", Color.rgb(118, 137, 130, 1));
        colorBlindColorMap.put("GRASSY", Color.rgb(159, 203, 159, 1));
        colorBlindColorMap.put("FOREST", Color.rgb(102, 136, 102, 1));
        colorBlindColorMap.put("CEMENT", Color.rgb(225, 223, 221, 1));
        colorBlindColorMap.put("FARMLAND", Color.rgb(154, 174, 121, 1));
        colorBlindColorMap.put("COMMERCIAL_GROUND", Color.rgb(185, 175, 160, 1));
        colorBlindColorMap.put("SECONDARY", Color.rgb(182, 182, 182, 1));
        colorBlindColorMap.put("SECONDARY_OUTLINE", Color.rgb(128, 128, 128, 1));
        colorBlindColorMap.put("MOTORWAY", Color.rgb(188, 72, 72, 1));
        colorBlindColorMap.put("MOTORWAY_OUTLINE", Color.rgb(153, 57, 57, 1));
        colorBlindColorMap.put("PEDESTRIAN", Color.rgb(162, 162, 162, 1));
        colorBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.rgb(108, 108, 108, 1));
        colorBlindColorMap.put("PRIMARY", Color.rgb(140, 107, 177, 1));
        colorBlindColorMap.put("PRIMARY_OUTLINE", Color.rgb(112, 77, 145, 1));
        colorBlindColorMap.put("FOOTPATH", Color.rgb(153, 57, 57, 1));
        colorBlindColorMap.put("FOOTPATH_OUTLINE", Color.TRANSPARENT);
        colorBlindColorMap.put("RESIDENTIAL", Color.rgb(198, 198, 198, 1));
        colorBlindColorMap.put("ResidentialRoadOutline", Color.rgb(142, 142, 142, 1));
        colorBlindColorMap.put("TERTIARY", Color.rgb(198, 198, 198, 1));
        colorBlindColorMap.put("TERTIARY_OUTLINE", Color.rgb(142, 142, 142, 1));
    }

    public void changeTheme(String theme){
        switch(theme){
            case "default":
                colorMap = defaultColorMap;
                break;
            case "dark":
                colorMap = darkColorMap;
                break;
            case "colorblind":
                colorMap = colorBlindColorMap;
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
