
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class CompletedTasksController {
    @FXML private ListView<Task> completedTasksListView;
    
    private User currentUser;
    private ObservableList<Task> completedTasks;
    
    public void initialize() {
        // Configure the list view to display completed tasks
        completedTasksListView.setCellFactory(param -> new CompletedTaskListCell());
        
        // Add context menu for deleting completed tasks
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete Task");
        
        deleteItem.setOnAction(e -> {
            Task selectedTask = completedTasksListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                deleteTask(selectedTask);
            }
        });
        
        contextMenu.getItems().add(deleteItem);
        completedTasksListView.setContextMenu(contextMenu);
    }
    
    public void initUser(User user) {
        this.currentUser = user;
        refreshTasks();
    }
    
    private void refreshTasks() {
        List<Task> userCompletedTasks = DataStorage.getInstance()
                                       .getTasksByFilter(currentUser.getUserId(), Task::isCompleted);
        completedTasks = FXCollections.observableArrayList(userCompletedTasks);
        completedTasksListView.setItems(completedTasks);
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
    
    @FXML
    private void handleClose() {
        Stage stage = (Stage) completedTasksListView.getScene().getWindow();
        stage.close();
    }
    
    // Custom cell for displaying completed tasks
    private class CompletedTaskListCell extends ListCell<Task> {
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
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: gray; -fx-strikethrough: true;");
            
            Label detailsLabel = new Label(task.getTaskDetails());
            detailsLabel.setStyle("-fx-text-fill: gray;");
            
            Label priorityLabel = new Label("Priority: " + task.getTaskPriority());
            priorityLabel.setStyle(getPriorityStyle(task.getTaskPriority()) + "-fx-opacity: 0.7;");
            
            Label completedLabel = new Label("Completed");
            completedLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-style: italic;");
            
            container.getChildren().addAll(nameLabel, detailsLabel, priorityLabel, completedLabel);
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
