package marp.mapelements.details;

import javafx.scene.image.Image;

import java.util.Objects;

public enum PointType {
    SHOP(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/shop.png"))), "Shop"),
    BUS_STOP(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/bus.png"))), "Bus stop"),
    METRO_STATION(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/metro.png"))), "Metro station"),
    TRAIN_STATION(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/train.png"))), "Train station"),
    PLACE_OF_WORSHIP(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/worship.png"))), "Place of worship"),
    RESTAURANT(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/restaurant.png"))), "Restaurant"),
    BAR(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/bar.png"))), "Bar"),
    CAFE(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/cafe.png"))), "Cafe"),
    BANK(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/bank.png"))), "Bank"),
    HEALTHCARE(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/healthcare.png"))), "Healthcare"),
    THEATRE(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/theatre.png"))), "Theatre"),
    TOILETS(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/toilets.png"))), "Toilets"),
    CUSTOM(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/custompoint.png"))), "Toilets"),
    FAVOURITE(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/heart.png"))), "Toilets"),
    SELECTED(new Image(Objects.requireNonNull(PointType.class.getResourceAsStream("/icons/custompoint.png"))), "Toilets"),
    ADDRESS(null, "Address"),
    UNDEFINED(null, null);

    public final Image icon;
    public final String typeName;
    PointType(Image icon, String typename) {
        this.icon = icon;
        this.typeName = typename;

    }
}
