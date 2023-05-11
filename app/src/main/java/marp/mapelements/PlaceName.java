package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import marp.color.MapColor;
import marp.mapelements.details.FontSize;

public class PlaceName extends Point {
    String name;
    FontSize fontSize;

    public PlaceName(String name, FontSize fontSize, float x, float y) {
        super(x, y);
        this.name = name;
        this.fontSize = fontSize;
    }
    public void draw(GraphicsContext gc, float zoom) {
        gc.setFont(Font.font("Helvetica Neue", zoom * fontSize.getSize()));
        gc.setFill(MapColor.getInstance().colorMap.get("TEXT"));
        gc.setStroke(MapColor.getInstance().colorMap.get("TEXT_OUTLINE"));
        gc.setLineWidth(zoom*2);
        Text text = new Text(name);
        text.setFont(gc.getFont());
        double width = text.getLayoutBounds().getWidth(); //Get the width of the text
        double height = text.getLayoutBounds().getHeight(); //Get the height of the text
        gc.strokeText(name, x - (width / 2), y + (height / 2)); //Draw the text outline centered at (x, y)
        gc.fillText(name, x - (width / 2), y + (height / 2)); //Draw the text centered at (x, y)
    }
}