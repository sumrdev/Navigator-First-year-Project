package marp.mapelements;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import marp.mapelements.details.PointType;

public class Address extends Point implements MapPoint {
    // decided we needed only street, houseNumber, postalCode, city
    // houseNumber from .osm file can have a side id after the number e.x. "18A"
    public static String REGEX = "^(?<house>[0-9A-Z]*)?$";
    String street;
    String houseNumber;
    // chose to make postcode string because we don't need to manipulate it at any
    // point, and we thereby
    // don't need to parse it from where it is read from the file
    String postCode;
    String city;
    PointType type = PointType.ADDRESS;
    boolean isFavourite = false;

    public Address(String street, String houseNumber, String postCode, String city, float x, float y) {
        super(x, y);
        this.street = street.intern();
        this.houseNumber = houseNumber.intern();
        if (postCode != null) {
            this.postCode = postCode.intern();
        }
        if (city != null) {
            this.city = city.intern();
        }
    }

    public void draw(GraphicsContext gc, float zoom) {
        gc.setFont(Font.font("Helvetica Neue", zoom * 10));
        gc.fillText(houseNumber, x, y);
    }

    @Override
    public String getName() {
        return street + " " + houseNumber + " " + postCode + " " + city;
    }

    @Override
    public PointType getType() {
        return type;
    }

    @Override
    public void setType(PointType newType) {
        type = newType;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return getName();
    }
}
