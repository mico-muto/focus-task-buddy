
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class ProfileController {
    @FXML private Label fullNameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label ageLabel;
    @FXML private Label genderLabel;
    @FXML private Label birthDateLabel;
    @FXML private Button backButton;
    
    private User currentUser;
    
    public void initUser(User user) {
        this.currentUser = user;
        updateProfileInfo();
    }
    
    private void updateProfileInfo() {
        fullNameLabel.setText(currentUser.getFullName());
        usernameLabel.setText(currentUser.getUsername());
        emailLabel.setText(currentUser.getEmail());
        ageLabel.setText(String.valueOf(currentUser.getAge()));
        genderLabel.setText(currentUser.getGender());
        
        String birthDate = String.format("%d/%d/%d", 
            currentUser.getBirthMonth(),
            currentUser.getBirthDay(),
            currentUser.getBirthYear()
        );
        birthDateLabel.setText(birthDate);
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DashboardView.fxml"));
            Parent dashboardView = loader.load();
            
            DashboardController controller = loader.getController();
            controller.initUser(currentUser);
            
            Scene scene = new Scene(dashboardView);
            Stage stage = (Stage) backButton.getScene().getWindow();
            
            stage.setScene(scene);
            stage.setTitle("Focus Task Buddy - Dashboard");
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error returning to dashboard: " + e.getMessage());
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
