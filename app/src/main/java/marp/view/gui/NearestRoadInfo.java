package marp.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class NearestRoadInfo extends HBox {
    MapLabelSmall roadName;

    public NearestRoadInfo() {
        roadName = new MapLabelSmall("");
        this.getChildren().addAll((new MapLabelSmall("Nearest road: ")), roadName);
        // Set the mouse transparent property of the map menu only for transparent
        // pixels
        this.setPickOnBounds(false);
        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setPadding(new Insets(10));
    }

    public void setRoadNameText(String roadName) {
        this.roadName.setText(roadName);
    }

}
