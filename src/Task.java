
public class Task {
    private int taskNo;
    private String taskName;
    private String taskDetails;
    private DateTime dueDate;
    private PrioritySelection taskPriority;
    private String taskStatus;
    private int userId;
    private boolean completed;
    
    public Task(int taskNo, String taskName, String taskDetails, DateTime dueDate,
               PrioritySelection taskPriority, String taskStatus, int userId) {
        this.taskNo = taskNo;
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.userId = userId;
        this.completed = false;
    }
    
    // Getters and setters
    public int getTaskNo() { return taskNo; }
    public void setTaskNo(int taskNo) { this.taskNo = taskNo; }
    
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    
    public String getTaskDetails() { return taskDetails; }
    public void setTaskDetails(String taskDetails) { this.taskDetails = taskDetails; }
    
    public DateTime getDueDate() { return dueDate; }
    public void setDueDate(DateTime dueDate) { this.dueDate = dueDate; }
    
    public PrioritySelection getTaskPriority() { return taskPriority; }
    public void setTaskPriority(PrioritySelection taskPriority) { this.taskPriority = taskPriority; }
    
    public String getTaskStatus() { return taskStatus; }
    public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { 
        this.completed = completed;
        if (completed) {
            this.taskStatus = "Completed";
        }
    }
}
