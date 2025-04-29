
import { useState, useEffect } from "react";
import AppLayout from "@/components/AppLayout";
import TaskCard from "@/components/TaskCard";
import { Task } from "@/types/models";
import { useAuth } from "@/contexts/AuthContext";
import dataService from "@/services/dataService";
import { useToast } from "@/components/ui/use-toast";

const CompletedTasksPage = () => {
  const { user } = useAuth();
  const { toast } = useToast();
  const [tasks, setTasks] = useState<Task[]>([]);
  
  useEffect(() => {
    if (user) {
      loadTasks();
    }
  }, [user]);
  
  const loadTasks = () => {
    if (user) {
      const userTasks = dataService.getTasksByUserId(user.userId).filter(task => task.completed);
      setTasks(userTasks);
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
  
  return (
    <AppLayout title="Completed">
      <div className="mb-6 flex justify-between items-center">
        <h2 className="text-xl">Completed Tasks</h2>
      </div>
      
      <div>
        {tasks.length === 0 ? (
          <p className="text-gray-500">No completed tasks found.</p>
        ) : (
          tasks.map((task) => (
            <TaskCard 
              key={task.taskNo} 
              task={task} 
              onDelete={handleDeleteTask}
            />
          ))
        )}
      </div>
    </AppLayout>
  );
};

export default CompletedTasksPage;
