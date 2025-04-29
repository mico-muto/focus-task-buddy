
// Models based on UML diagram

export enum Priority {
  HIGH = "HIGH",
  MEDIUM = "MEDIUM",
  LOW = "LOW"
}

export interface DateTime {
  month: number;
  day: number;
  year: number;
  hour: number;
  minute: number;
  timeStr: string;
  
  // Methods would be implemented in class version
  // isValidTime?: () => boolean;
  // getFormattedDateTime?: () => string;
}

export interface Error {
  errorCode: number;
  errorMessage: string;
  timestamp: DateTime;
}

export interface Task {
  taskNo: number;
  taskName: string;
  taskDetails: string;
  dueDate: DateTime;
  taskPriority: Priority;
  taskStatus: string;
  userId: number;
  completed?: boolean;
}

export interface User {
  username: string;
  password: string;
  firstName: string;
  middleName: string;
  lastName: string;
  email: string;
  age: number;
  gender: string;
  profilePic: string;
  birthMonth: number;
  birthDay: number;
  birthYear: number;
  userId: number;
  isAdmin?: boolean;
}

// This would help us simulate the backend functionality
export interface DataStorage {
  users: User[];
  tasks: Task[];
}
