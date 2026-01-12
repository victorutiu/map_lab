package gui;

public class SymbolTableRow {
    private final String variable;
    private final String value;

    public SymbolTableRow(String variable, String value) {
        this.variable = variable;
        this.value = value;
    }

    public String getVariable() {
        return variable;
    }

    public String getValue() {
        return value;
    }
}
