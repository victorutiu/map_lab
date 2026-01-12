package gui;

public class HeapRow {
    private final Integer address;
    private final String value;

    public HeapRow(Integer address, String value) {
        this.address = address;
        this.value = value;
    }

    public Integer getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }
}
