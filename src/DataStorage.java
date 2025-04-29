
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataStorage {
    private static DataStorage instance;
    
    private List<User> users;
    private List<Task> tasks;
    private User currentUser;
    
    private DataStorage() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
        currentUser = null;
    }
    
    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }
    
    public void initializeDemoData() {
        // Add demo admin user
        Admin adminUser = new Admin(
            "admin", "admin", "Admin", "", "User",
            "admin@example.com", 30, "Other", "", 1, 1, 1990, 1
        );
        users.add(adminUser);
        
        // Add regular user
        RegularUser regularUser = new RegularUser(
            "user", "user123", "John", "", "Doe",
            "john@example.com", 25, "Male", "", 5, 15, 1998, 2
        );
        users.add(regularUser);
        
        // Add some tasks for the regular user
        DateTime demoDate = new DateTime(4, 30, 2025, 12, 0);
        
        Task task1 = new Task(
            1, "Complete project proposal", 
            "Finish writing the project proposal document with budget estimates",
            demoDate, PrioritySelection.HIGH, "In Progress", 2
        );
        
        Task task2 = new Task(
            2, "Schedule team meeting", 
            "Schedule a meeting to discuss project timeline and assignments",
            demoDate, PrioritySelection.MEDIUM, "Pending", 2
        );
        
        Task task3 = new Task(
            3, "Review code changes", 
            "Go through the new code changes and provide feedback",
            demoDate, PrioritySelection.LOW, "Completed", 2
        );
        task3.setCompleted(true);
        
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
    }
    
    // User methods
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public User registerUser(String username, String password, String firstName, 
                         String middleName, String lastName, String email, 
                         int age, String gender, String profilePic,
                         int birthMonth, int birthDay, int birthYear) {
        // Check if username already exists
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return null;
        }
        
        int userId = getNextUserId();
        RegularUser newUser = new RegularUser(
            username, password, firstName, middleName, lastName,
            email, age, gender, profilePic, birthMonth, birthDay, birthYear, userId
        );
        
        users.add(newUser);
        return newUser;
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    public User getUserById(int userId) {
        return users.stream()
                   .filter(u -> u.getUserId() == userId)
                   .findFirst()
                   .orElse(null);
    }
    
    public boolean deleteUser(int userId) {
        User userToRemove = getUserById(userId);
        if (userToRemove != null) {
            users.remove(userToRemove);
            
            // Also delete associated tasks
            tasks.removeIf(task -> task.getUserId() == userId);
            return true;
        }
        return false;
    }
    
    // Task methods
    public Task createTask(String taskName, String taskDetails, DateTime dueDate,
                       PrioritySelection taskPriority, String taskStatus, int userId) {
        int taskNo = getNextTaskId();
        Task newTask = new Task(taskNo, taskName, taskDetails, dueDate, taskPriority, taskStatus, userId);
        tasks.add(newTask);
        return newTask;
    }
    
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
    
    public List<Task> getTasksByUserId(int userId) {
        return tasks.stream()
                   .filter(t -> t.getUserId() == userId)
                   .collect(Collectors.toList());
    }
    
    public List<Task> getTasksByFilter(int userId, Predicate<Task> filter) {
        return tasks.stream()
                   .filter(t -> t.getUserId() == userId)
                   .filter(filter)
                   .collect(Collectors.toList());
    }
    
    public List<Task> getCompletedTasksByUserId(int userId) {
        return getTasksByFilter(userId, Task::isCompleted);
    }
    
    public List<Task> getPendingTasksByUserId(int userId) {
        return getTasksByFilter(userId, task -> !task.isCompleted());
    }
    
    public Task getTaskById(int taskNo) {
        return tasks.stream()
                  .filter(t -> t.getTaskNo() == taskNo)
                  .findFirst()
                  .orElse(null);
    }
    
    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskNo() == updatedTask.getTaskNo()) {
                tasks.set(i, updatedTask);
                return;
            }
        }
    }
    
    public boolean deleteTask(int taskNo) {
        return tasks.removeIf(task -> task.getTaskNo() == taskNo);
    }
    
    public Task markTaskAsCompleted(int taskNo) {
        Task task = getTaskById(taskNo);
        if (task != null) {
            task.setCompleted(true);
            task.setTaskStatus("Completed");
        }
        return task;
    }
    
    private int getNextUserId() {
        return users.stream()
                  .mapToInt(User::getUserId)
                  .max()
                  .orElse(0) + 1;
    }
    
    public int getNextTaskId() {
        return tasks.stream()
                  .mapToInt(Task::getTaskNo)
                  .max()
                  .orElse(0) + 1;
    }
}
