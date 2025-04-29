
import java.util.List;

public class DataSet {
    private List<User> users;
    private List<Task> tasks;
    
    public DataSet(List<User> users, List<Task> tasks) {
        this.users = users;
        this.tasks = tasks;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
}
