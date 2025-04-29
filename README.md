
# Focus Task Buddy

A JavaFX-based task management application.

## Project Structure

```
src/
├── Main.java
├── Admin.java
├── RegularUser.java
├── User.java
├── Task.java
├── DateTime.java
├── DataStorage.java
├── PrioritySelection.java
├── DataSet.java
├── controllers/
│   ├── LoginController.java
│   ├── RegisterController.java
│   ├── DashboardController.java
│   ├── ProfileController.java
│   ├── CompletedTasksController.java
│   └── TaskForm.java
├── views/
│   ├── LoginView.fxml
│   ├── RegisterView.fxml
│   ├── DashboardView.fxml
│   ├── ProfileView.fxml
│   ├── CompletedTasksView.fxml
│   └── TaskFormView.fxml
└── styles/
    └── application.css
```

## Running the Application

1. Ensure JavaFX is properly set up in your IDE
2. For Eclipse: 
   - Right-click on the project
   - Run As > Java Application
   - Select Main as the main class

3. VM Arguments for JavaFX (if needed):
   ```
   --module-path /path/to/javafx-sdk/lib --add-modules=javafx.controls,javafx.fxml
   ```

## Default Login Credentials

- Admin:
  - Username: admin
  - Password: admin

- Regular User:
  - Username: user
  - Password: user123
