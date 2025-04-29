
import { useState, useEffect } from "react";
import AppLayout from "@/components/AppLayout";
import TaskCard from "@/components/TaskCard";
import TaskForm from "@/components/TaskForm";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";
import { Task } from "@/types/models";
import { useAuth } from "@/contexts/AuthContext";
import dataService from "@/services/dataService";
import { useToast } from "@/components/ui/use-toast";

const DashboardPage = () => {
  const { user } = useAuth();
  const { toast } = useToast();
  const [tasks, setTasks] = useState<Task[]>([]);
  const [showTaskForm, setShowTaskForm] = useState(false);
  
  useEffect(() => {
    if (user) {
      loadTasks();
    }
  }, [user]);
  
  const loadTasks = () => {
    if (user) {
      const userTasks = dataService.getTasksByUserId(user.userId);
      setTasks(userTasks);
    }
  };
  
  const handleCreateTask = (taskData: Omit<Task, "taskNo">) => {
    try {
      dataService.createTask(taskData);
      setShowTaskForm(false);
      loadTasks();
      toast({
        title: "Task created",
        description: "Your task has been created successfully",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Error",
        description: "Failed to create task",
      });
    }
  };
  
  const handleCompleteTask = (taskNo: number) => {
    try {
      dataService.markTaskAsCompleted(taskNo);
      loadTasks();
      toast({
        title: "Task completed",
        description: "Task has been marked as complete",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Error",
        description: "Failed to mark task as complete",
      });
    }
  };
  
  const handleDeleteTask = (taskNo: number) => {
    try {
      dataService.deleteTask(taskNo);
      loadTasks();
      toast({
        title: "Task deleted",
        description: "Task has been deleted successfully",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Error",
        description: "Failed to delete task",
      });
    }
  };
  
  const pendingTasks = tasks.filter(task => !task.completed);
  
  return (
    <AppLayout title="Dashboard">
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-xl">Overview</h2>
        <Button 
          className="bg-app-primary hover:bg-blue-700"
          onClick={() => setShowTaskForm(!showTaskForm)}
        >
          <PlusCircle className="mr-2 h-4 w-4" />
          New Task
        </Button>
      </div>
      
      {showTaskForm && (
        <div className="mb-6">
          <TaskForm 
            onSubmit={handleCreateTask} 
            onCancel={() => setShowTaskForm(false)} 
          />
        </div>
      )}
      
      <div>
        <h3 className="font-medium mb-2">Current Tasks</h3>
        {pendingTasks.length === 0 ? (
          <p className="text-gray-500">No pending tasks. Create a new task to get started!</p>
        ) : (
          pendingTasks.map((task) => (
            <TaskCard 
              key={task.taskNo} 
              task={task} 
              onComplete={handleCompleteTask}
              onDelete={handleDeleteTask}
            />
          ))
        )}
      </div>
    </AppLayout>
  );
};

export default DashboardPage;
