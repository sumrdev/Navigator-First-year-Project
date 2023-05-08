package marp.view.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NearestRoadInfo extends HBox {
    MapLabelSmall roadName;
    public NearestRoadInfo() {
        roadName = new MapLabelSmall("");
        this.getChildren().addAll((new MapLabelSmall( "Nearest road: ")), roadName);
        // Set the mouse transparent property of the map menu only for transparent pixels
        this.setPickOnBounds(false);
        this.setAlignment(Pos.BOTTOM_LEFT);
    }
    public void setRoadNameText(String roadName) {
        this.roadName.setText(roadName);
    }

}
