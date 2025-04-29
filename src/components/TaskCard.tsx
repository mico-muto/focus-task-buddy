
import { Task, Priority } from "@/types/models";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { CheckCircle2, Circle } from "lucide-react";
import { Button } from "@/components/ui/button";

interface TaskCardProps {
  task: Task;
  onComplete?: (taskNo: number) => void;
  onDelete?: (taskNo: number) => void;
}

const TaskCard: React.FC<TaskCardProps> = ({ task, onComplete, onDelete }) => {
  const getPriorityColor = (priority: Priority) => {
    switch (priority) {
      case Priority.HIGH:
        return "text-red-600";
      case Priority.MEDIUM:
        return "text-amber-500";
      case Priority.LOW:
        return "text-green-600";
      default:
        return "text-gray-600";
    }
  };
  
  const formatDate = (date: any) => {
    return `${date.month}/${date.day}/${date.year} ${date.timeStr}`;
  };
  
  return (
    <Card className="mb-4">
      <CardHeader className="pb-2">
        <div className="flex justify-between items-center">
          <div className="flex items-center gap-2">
            {task.completed ? (
              <CheckCircle2 className="text-green-600 h-5 w-5" />
            ) : (
              <Circle 
                className="text-gray-400 h-5 w-5 cursor-pointer hover:text-gray-600" 
                onClick={() => onComplete && onComplete(task.taskNo)}
              />
            )}
            <CardTitle className={task.completed ? "line-through text-gray-500" : ""}>
              {task.taskName}
            </CardTitle>
          </div>
          <span className={getPriorityColor(task.taskPriority)}>
            {task.taskPriority}
          </span>
        </div>
      </CardHeader>
      <CardContent>
        <p className="text-gray-700 mb-2">{task.taskDetails}</p>
        <div className="flex justify-between items-center text-sm text-gray-500">
          <span>Due: {formatDate(task.dueDate)}</span>
          <div>
            <span className="mr-4">Status: {task.taskStatus}</span>
            {onDelete && (
              <Button 
                variant="ghost" 
                className="text-red-500 hover:text-red-700 p-0 h-auto" 
                onClick={() => onDelete(task.taskNo)}
              >
                Delete
              </Button>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

export default TaskCard;
