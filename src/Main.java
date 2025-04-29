
package ToDo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Get the correct URL for the resource
        URL loginViewUrl = getClass().getResource("/ToDo/views/LoginView.fxml");
        if (loginViewUrl == null) {
            System.err.println("Error: Could not find LoginView.fxml");
            System.err.println("Working directory: " + System.getProperty("user.dir"));
            throw new RuntimeException("LoginView.fxml not found");
        }
        
        // Load the login screen
        Parent root = FXMLLoader.load(loginViewUrl);
        primaryStage.setTitle("Focus Task Buddy");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        // Initialize the data storage with demo data
        ToDo.DataStorage.getInstance().initializeDemoData();
        
        // Add admin user explicitly for testing
        ToDo.DataStorage.getInstance().addAdminUser("admin", "admin");
        
        launch(args);
    }
}
