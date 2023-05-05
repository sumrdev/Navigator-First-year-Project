package marp.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import marp.mapelements.Address;

public class SimpleTrie implements Serializable{
    public TrieNode root;
    public TrieNode currentNode;

    public SimpleTrie() {
        root = new TrieNode();
    }

    public void moveThroughTree(String searchInput) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        for (int i = 0; i < searchInput.length(); i++) {
            char currentChar = searchInput.charAt(i);
            currentNode = currentNode.getNode(currentChar);
        }
    }

    // address is made lowercase to make searching easier for the user, ie.
    // writing "Svanevej" is read the same as "svanevej"
    public void insert(Address address) {
        currentNode = root;
        String addressString = address.getStreet() + " " + address.getPostCode() + " " + address.getCity();
        String lowercaseAddressString = addressString.toLowerCase();

        for (int i = 0; i < lowercaseAddressString.length(); i++) {
            char currentChar = lowercaseAddressString.charAt(i);
            if (!currentNode.containsKey(currentChar)) {
                currentNode.setNode(currentChar, new TrieNode());
            }
            currentNode = currentNode.getNode(currentChar);
        }
        currentNode.setIsEndTrue();
        currentNode.setEndAddress(addressString);
        currentNode.addHouseNumber(address.getHouseNumber(), address);
    }

    // exception handling?
    public ArrayList<String> getAddressSuggestions(String searchInput, int suggestionAmount) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        ArrayList<String> suggestionList = new ArrayList<>();
        if (containsSearch(searchInput)) {
            moveThroughTree(searchInput);
            suggestionFinder(suggestionList, currentNode, suggestionAmount);
            return suggestionList;
        }
        return suggestionList;
    }

    // recursive method for use in getAddressSuggestions()
    // rækkefølge? hvad hvis searchinput er en ende/addresse
    public void suggestionFinder(ArrayList<String> suggestionList, TrieNode currentNode, int suggestionAmount) {
        if (currentNode.getIsEnd()) {
            suggestionList.add(currentNode.getEndAddress());
        }
        if (currentNode.branches.isEmpty() || suggestionList.size() >= suggestionAmount) {
            return;
        }
        for (TrieNode branch : currentNode.branches.values()) {
            suggestionFinder(suggestionList, branch, suggestionAmount);
        }

    }

    public boolean containsSearch(String searchInput) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        for (int i = 0; i < searchInput.length(); i++) {
            char currentChar = searchInput.charAt(i);
            if (currentNode.containsKey(currentChar)) {
                currentNode = currentNode.getNode(currentChar);
            } else {
                currentNode = root;
                return false;
            }
        }
        currentNode = root;
        return true;
    }

    public boolean fullContainsSearch(String searchInput) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        for (int i = 0; i < searchInput.length(); i++) {
            char currentChar = searchInput.charAt(i);
            if (currentNode.containsKey(currentChar)) {
                currentNode = currentNode.getNode(currentChar);
            } else {
                currentNode = root;
                return false;
            }
        }
        if (currentNode.getIsEnd()) {
            currentNode = root;
            return true;
        } else {
            currentNode = root;
            return false;
        }
    }

    public String getFullAddress(String searchInput) {
        currentNode = root;
        moveThroughTree(searchInput);
        return currentNode.getEndAddress();
    }

    public ArrayList<String> getHouseNumberSuggestions(String searchInput, int suggestionAmount) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        ArrayList<String> suggestionList = new ArrayList<>();
        moveThroughTree(searchInput);

        ArrayList<String> nodeNumbers = currentNode.getHouseNumbers();
        for (int j = 0; j < suggestionAmount && j < nodeNumbers.size(); j++) {
            suggestionList.add(nodeNumbers.get(j));
        }
        return suggestionList;
    }
    
    public Address getAddressObject(String searchInput, String house){
        currentNode = root;
        moveThroughTree(searchInput);
        return currentNode.getAddressObject(house);
    }
}
