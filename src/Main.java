
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login screen first
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));
        primaryStage.setTitle("Focus Task Buddy");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        // Initialize the data storage with demo data
        DataStorage.getInstance().initializeDemoData();
        launch(args);
    }
}
