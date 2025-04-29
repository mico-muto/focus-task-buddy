
public enum PrioritySelection {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");
    
    private final String displayValue;
    
    PrioritySelection(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
    
    @Override
    public String toString() {
        return displayValue;
    }
}
