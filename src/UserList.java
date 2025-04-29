
import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> users;
    
    public UserList() {
        users = new ArrayList<>();
    }
    
    public UserList(List<User> users) {
        this.users = new ArrayList<>(users);
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public void removeUser(User user) {
        users.remove(user);
    }
    
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
    
    public User getUserById(int userId) {
        return users.stream()
                   .filter(u -> u.getUserId() == userId)
                   .findFirst()
                   .orElse(null);
    }
    
    public User getUserByUsername(String username) {
        return users.stream()
                   .filter(u -> u.getUsername().equals(username))
                   .findFirst()
                   .orElse(null);
    }
    
    public int size() {
        return users.size();
    }
}
