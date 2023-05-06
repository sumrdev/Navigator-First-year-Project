package marp.mapelements.details;

public enum FontSize {
    SMALL(10),
    QUITE_SMALL(15),
    MEDIUM(20),
    MEDIUM_LARGE(30),
    LARGE(35),
    QUITE_LARGE(40),
    UNDEFINED(0);
    int size;
    FontSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }
}
