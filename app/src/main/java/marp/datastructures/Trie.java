package marp.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import marp.mapelements.Address;

public class Trie implements Serializable{
    private TrieNode root;
    private TrieNode currentNode;
    /**
 * Constructor for Trie
 */
    public Trie() {
        root = new TrieNode();
    }
 /**
 * Inserts an Address object into the Trie, creating a path by following the branches of the Trie as far as possible, 
 * then creating new TrieNodes as necessary. Finally, the Address object, and the String form of the address, is stored at the end of the branch, and the final TrieNode is 
 * marked as an end node
 *  @param address the Address object to be inserted
 */
    public void insert(Address address) {
    // address is made lowercase to make searching easier for the user, ie.
    // writing "Svanevej" is read the same as "svanevej"
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
        currentNode.setIsEnd(true);
        currentNode.setEndAddress(addressString);
        currentNode.addHouseNumber(address.getHouseNumber(), address);
    }
/**
 * @param searchInput the String used to navigate through the Trie
 * @param suggestionAmount the maximum amount of housenumber String suggestions needed, ie. the maximum size of the list returned
 * @return A list of possible String housenumbers for the given input
 */
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
/**
 *  @param searchInput the String used to navigate through the Trie
 * @param suggestionAmount the maximum amount of address String suggestions needed, ie. the maximum size of the list returned
 * @return a list of possible String addresses for the given input
 */
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
    private void suggestionFinder(ArrayList<String> suggestionList, TrieNode currentNode, int suggestionAmount) {
        if (currentNode.getIsEnd()) {
            suggestionList.add(currentNode.getEndAddress());
        }
        if (currentNode.getBranches().isEmpty() || suggestionList.size() >= suggestionAmount) {
            return;
        }
        for (TrieNode branch : currentNode.getBranches().values()) {
            suggestionFinder(suggestionList, branch, suggestionAmount);
        }
    }

    private boolean containsSearch(String searchInput) {
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
/**
 * @param searchInput the String address used to navigate through the Trie,
 * @return a boolean value, true if the Trie contains the search input and the input is a full address (
 * without a housenumber), false otherwise
 */
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
/**
 * Method for getting a String address with proper capitalisation
 * @param searchInput the String address used to navigate through the Trie
 * @return a String address as it should be displayed to a user
 */
    public String getFullAddress(String searchInput) {
        currentNode = root;
        moveThroughTree(searchInput);
        return currentNode.getEndAddress();
    }
/**
 * @param searchInput the String address used to navigate through the Trie
 * @param house the String housenumber for the 
 * @return an Address object
 */
    public Address getAddressObject(String searchInput, String house){
        currentNode = root;
        moveThroughTree(searchInput);
        return currentNode.getAddressObject(house);
    }

    
    private void moveThroughTree(String searchInput) {
        currentNode = root;
        searchInput = searchInput.toLowerCase();
        for (int i = 0; i < searchInput.length(); i++) {
            char currentChar = searchInput.charAt(i);
            currentNode = currentNode.getNode(currentChar);
        }
    }
}
