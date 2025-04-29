
import { User, Task, Priority, DateTime } from "@/types/models";

// This service simulates a database and provides methods to interact with it
class DataService {
  private users: User[] = [];
  private tasks: Task[] = [];
  private currentUserId: number | null = null;
  
  constructor() {
    this.loadFromLocalStorage();
    
    // Add demo data if empty
    if (this.users.length === 0) {
      this.addDemoData();
    }
  }
  
  private addDemoData() {
    // Create admin user
    const adminUser: User = {
      userId: 1,
      username: "admin",
      password: "admin123",
      firstName: "Admin",
      middleName: "",
      lastName: "User",
      email: "admin@example.com",
      age: 30,
      gender: "Other",
      profilePic: "",
      birthMonth: 1,
      birthDay: 1,
      birthYear: 1990,
      isAdmin: true
    };
    
    // Create regular user
    const regularUser: User = {
      userId: 2,
      username: "user",
      password: "user123",
      firstName: "John",
      middleName: "",
      lastName: "Doe",
      email: "john@example.com",
      age: 25,
      gender: "Male",
      profilePic: "",
      birthMonth: 5,
      birthDay: 15,
      birthYear: 1998,
      isAdmin: false
    };
    
    this.users.push(adminUser, regularUser);
    
    // Create some sample tasks
    const demoDate: DateTime = {
      month: 4,
      day: 30,
      year: 2025,
      hour: 12,
      minute: 0,
      timeStr: "12:00 PM"
    };
    
    const tasks: Task[] = [
      {
        taskNo: 1,
        taskName: "Complete project proposal",
        taskDetails: "Finish writing the project proposal document with budget estimates",
        dueDate: demoDate,
        taskPriority: Priority.HIGH,
        taskStatus: "In Progress",
        userId: 2,
      },
      {
        taskNo: 2,
        taskName: "Schedule team meeting",
        taskDetails: "Schedule a meeting to discuss project timeline and assignments",
        dueDate: demoDate,
        taskPriority: Priority.MEDIUM,
        taskStatus: "Pending",
        userId: 2,
      },
      {
        taskNo: 3,
        taskName: "Review code changes",
        taskDetails: "Go through the new code changes and provide feedback",
        dueDate: demoDate,
        taskPriority: Priority.LOW,
        taskStatus: "Completed",
        userId: 2,
        completed: true
      }
    ];
    
    this.tasks.push(...tasks);
    this.saveToLocalStorage();
  }
  
  private loadFromLocalStorage() {
    try {
      const usersString = localStorage.getItem('users');
      const tasksString = localStorage.getItem('tasks');
      const currentUserIdString = localStorage.getItem('currentUserId');
      
      if (usersString) this.users = JSON.parse(usersString);
      if (tasksString) this.tasks = JSON.parse(tasksString);
      if (currentUserIdString) this.currentUserId = JSON.parse(currentUserIdString);
    } catch (error) {
      console.error("Error loading data from localStorage", error);
    }
  }
  
  private saveToLocalStorage() {
    try {
      localStorage.setItem('users', JSON.stringify(this.users));
      localStorage.setItem('tasks', JSON.stringify(this.tasks));
      if (this.currentUserId !== null) {
        localStorage.setItem('currentUserId', JSON.stringify(this.currentUserId));
      } else {
        localStorage.removeItem('currentUserId');
      }
    } catch (error) {
      console.error("Error saving data to localStorage", error);
    }
  }
  
  // User Methods
  public login(username: string, password: string): User | null {
    const user = this.users.find(u => u.username === username && u.password === password);
    if (user) {
      this.currentUserId = user.userId;
      this.saveToLocalStorage();
      return user;
    }
    return null;
  }
  
  public logout(): void {
    this.currentUserId = null;
    this.saveToLocalStorage();
  }
  
  public getCurrentUser(): User | null {
    if (this.currentUserId === null) return null;
    return this.users.find(u => u.userId === this.currentUserId) || null;
  }
  
  public registerUser(user: Omit<User, 'userId'>): User {
    const newUserId = Math.max(0, ...this.users.map(u => u.userId)) + 1;
    const newUser = { ...user, userId: newUserId, isAdmin: false };
    this.users.push(newUser);
    this.saveToLocalStorage();
    return newUser;
  }
  
  public getAllUsers(): User[] {
    return this.users;
  }
  
  public getUserById(userId: number): User | null {
    return this.users.find(u => u.userId === userId) || null;
  }
  
  public deleteUser(userId: number): boolean {
    const initialLength = this.users.length;
    this.users = this.users.filter(u => u.userId !== userId);
    
    // Also delete associated tasks
    this.tasks = this.tasks.filter(t => t.userId !== userId);
    
    this.saveToLocalStorage();
    return this.users.length < initialLength;
  }
  
  // Task Methods
  public createTask(task: Omit<Task, 'taskNo'>): Task {
    const newTaskNo = Math.max(0, ...this.tasks.map(t => t.taskNo)) + 1;
    const newTask = { ...task, taskNo: newTaskNo };
    this.tasks.push(newTask);
    this.saveToLocalStorage();
    return newTask;
  }
  
  public getAllTasks(): Task[] {
    return this.tasks;
  }
  
  public getTasksByUserId(userId: number): Task[] {
    return this.tasks.filter(t => t.userId === userId);
  }
  
  public getTaskById(taskNo: number): Task | null {
    return this.tasks.find(t => t.taskNo === taskNo) || null;
  }
  
  public updateTask(taskNo: number, updates: Partial<Task>): Task | null {
    const taskIndex = this.tasks.findIndex(t => t.taskNo === taskNo);
    if (taskIndex === -1) return null;
    
    this.tasks[taskIndex] = { ...this.tasks[taskIndex], ...updates };
    this.saveToLocalStorage();
    return this.tasks[taskIndex];
  }
  
  public deleteTask(taskNo: number): boolean {
    const initialLength = this.tasks.length;
    this.tasks = this.tasks.filter(t => t.taskNo !== taskNo);
    this.saveToLocalStorage();
    return this.tasks.length < initialLength;
  }
  
  public markTaskAsCompleted(taskNo: number): Task | null {
    return this.updateTask(taskNo, { completed: true, taskStatus: 'Completed' });
  }
  
  // Admin Methods
  public isCurrentUserAdmin(): boolean {
    const currentUser = this.getCurrentUser();
    return currentUser?.isAdmin === true;
  }
}

// Create a singleton instance
const dataService = new DataService();

export default dataService;
