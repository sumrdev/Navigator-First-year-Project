package marp.view.gui;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import marp.datastructures.Trie;
import marp.mapelements.Address;
import marp.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchBar extends TextField {
    public int suggestionAmount;
    public int unchangingAmount;
    public ContextMenu popup;
    public ArrayList<CustomMenuItem> popupList = new ArrayList<>();
    public boolean isAMapLoaded;
    public Model model;

    String REGEX = "^(?<street>[0-9]?[\\p{L} .']+([0-9]+[ .][\\p{L} .']+)?) +((?<house>[0-9]+[\\p{L} ]?)[,]? +)?((?<floor>([0-9]+|(kl)|(st))).? +(?<side>((tv)|(th)|(mf))),? +)?(?<postcode>[0-9]{4}) *( +(?<city>[\\p{L} ]+))?$";
    Pattern PATTERN = Pattern.compile(REGEX);
    Matcher matcher;

    public SearchBar(Model model, int suggestionAmount) {
        super();
        this.suggestionAmount = suggestionAmount;
        this.unchangingAmount = suggestionAmount;
        this.model = model;
        popup = new ContextMenu();

        setListener();

        getStylesheets().addAll("CSS/darkmodesheet.css", "CSS/stylesheet.css");
        getStyleClass().add("map-button");


        this.setText("this is to make the suggestions appear on first typing");
        this.setText("");

    }

    private void setListener() {
        textProperty().addListener((obs, oldText, newText) -> {
            suggestionAmount = unchangingAmount;
            if (model.getSuggestionTrie().fullContainsSearch(newText)) {

                houseSuggester(newText);
            } else {
                addressSuggester(newText);
            }
        });
    }

    private void addressSuggester(String newText) {
        popupList.clear();
        for (String address : model.getSuggestionTrie().getAddressSuggestions(newText, suggestionAmount)) {
            createAddressElement(address);
        }
        createAddressExpander(newText);

        //show only if the searchbar exists in a scene
        if (this.getScene() != null) {
            popup.show(this, Side.BOTTOM, 0, 0);
        }
        popup.getItems().clear();
        popup.getItems().addAll(popupList);
    }

    private void houseSuggester(String newText) {
        popupList.clear();
        for (String houseNumber : model.getSuggestionTrie().getHouseNumberSuggestions(newText, suggestionAmount)) {
            createHouseElement(newText, houseNumber);
        }
        createHouseExpander(newText);

        popup.show(this, Side.BOTTOM, 0, 0);
        popup.getItems().clear();
        popup.getItems().addAll(popupList);

    }

    private void createAddressElement(String popupText) {
        matcher = PATTERN.matcher(model.getSuggestionTrie().getFullAddress(popupText));
            // åbenbart nødvendigt at lave matcher.matches() også selvom det ikke bruges?
            matcher.matches();
        String text = matcher.group("street") +"  "+ matcher.group("postcode") + " " + matcher.group("city");
        Label suggestion = new Label(popupText);
        CustomMenuItem popupItem = new CustomMenuItem(suggestion, false);
        popupList.add(popupItem);
        popupItem.setOnAction(e -> {
            setText(text);
            int length = matcher.group("street").length() + 1;
            this.positionCaret(length);
            
        });
    }

    private void createHouseElement(String popupText, String houseNumber) {
        matcher = PATTERN.matcher(model.getSuggestionTrie().getFullAddress(popupText));
        // åbenbart nødvendigt at lave matcher.matches() også selvom det ikke bruges?
        matcher.matches();
        String textWithNumber = matcher.group("street") +" "+ houseNumber +" "+ matcher.group("postcode") + " " + matcher.group("city");
        Label suggestion = new Label(textWithNumber);
        CustomMenuItem popupItem = new CustomMenuItem(suggestion, true);
        popupList.add(popupItem);
        popupItem.setOnAction(e -> {
            setText(textWithNumber);
            int length = this.getText().length();
            this.positionCaret(length);
        });
    }

    private void createAddressExpander(String newText) {
        Label suggestion = new Label("More suggestions...");
        CustomMenuItem popupItem = new CustomMenuItem(suggestion, false);
        popupList.add(popupItem);
        popupItem.setOnAction(e -> {
            suggestionAmount = suggestionAmount + unchangingAmount;
            addressSuggester(newText);
        });
    }

    private void createHouseExpander(String newText) {
        Label suggestion = new Label("More suggestions...");
        CustomMenuItem popupItem = new CustomMenuItem(suggestion, false);
        popupList.add(popupItem);
        popupItem.setOnAction(e -> {
            suggestionAmount = suggestionAmount + unchangingAmount;
            houseSuggester(newText);
        });
    }

    public Address getAddress(){
        Matcher matcher = PATTERN.matcher(getText());

        if (matcher.matches() && matcher.group("house") != null && model.getSuggestionTrie().fullContainsSearch(matcher.group("street") + " " + matcher.group("postcode") + " "
                + matcher.group("city"))){
            return model.getSuggestionTrie().getAddressObject(matcher.group("street") + " " + matcher.group("postcode") + " "
                    + matcher.group("city"), matcher.group("house"));
        }
        else {
            return null;
        }
    }
}
