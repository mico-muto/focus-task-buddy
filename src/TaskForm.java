
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class TaskForm {
    @FXML private TextField taskNameField;
    @FXML private TextArea taskDetailsArea;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> hourComboBox;
    @FXML private ComboBox<String> minuteComboBox;
    @FXML private ComboBox<String> amPmComboBox;
    @FXML private ComboBox<PrioritySelection> priorityComboBox;
    @FXML private ComboBox<String> statusComboBox;
    
    private Task existingTask;
    private int userId;
    
    public void initialize() {
        // Initialize time pickers
        hourComboBox.setItems(FXCollections.observableArrayList(
                "12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        
        minuteComboBox.setItems(FXCollections.observableArrayList(
                "00", "15", "30", "45"));
        
        amPmComboBox.setItems(FXCollections.observableArrayList(
                "AM", "PM"));
        
        // Initialize priority selector
        priorityComboBox.setItems(FXCollections.observableArrayList(
                PrioritySelection.HIGH, PrioritySelection.MEDIUM, PrioritySelection.LOW));
        
        // Initialize status selector
        statusComboBox.setItems(FXCollections.observableArrayList(
                "Not Started", "In Progress", "On Hold", "Completed"));
        
        // Default values
        hourComboBox.setValue("12");
        minuteComboBox.setValue("00");
        amPmComboBox.setValue("PM");
        priorityComboBox.setValue(PrioritySelection.MEDIUM);
        statusComboBox.setValue("Not Started");
        dueDatePicker.setValue(LocalDate.now());
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setTask(Task task) {
        this.existingTask = task;
        
        // Fill form with existing task data
        taskNameField.setText(task.getTaskName());
        taskDetailsArea.setText(task.getTaskDetails());
        
        // Set date
        LocalDate dueDate = LocalDate.of(
            task.getDueDate().getYear(), 
            task.getDueDate().getMonth(), 
            task.getDueDate().getDay()
        );
        dueDatePicker.setValue(dueDate);
        
        // Set time
        int hour = task.getDueDate().getHour();
        String amPm = hour < 12 ? "AM" : "PM";
        hour = hour % 12;
        if (hour == 0) hour = 12;
        
        hourComboBox.setValue(String.valueOf(hour));
        minuteComboBox.setValue(String.format("%02d", task.getDueDate().getMinute()));
        amPmComboBox.setValue(amPm);
        
        // Set priority and status
        priorityComboBox.setValue(task.getTaskPriority());
        statusComboBox.setValue(task.getTaskStatus());
    }
    
    @FXML
    private void handleSave() {
        // Validate form
        if (taskNameField.getText().isEmpty()) {
            showAlert("Task name is required");
            return;
        }
        
        // Parse date and time
        LocalDate date = dueDatePicker.getValue();
        int hour = Integer.parseInt(hourComboBox.getValue());
        int minute = Integer.parseInt(minuteComboBox.getValue());
        
        // Adjust hour for PM
        if (amPmComboBox.getValue().equals("PM") && hour != 12) {
            hour += 12;
        }
        // Adjust for 12 AM
        if (amPmComboBox.getValue().equals("AM") && hour == 12) {
            hour = 0;
        }
        
        DateTime dueDate = new DateTime(
            date.getMonthValue(),
            date.getDayOfMonth(),
            date.getYear(),
            hour,
            minute
        );
        
        if (existingTask != null) {
            // Update existing task
            existingTask.setTaskName(taskNameField.getText());
            existingTask.setTaskDetails(taskDetailsArea.getText());
            existingTask.setDueDate(dueDate);
            existingTask.setTaskPriority(priorityComboBox.getValue());
            existingTask.setTaskStatus(statusComboBox.getValue());
            
            DataStorage.getInstance().updateTask(existingTask);
        } else {
            // Create new task
            Task newTask = new Task(
                DataStorage.getInstance().getNextTaskId(),
                taskNameField.getText(),
                taskDetailsArea.getText(),
                dueDate,
                priorityComboBox.getValue(),
                statusComboBox.getValue(),
                userId
            );
            
            DataStorage.getInstance().addTask(newTask);
        }
        
        // Close the form
        closeForm();
    }
    
    @FXML
    private void handleCancel() {
        closeForm();
    }
    
    private void closeForm() {
        Stage stage = (Stage) taskNameField.getScene().getWindow();
        stage.close();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
