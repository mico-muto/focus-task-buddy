
public class RegularUser extends User {
    private boolean isAdmin;
    
    public RegularUser(String username, String password, String firstName, String middleName,
                      String lastName, String email, int age, String gender, String profilePic,
                      int birthMonth, int birthDay, int birthYear, int userId) {
        super(username, password, firstName, middleName, lastName, email, age, gender, 
              profilePic, birthMonth, birthDay, birthYear, userId);
        this.isAdmin = false;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
}
