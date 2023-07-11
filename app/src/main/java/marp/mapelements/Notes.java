package marp.mapelements;

import java.util.HashMap;
import java.util.Map;

public class Notes {

    private Map<String, String> notesForPOI;

    public Map<String, String> getHashMapForPOI() {
        return notesForPOI;
    }

    public void NewNote(String address, String note) {
        notesForPOI.put(address, note);
    }

    public String getNoteFromAddress(String address) {
        if (notesForPOI.containsKey(address)) {
            return notesForPOI.get(address);
        } else {
            return null;
        }
    }

    public Notes() {
        notesForPOI = new HashMap<String, String>();
    }
}
