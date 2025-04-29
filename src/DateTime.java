
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private String timeStr;
    
    public DateTime(int month, int day, int year, int hour, int minute) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        updateTimeStr();
    }
    
    private void updateTimeStr() {
        String amPm = hour < 12 ? "AM" : "PM";
        int displayHour = hour % 12;
        if (displayHour == 0) displayHour = 12;
        
        this.timeStr = String.format("%d:%02d %s", displayHour, minute, amPm);
    }
    
    public boolean isValidTime() {
        // Basic validation
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        if (year < 2000 || year > 2100) return false;  // Adjust range as needed
        if (hour < 0 || hour > 23) return false;
        if (minute < 0 || minute > 59) return false;
        
        return true;
    }
    
    public String getFormattedDateTime() {
        return String.format("%d/%d/%d %s", month, day, year, timeStr);
    }
    
    // Getters and setters
    public int getMonth() { return month; }
    public void setMonth(int month) {
        this.month = month;
        updateTimeStr();
    }
    
    public int getDay() { return day; }
    public void setDay(int day) {
        this.day = day;
        updateTimeStr();
    }
    
    public int getYear() { return year; }
    public void setYear(int year) {
        this.year = year;
        updateTimeStr();
    }
    
    public int getHour() { return hour; }
    public void setHour(int hour) {
        this.hour = hour;
        updateTimeStr();
    }
    
    public int getMinute() { return minute; }
    public void setMinute(int minute) {
        this.minute = minute;
        updateTimeStr();
    }
    
    public String getTimeStr() { return timeStr; }
    
    // Create a DateTime object with current date and time
    public static DateTime now() {
        LocalDateTime now = LocalDateTime.now();
        return new DateTime(
            now.getMonthValue(),
            now.getDayOfMonth(),
            now.getYear(),
            now.getHour(),
            now.getMinute()
        );
    }
}
