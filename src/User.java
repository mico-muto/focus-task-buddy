
public abstract class User {
    protected String username;
    protected String password;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String email;
    protected int age;
    protected String gender;
    protected String profilePic;
    protected int birthMonth;
    protected int birthDay;
    protected int birthYear;
    protected int userId;
    
    public User(String username, String password, String firstName, String middleName,
                String lastName, String email, int age, String gender, String profilePic,
                int birthMonth, int birthDay, int birthYear, int userId) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.profilePic = profilePic;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.userId = userId;
    }
    
    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
    
    public int getBirthMonth() { return birthMonth; }
    public void setBirthMonth(int birthMonth) { this.birthMonth = birthMonth; }
    
    public int getBirthDay() { return birthDay; }
    public void setBirthDay(int birthDay) { this.birthDay = birthDay; }
    
    public int getBirthYear() { return birthYear; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getFullName() {
        return firstName + (middleName.isEmpty() ? " " : " " + middleName + " ") + lastName;
    }
}
