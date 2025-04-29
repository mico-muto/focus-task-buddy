
public class Admin extends User {
    private boolean isAdmin;
    
    public Admin(String username, String password, String firstName, String middleName,
                String lastName, String email, int age, String gender, String profilePic,
                int birthMonth, int birthDay, int birthYear, int userId) {
        super(username, password, firstName, middleName, lastName, email, age, gender, 
              profilePic, birthMonth, birthDay, birthYear, userId);
        this.isAdmin = true;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
}
