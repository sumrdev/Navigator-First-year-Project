package marp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import marp.datastructures.Trie;
import marp.mapelements.Address;
import marp.model.Model;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class TrieTest {
    Trie trie;
    @BeforeEach
    public void setUpTrie(){
        trie = new Trie();
    }
    @Test
    public void simpleInsertTest(){
        trie.insert(new Address("Street", "1", "9999", "City", 1, 1));
        Assertions.assertEquals(true, trie.containsSearch("Street 9999 City"));
        Assertions.assertEquals(true, trie.containsSearch("Street 9999 Cit"));
        Assertions.assertEquals(true, trie.containsSearch("Street 9999"));
        Assertions.assertEquals(true, trie.containsSearch(""));


        Assertions.assertEquals(false, trie.containsSearch("Street 9999 Citys"));
        Assertions.assertEquals(false, trie.containsSearch("Street 1999 City"));
        Assertions.assertEquals(false, trie.containsSearch("Street 999 Citys"));
        Assertions.assertEquals(false, trie.containsSearch("treet 9999 City"));
    }
    @Test
    public void simpleContainsTest(){
        trie.insert(new Address("Street", "1", "9999", "City", 1, 1));
        String concatString = "";
        for (String character : ("Street 9999 City").split("")){
            concatString = concatString + character;
            Assertions.assertEquals(true, trie.containsSearch(concatString));
        }
    }
    @Test
    public void simpleStringRecognizationTest(){
        trie.insert(new Address("Street", "1", "9999", "City", 1, 1));
        Assertions.assertEquals(true, trie.containsSearch("street 9999 city"));
        Assertions.assertEquals(true, trie.containsSearch("S t r e e t 9 9 9 9 C i t y"));
        Assertions.assertEquals(true, trie.containsSearch("STREET 9999 CITY"));
        Assertions.assertEquals(true, trie.containsSearch("Street9999City"));
        Assertions.assertEquals(true, trie.containsSearch(" Street 9999 City"));
        Assertions.assertEquals(true, trie.containsSearch("Street         9999 City"));
        Assertions.assertEquals(true, trie.containsSearch("Street9999   City"));
        Assertions.assertEquals(true, trie.containsSearch("street9999city"));
        Assertions.assertEquals(true, trie.containsSearch("street 9999 c"));
        Assertions.assertEquals(true, trie.containsSearch("street 9999"));
        Assertions.assertEquals(true, trie.containsSearch("street9999"));
        Assertions.assertEquals(true, trie.containsSearch("street9999         "));
    }
    @Test
    public void complexInsertTest(){
        trie.insert(new Address("'¨´æøå.,-<>!#¤%&/()@£=][{}1234567890", "1", "9999", "City", 1, 1));
        Assertions.assertEquals(true, trie.containsSearch("'¨´æøå.,-<>!#¤%&/()@£=][{}1234567890 9999 City"));
    }
    @Test
    public void complexStringRecognizationTest(){
        trie.insert(new Address("'¨´æøå.,-<>!#¤%&/()@£=][{}1234567890", "1", "9999", "City", 1, 1));
        Assertions.assertEquals(true, trie.containsSearch("'¨´æøå.,-<>!#¤%&/()@£=][{}1234567890 9999 city"));
        Assertions.assertEquals(true, trie.containsSearch("' ¨ ´ æ ø å . , - < > ! # ¤ % & / ( ) @ £ = ] [ { } 1 2 3 4 5 6 7 8 9 0 9 9 9 9  C i t y"));
    }
    @Test
    public void fullContainsSearchTest(){
        trie.insert(new Address("Street", "1", "9999", "FirstCity", 1, 1));
        trie.insert(new Address("Street", "1", "9999", "SecondCity", 1, 1));
        Assertions.assertEquals(true, trie.fullContainsSearch("Street 9999 FirstCity"));
        Assertions.assertEquals(true, trie.fullContainsSearch("Street 9999 SecondCity"));
        Assertions.assertEquals(true, trie.fullContainsSearch("street9999firstcity"));
        Assertions.assertEquals(true, trie.fullContainsSearch("street9999secondcity"));

        Assertions.assertEquals(false, trie.fullContainsSearch("Street 9999 FirstCityWow"));
        Assertions.assertEquals(false, trie.fullContainsSearch("Street 9999 FirstCi"));
        Assertions.assertEquals(false, trie.fullContainsSearch("Street 999 FirstCity"));
        Assertions.assertEquals(false, trie.fullContainsSearch("Street 9999"));
        Assertions.assertEquals(false, trie.fullContainsSearch(""));


        trie.insert(new Address("Street", "1", "9999", "City", 1, 1));
        trie.insert(new Address("Street", "1", "9999", "CityContinued", 1, 1));
        Assertions.assertEquals(true, trie.fullContainsSearch("Street 9999 City"));
        Assertions.assertEquals(true, trie.fullContainsSearch("Street 9999 CityContinued"));

        Assertions.assertEquals(false, trie.fullContainsSearch("Street 9999 CityCon"));
    }
    @Test
    public void getFullAddressTest(){
    trie.insert(new Address("Street", "1", "9999", "FirstCity", 1, 1));
    trie.insert(new Address("Street", "1", "9999", "SecondCity", 1, 1));
    Assertions.assertEquals("Street 9999 FirstCity", trie.getFullAddress("Street 9999 FirstCity"));
    Assertions.assertEquals("Street 9999 FirstCity", trie.getFullAddress("street  9999firstCity"));
    
    Assertions.assertEquals(null, trie.getFullAddress("Street 9999 First"));
    Assertions.assertEquals(null, trie.getFullAddress("Street 1111 FirstCity"));
    Assertions.assertEquals(null, trie.getFullAddress(""));
    }
    @Test 
    public void getAddressObjectTest(){
        Address FirstAddressAsItShouldBe = new Address("Street", "1", "9999", "City", 1, 2);
        Address SecondAddressAsItShouldBe = new Address("Street", "2", "9999", "City", 1, 2);

        Address gottenAddress = trie.getAddressObject("Street 9999 City", "1");
        Assertions.assertEquals("Street", gottenAddress.getStreet() );
        Assertions.assertEquals("9999", gottenAddress.getPostCode() );
        Assertions.assertEquals("1", gottenAddress.getHouseNumber() );
        Assertions.assertEquals("City", gottenAddress.getCity() );
        Assertions.assertEquals(1, gottenAddress.getX() );
        Assertions.assertEquals(2, gottenAddress.getY() );

        gottenAddress = trie.getAddressObject("street9999city", "1");
        Assertions.assertEquals("Street", gottenAddress.getStreet() );
        Assertions.assertEquals("9999", gottenAddress.getPostCode() );
        Assertions.assertEquals("1", gottenAddress.getHouseNumber() );
        Assertions.assertEquals("City", gottenAddress.getCity() );
        Assertions.assertEquals(1, gottenAddress.getX() );
        Assertions.assertEquals(2, gottenAddress.getY() );

        Assertions.assertEquals(null, trie.getAddressObject("Street 1111 CityWow", "1"));
        Assertions.assertEquals(null, trie.getAddressObject("Street 1111 City", "1"));
        Assertions.assertEquals(null, trie.getAddressObject("Street 9999 City", "3"));
    }
}
