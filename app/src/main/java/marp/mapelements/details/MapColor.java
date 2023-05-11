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
        defaultColorMap.put("BACKGROUND", Color.rgb(176, 211, 232));
        defaultColorMap.put("WATER", Color.rgb(176, 211, 232));
        defaultColorMap.put("BUILDING", Color.rgb(190, 190, 190));
        defaultColorMap.put("COASTLINE", Color.rgb(206, 234, 214));
        defaultColorMap.put("coastline_near", Color.rgb(255, 255, 255));
        defaultColorMap.put("coastline_far", Color.rgb(206, 234, 214));
        defaultColorMap.put("GRASS", Color.rgb(206, 234, 214));
        defaultColorMap.put("FOREST", Color.rgb(145, 189, 159));
        defaultColorMap.put("CEMENT", Color.rgb(233, 233, 233));
        defaultColorMap.put("FARMLAND", Color.rgb(204, 227, 175));
        defaultColorMap.put("COMMERCIAL_GROUND", Color.rgb(225, 224, 212));
        defaultColorMap.put("WATERWAY", Color.rgb(176, 211, 232));
        defaultColorMap.put("RAILWAY", Color.rgb(190, 190, 190));
        defaultColorMap.put("SECONDARY", Color.WHITE);
        defaultColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("MOTORWAY", Color.YELLOW);
        defaultColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        defaultColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230));
        defaultColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        defaultColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        defaultColorMap.put("PATH", Color.GREEN);
        defaultColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        defaultColorMap.put("RESIDENTIAL", Color.WHITE);
        defaultColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("TERTIARY", Color.WHITE);
        defaultColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        defaultColorMap.put("TEXT_OUTLINE", Color.WHITE);
        defaultColorMap.put("TEXT", Color.BLACK);

        // Dark mode
        darkColorMap.put("BACKGROUND", Color.rgb(68, 87, 101,1));
        darkColorMap.put("WATER", Color.rgb(68, 87, 101,1));
        darkColorMap.put("BUILDING", Color.rgb(51, 51, 51,1));
        darkColorMap.put("COASTLINE", Color.rgb(83, 115, 99,1));
        darkColorMap.put("coastline_near", Color.rgb(115, 115, 115,1));
        darkColorMap.put("coastline_far", Color.rgb(83, 115, 99,1));
        darkColorMap.put("GRASS", Color.rgb(83, 115, 99,1));
        darkColorMap.put("FOREST", Color.rgb(67, 94, 81,1));
        darkColorMap.put("CEMENT", Color.rgb(105, 105, 105,1));
        darkColorMap.put("FARMLAND", Color.web("#687F4B"));
        darkColorMap.put("COMMERCIAL_GROUND", Color.rgb(105, 102, 96,1));
        darkColorMap.put("WATERWAY", Color.rgb(68, 87, 101,1));
        darkColorMap.put("RAILWAY", Color.rgb(80, 80, 80,1));
        darkColorMap.put("SECONDARY", Color.rgb(145, 145, 145,1));
        darkColorMap.put("SECONDARY_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("MOTORWAY", Color.rgb(112, 68, 68,1));
        darkColorMap.put("MOTORWAY_OUTLINE", Color.rgb(96, 51, 51,1));
        darkColorMap.put("PEDESTRIAN", Color.rgb(105, 105, 105,1));
        darkColorMap.put("PEDESTRIAN_OUTLINE", Color.rgb(85, 85, 85,1));
        darkColorMap.put("PRIMARY", Color.rgb(131, 103, 103,1));
        darkColorMap.put("PRIMARY_OUTLINE", Color.rgb(112, 68, 68,1));
        darkColorMap.put("PATH", Color.rgb(96, 51, 51,1));
        darkColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        darkColorMap.put("RESIDENTIAL", Color.rgb(145, 145, 145,1));
        darkColorMap.put("RESIDENTIAL_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("TERTIARY", Color.rgb(145, 145, 145,1));
        darkColorMap.put("TERTIARY_OUTLINE", Color.rgb(105, 105, 105,1));
        darkColorMap.put("TEXT_OUTLINE", Color.rgb(51, 51, 51,1));
        darkColorMap.put("TEXT", Color.WHITE);

        // Green blindness
        deuteranopiaBlindColorMap.put("BACKGROUND", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("WATER", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("COASTLINE", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        deuteranopiaBlindColorMap.put("coastline_far", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("GRASS", Color.rgb(203, 196, 220,1));
        deuteranopiaBlindColorMap.put("FOREST", Color.rgb(167, 161, 190,1));
        deuteranopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        deuteranopiaBlindColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        deuteranopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.rgb(225, 224, 212,1));
        deuteranopiaBlindColorMap.put("WATERWAY", Color.rgb(187, 169, 149,1));
        deuteranopiaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        deuteranopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("MOTORWAY", Color.YELLOW);
        deuteranopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        deuteranopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        deuteranopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        deuteranopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        deuteranopiaBlindColorMap.put("PATH", Color.rgb(69, 65, 96,1));
        deuteranopiaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        deuteranopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        deuteranopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        deuteranopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        deuteranopiaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        deuteranopiaBlindColorMap.put("TEXT", Color.BLACK);

        // Red blindness
        protanopiaBlindColorMap.put("BACKGROUND", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("WATER", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        protanopiaBlindColorMap.put("coastline_far", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("GRASS", Color.rgb(206, 234, 214,1));
        protanopiaBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        protanopiaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        protanopiaBlindColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        protanopiaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        protanopiaBlindColorMap.put("WATERWAY", Color.rgb(176, 211, 232,1));
        protanopiaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        protanopiaBlindColorMap.put("SECONDARY", Color.WHITE);
        protanopiaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("MOTORWAY", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("MOTORWAY_OUTLINE", Color.rgb(201, 136, 142,1));
        protanopiaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        protanopiaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("PRIMARY", Color.rgb(245, 216, 217,1));
        protanopiaBlindColorMap.put("PRIMARY_OUTLINE", Color.rgb(234, 176, 183,1));
        protanopiaBlindColorMap.put("PATH", Color.GREEN);
        protanopiaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        protanopiaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        protanopiaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TERTIARY", Color.WHITE);
        protanopiaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        protanopiaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        protanopiaBlindColorMap.put("TEXT", Color.BLACK);

        // Blue blindness
        tritanBlindColorMap.put("BACKGROUND", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("WATER", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("COASTLINE", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        tritanBlindColorMap.put("coastline_far", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("GRASS", Color.rgb(206, 234, 214,1));
        tritanBlindColorMap.put("FOREST", Color.rgb(145, 189, 159,1));
        tritanBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        tritanBlindColorMap.put("FARMLAND", Color.rgb(204, 227, 175,1));
        tritanBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("WATERWAY", Color.rgb(203, 196, 220,1));
        tritanBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        tritanBlindColorMap.put("SECONDARY", Color.WHITE);
        tritanBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("MOTORWAY", Color.YELLOW);
        tritanBlindColorMap.put("MOTORWAY_OUTLINE", Color.ORANGE);
        tritanBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        tritanBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("PRIMARY", Color.LIGHTYELLOW);
        tritanBlindColorMap.put("PRIMARY_OUTLINE", Color.YELLOW);
        tritanBlindColorMap.put("PATH", Color.GREEN);
        tritanBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        tritanBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        tritanBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TERTIARY", Color.WHITE);
        tritanBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        tritanBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        tritanBlindColorMap.put("TEXT", Color.BLACK);

        // Grey-scale
        monochromaBlindColorMap.put("BACKGROUND", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("WATER", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("BUILDING", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("COASTLINE", Color.web("#E4E4E4"));
        monochromaBlindColorMap.put("coastline_near", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("coastline_far", Color.rgb(214, 214, 214,1));
        monochromaBlindColorMap.put("GRASS", Color.rgb(214, 214, 214,1));
        monochromaBlindColorMap.put("FOREST", Color.web("#6E6E6E"));
        monochromaBlindColorMap.put("CEMENT", Color.rgb(233, 233, 233,1));
        monochromaBlindColorMap.put("FARMLAND", Color.web("#D0CFCF"));
        monochromaBlindColorMap.put("COMMERCIAL_GROUND", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("WATERWAY", Color.rgb(255, 255, 255,1));
        monochromaBlindColorMap.put("RAILWAY", Color.rgb(190, 190, 190,1));
        monochromaBlindColorMap.put("SECONDARY", Color.WHITE);
        monochromaBlindColorMap.put("SECONDARY_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("MOTORWAY", Color.web("#F2F1F1"));
        monochromaBlindColorMap.put("MOTORWAY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("PEDESTRIAN", Color.rgb(230, 230, 230,1));
        monochromaBlindColorMap.put("PEDESTRIAN_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("PRIMARY_OUTLINE", Color.DARKGRAY);
        monochromaBlindColorMap.put("PATH", Color.web("#5E5E5E"));
        monochromaBlindColorMap.put("PATH_OUTLINE", Color.TRANSPARENT);
        monochromaBlindColorMap.put("RESIDENTIAL", Color.WHITE);
        monochromaBlindColorMap.put("RESIDENTIAL_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TERTIARY", Color.WHITE);
        monochromaBlindColorMap.put("TERTIARY_OUTLINE", Color.LIGHTGRAY);
        monochromaBlindColorMap.put("TEXT_OUTLINE", Color.WHITE);
        monochromaBlindColorMap.put("TEXT", Color.rgb(30, 30,30,1));

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
    public void updateCoastlineColorFromZoom(double zoomLevel) {
        Color coastlineNear = colorMap.get("coastline_near");
        Color coastlineFar = colorMap.get("coastline_far");

        if (zoomLevel < 300) {
            colorMap.put("COASTLINE", coastlineNear);
        } else if (zoomLevel > 900) {
            colorMap.put("COASTLINE", coastlineFar);
        } else {
            double t = (zoomLevel - 300) / 600.0;
            System.out.println("FACTOR t: " + t);

            Color interpolatedColor = coastlineNear.interpolate(coastlineFar, t);
            colorMap.put("COASTLINE", interpolatedColor);

        }
    }


    public static MapColor getInstance() {
        return instance;
    }

}
