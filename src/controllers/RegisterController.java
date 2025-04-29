
package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private ComboBox<Integer> birthMonthComboBox;
    @FXML private ComboBox<Integer> birthDayComboBox;
    @FXML private ComboBox<Integer> birthYearComboBox;
    @FXML private Button registerButton;
    @FXML private Button cancelButton;
    @FXML private Button loginButton;
    
    public void initialize() {
        // Initialize gender dropdown
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        
        // Initialize birth date dropdowns
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        birthMonthComboBox.setItems(FXCollections.observableArrayList(months));
        
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(i);
        }
        birthDayComboBox.setItems(FXCollections.observableArrayList(days));
        
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 100; i <= currentYear; i++) {
            years.add(i);
        }
        birthYearComboBox.setItems(FXCollections.observableArrayList(years));
    }
    
    @FXML
    private void handleRegister() {
        // Validate form
        if (!validateForm()) {
            return;
        }
        
        // Get form values
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = genderComboBox.getValue();
        int birthMonth = birthMonthComboBox.getValue();
        int birthDay = birthDayComboBox.getValue();
        int birthYear = birthYearComboBox.getValue();
        
        // Register user
        User newUser = DataStorage.getInstance().registerUser(
            username, password, firstName, middleName, lastName,
            email, age, gender, "", birthMonth, birthDay, birthYear
        );
        
        if (newUser != null) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                      "Your account has been created successfully. You can now log in.");
            handleBackToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", 
                      "Username already exists. Please choose another username.");
        }
    }
    
    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();
        
        if (firstNameField.getText().isEmpty()) {
            errors.append("- First name is required\n");
        }
        
        if (lastNameField.getText().isEmpty()) {
            errors.append("- Last name is required\n");
        }
        
        if (usernameField.getText().isEmpty()) {
            errors.append("- Username is required\n");
        } else if (usernameField.getText().length() < 3) {
            errors.append("- Username must be at least 3 characters long\n");
        }
        
        if (emailField.getText().isEmpty()) {
            errors.append("- Email is required\n");
        } else if (!emailField.getText().contains("@")) {
            errors.append("- Email must be valid\n");
        }
        
        if (passwordField.getText().isEmpty()) {
            errors.append("- Password is required\n");
        } else if (passwordField.getText().length() < 6) {
            errors.append("- Password must be at least 6 characters long\n");
        }
        
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errors.append("- Passwords do not match\n");
        }
        
        if (ageField.getText().isEmpty()) {
            errors.append("- Age is required\n");
        } else {
            try {
                int age = Integer.parseInt(ageField.getText());
                if (age < 1) {
                    errors.append("- Age must be positive\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Age must be a number\n");
            }
        }
        
        if (genderComboBox.getValue() == null) {
            errors.append("- Gender is required\n");
        }
        
        if (birthMonthComboBox.getValue() == null) {
            errors.append("- Birth month is required\n");
        }
        
        if (birthDayComboBox.getValue() == null) {
            errors.append("- Birth day is required\n");
        }
        
        if (birthYearComboBox.getValue() == null) {
            errors.append("- Birth year is required\n");
        }
        
        if (errors.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", errors.toString());
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void handleCancel() {
        handleBackToLogin();
    }
    
    @FXML
    private void handleBackToLogin() {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(loginView);
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Focus Task Buddy - Login");
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error returning to login: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
