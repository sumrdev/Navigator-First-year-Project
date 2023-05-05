package marp.datastructures;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import marp.mapelements.Address;



public class TrieNode implements Serializable {
    public boolean isEnd;
    public HashMap<Character, TrieNode> branches;
    public HashMap<String, Address> houseNumberToAddress = new HashMap<>();
    public String endAddress;

    public TrieNode(){
        branches = new HashMap<>();
    }

    public void setIsEndTrue(){
        isEnd = true;
    }

    public boolean getIsEnd(){
        return isEnd;
    }

    public TrieNode getNode(char character){
        return branches.get(character);
    }

    public void setNode(char character, TrieNode node){
        branches.put(character, node);
    }

    public boolean containsKey(char character){
        if (branches.containsKey(character)){
            return true;
        }
        else {
            return false;
        }
    }

    public void addHouseNumber(String houseNumber, Address address){
        houseNumberToAddress.put(houseNumber, address);
    }

    public ArrayList<String> getHouseNumbers(){
        ArrayList<String> houseNumberList = new ArrayList<>(houseNumberToAddress.keySet());
        return houseNumberList;
    }

    public void setEndAddress(String address) {
        this.endAddress = address;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public Address getAddressObject(String house){
        return houseNumberToAddress.get(house);
    }
}
