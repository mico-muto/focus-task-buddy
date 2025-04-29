
public class Error {
    private int errorCode;
    private String errorMessage;
    private DateTime timestamp;
    
    public Error(int errorCode, String errorMessage, DateTime timestamp) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }
    
    // Getters and setters
    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public DateTime getTimestamp() { return timestamp; }
    public void setTimestamp(DateTime timestamp) { this.timestamp = timestamp; }
    
    @Override
    public String toString() {
        return "Error " + errorCode + ": " + errorMessage + 
               " (Time: " + timestamp.getFormattedDateTime() + ")";
    }
}
