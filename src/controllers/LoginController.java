
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both username and password.");
            return;
        }
        
        User user = DataStorage.getInstance().login(username, password);
        
        if (user != null) {
            // Successfully logged in
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DashboardView.fxml"));
                Parent dashboardView = loader.load();
                
                DashboardController controller = loader.getController();
                controller.initUser(user);
                
                Scene scene = new Scene(dashboardView);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                
                stage.setScene(scene);
                stage.setTitle("Focus Task Buddy - Dashboard");
                stage.show();
                
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error loading dashboard: " + e.getMessage());
            }
        } else {
            showAlert("Invalid username or password.");
        }
    }
    
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RegisterView.fxml"));
            Parent registerView = loader.load();
            
            Scene scene = new Scene(registerView);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            
            stage.setScene(scene);
            stage.setTitle("Focus Task Buddy - Register");
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading register form: " + e.getMessage());
        }
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
