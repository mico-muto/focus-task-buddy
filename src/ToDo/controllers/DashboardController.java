
package ToDo.controllers;

import ToDo.DataStorage;
import ToDo.User;
import ToDo.Task;
import ToDo.PrioritySelection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private ListView<Task> taskListView;
    @FXML private Button addTaskButton;
    @FXML private Button viewCompletedButton;
    @FXML private Button profileButton;
    @FXML private Button logoutButton;
    
    private User currentUser;
    private ObservableList<Task> tasks;
    
    public void initialize() {
        // Configure the list view to display tasks
        taskListView.setCellFactory(param -> new TaskListCell());
        
        // Add context menu to list view items
        ContextMenu contextMenu = new ContextMenu();
        MenuItem completeItem = new MenuItem("Mark as Complete");
        MenuItem editItem = new MenuItem("Edit Task");
        MenuItem deleteItem = new MenuItem("Delete Task");
        
        completeItem.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null && !selectedTask.isCompleted()) {
                DataStorage.getInstance().markTaskAsCompleted(selectedTask.getTaskNo());
                refreshTasks();
            }
        });
        
        editItem.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                openTaskForm(selectedTask);
            }
        });
        
        deleteItem.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                deleteTask(selectedTask);
            }
        });
        
        contextMenu.getItems().addAll(completeItem, editItem, deleteItem);
        taskListView.setContextMenu(contextMenu);
    }
    
    public void initUser(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + user.getFirstName() + "!");
        refreshTasks();
    }
    
    private void refreshTasks() {
        List<Task> userTasks = DataStorage.getInstance().getPendingTasksByUserId(currentUser.getUserId());
        tasks = FXCollections.observableArrayList(userTasks);
        taskListView.setItems(tasks);
    }
    
    @FXML
    private void handleAddTask() {
        openTaskForm(null);
    }
    
    private void openTaskForm(Task task) {
        try {
            URL taskFormUrl = getClass().getResource("/ToDo/views/TaskFormView.fxml");
            if (taskFormUrl == null) {
                showAlert("Error: Could not find TaskFormView.fxml");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(taskFormUrl);
            Parent taskFormView = loader.load();
            
            ToDo.controllers.TaskFormController controller = loader.getController();
            controller.setUserId(currentUser.getUserId());
            
            if (task != null) {
                controller.setTask(task);
            }
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(taskFormView));
            stage.setTitle(task == null ? "Add Task" : "Edit Task");
            
            // Refresh tasks when form is closed
            stage.setOnHidden(e -> refreshTasks());
            
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error opening task form: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleViewCompleted() {
        try {
            URL completedTasksUrl = getClass().getResource("/ToDo/views/CompletedTasksView.fxml");
            if (completedTasksUrl == null) {
                showAlert("Error: Could not find CompletedTasksView.fxml");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(completedTasksUrl);
            Parent completedTasksView = loader.load();
            
            CompletedTasksController controller = loader.getController();
            controller.initUser(currentUser);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(completedTasksView));
            stage.setTitle("Completed Tasks");
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error opening completed tasks: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleProfile() {
        try {
            URL profileUrl = getClass().getResource("/ToDo/views/ProfileView.fxml");
            if (profileUrl == null) {
                showAlert("Error: Could not find ProfileView.fxml");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(profileUrl);
            Parent profileView = loader.load();
            
            ProfileController controller = loader.getController();
            controller.initUser(currentUser);
            
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(profileView));
            stage.setTitle("Focus Task Buddy - Profile");
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading profile: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleLogout() {
        DataStorage.getInstance().logout();
        
        try {
            URL loginUrl = getClass().getResource("/ToDo/views/LoginView.fxml");
            if (loginUrl == null) {
                showAlert("Error: Could not find LoginView.fxml");
                return;
            }
            
            Parent loginView = FXMLLoader.load(loginUrl);
            Scene scene = new Scene(loginView);
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Focus Task Buddy - Login");
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error returning to login: " + e.getMessage());
        }
    }
    
    private void deleteTask(Task task) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Task");
        confirmation.setHeaderText("Delete Task: " + task.getTaskName());
        confirmation.setContentText("Are you sure you want to delete this task?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DataStorage.getInstance().deleteTask(task.getTaskNo());
                refreshTasks();
            }
        });
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Custom cell for displaying tasks in the list view
    private class TaskListCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            
            if (empty || task == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            
            VBox container = new VBox(5);
            Label nameLabel = new Label(task.getTaskName());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            
            Label detailsLabel = new Label(task.getTaskDetails());
            
            Label priorityLabel = new Label("Priority: " + task.getTaskPriority());
            priorityLabel.setStyle(getPriorityStyle(task.getTaskPriority()));
            
            Label dueLabel = new Label("Due: " + task.getDueDate().getFormattedDateTime());
            
            container.getChildren().addAll(nameLabel, detailsLabel, priorityLabel, dueLabel);
            setGraphic(container);
        }
        
        private String getPriorityStyle(PrioritySelection priority) {
            switch (priority) {
                case HIGH:
                    return "-fx-text-fill: #e74c3c;";
                case MEDIUM:
                    return "-fx-text-fill: #f39c12;";
                case LOW:
                    return "-fx-text-fill: #2ecc71;";
                default:
                    return "";
            }
        }
    }
}
