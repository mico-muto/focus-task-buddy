
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.time.LocalDate;

public class DataStorage {
    private static DataStorage instance;
    private List<User> users;
    private List<Task> tasks;
    private int nextUserId = 1;
    private int nextTaskId = 1;
    
    private DataStorage() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
    }
    
    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
    
    public void initializeDemoData() {
        // Add a regular user
        registerUser("user", "user123", "John", "", "Doe", "john.doe@example.com", 
                    30, "Male", "", 5, 15, 1993);
                    
        // Add some tasks for the user
        DateTime now = getCurrentDateTime();
        DateTime tomorrow = new DateTime(
            now.getMonth(), now.getDay() + 1, now.getYear(), 
            12, 0
        );
        DateTime nextWeek = new DateTime(
            now.getMonth(), now.getDay() + 7, now.getYear(), 
            15, 30
        );
        
        int userId = 1; // First registered user ID
        addTask(new Task(nextTaskId++, "Complete Project Report", 
                "Finish the analysis section and prepare executive summary", 
                tomorrow, PrioritySelection.HIGH, "Not Started", userId));
        
        addTask(new Task(nextTaskId++, "Weekly Team Meeting", 
                "Discuss project progress and upcoming deadlines", 
                nextWeek, PrioritySelection.MEDIUM, "Not Started", userId));
                
        addTask(new Task(nextTaskId++, "Buy Groceries", 
                "Milk, eggs, bread, and vegetables", 
                tomorrow, PrioritySelection.LOW, "Not Started", userId));
                
        // Add a completed task
        addTask(new Task(nextTaskId++, "Pay Bills", 
                "Electricity and internet bills", 
                now, PrioritySelection.HIGH, "Completed", userId));
    }
    
    public void addAdminUser(String username, String password) {
        Admin admin = new Admin(username, password, "Admin", "", "User", 
                             "admin@example.com", 35, "Male", "", 1, 1, 1990, nextUserId++);
        users.add(admin);
    }
    
    public User registerUser(String username, String password, String firstName, 
                          String middleName, String lastName, String email, 
                          int age, String gender, String profilePic,
                          int birthMonth, int birthDay, int birthYear) {
        // Check if username already exists
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return null; // Username already taken
        }
        
        User newUser = new RegularUser(username, password, firstName, middleName, 
                                   lastName, email, age, gender, profilePic,
                                   birthMonth, birthDay, birthYear, nextUserId++);
        users.add(newUser);
        return newUser;
    }
    
    public User login(String username, String password) {
        return users.stream()
                   .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                   .findFirst()
                   .orElse(null);
    }
    
    public void logout() {
        // Perform any logout operations if needed
        System.out.println("User logged out");
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public void updateTask(Task task) {
        int index = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskNo() == task.getTaskNo()) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            tasks.set(index, task);
        }
    }
    
    public void markTaskAsCompleted(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskNo() == taskId) {
                task.setTaskStatus("Completed");
                break;
            }
        }
    }
    
    public void deleteTask(int taskId) {
        tasks.removeIf(task -> task.getTaskNo() == taskId);
    }
    
    public List<Task> getPendingTasksByUserId(int userId) {
        return tasks.stream()
                  .filter(task -> task.getUserId() == userId && !task.isCompleted())
                  .collect(Collectors.toList());
    }
    
    public List<Task> getTasksByFilter(int userId, Predicate<Task> filter) {
        return tasks.stream()
                  .filter(task -> task.getUserId() == userId && filter.test(task))
                  .collect(Collectors.toList());
    }
    
    public int getNextTaskId() {
        return nextTaskId++;
    }
    
    private DateTime getCurrentDateTime() {
        LocalDate now = LocalDate.now();
        return new DateTime(
            now.getMonthValue(),
            now.getDayOfMonth(),
            now.getYear(),
            java.time.LocalTime.now().getHour(),
            java.time.LocalTime.now().getMinute()
        );
    }
}
