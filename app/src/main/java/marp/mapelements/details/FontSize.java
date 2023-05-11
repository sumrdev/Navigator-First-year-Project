package marp.mapelements.details;

public enum FontSize {
    SMALL(10),
    QUITE_SMALL(10),
    MEDIUM(12),
    MEDIUM_LARGE(15),
    LARGE(20),
    QUITE_LARGE(25),
    UNDEFINED(0);
    int size;
    FontSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }
}
