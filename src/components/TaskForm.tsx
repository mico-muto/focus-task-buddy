
import { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Task, Priority, DateTime } from "@/types/models";
import { useAuth } from "@/contexts/AuthContext";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

interface TaskFormProps {
  onSubmit: (data: Omit<Task, "taskNo">) => void;
  onCancel?: () => void;
}

// Form schema using zod
const formSchema = z.object({
  taskName: z.string().min(1, "Task name is required"),
  taskDetails: z.string().min(1, "Task details are required"),
  dueMonth: z.coerce.number().min(1).max(12),
  dueDay: z.coerce.number().min(1).max(31),
  dueYear: z.coerce.number().min(2025),
  dueHour: z.coerce.number().min(0).max(23),
  dueMinute: z.coerce.number().min(0).max(59),
  taskPriority: z.nativeEnum(Priority),
});

const TaskForm: React.FC<TaskFormProps> = ({ onSubmit, onCancel }) => {
  const { user } = useAuth();
  
  // Set up the form
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      taskName: "",
      taskDetails: "",
      dueMonth: new Date().getMonth() + 1,
      dueDay: new Date().getDate(),
      dueYear: 2025,
      dueHour: 12,
      dueMinute: 0,
      taskPriority: Priority.MEDIUM,
    },
  });
  
  const handleSubmit = (values: z.infer<typeof formSchema>) => {
    if (!user) return;
    
    const dueDate: DateTime = {
      month: values.dueMonth,
      day: values.dueDay,
      year: values.dueYear,
      hour: values.dueHour,
      minute: values.dueMinute,
      timeStr: formatTime(values.dueHour, values.dueMinute),
    };
    
    const task: Omit<Task, "taskNo"> = {
      taskName: values.taskName,
      taskDetails: values.taskDetails,
      dueDate: dueDate,
      taskPriority: values.taskPriority,
      taskStatus: "Pending",
      userId: user.userId,
      completed: false,
    };
    
    onSubmit(task);
  };
  
  const formatTime = (hour: number, minute: number): string => {
    const period = hour >= 12 ? "PM" : "AM";
    const displayHour = hour % 12 === 0 ? 12 : hour % 12;
    const displayMinute = minute.toString().padStart(2, "0");
    return `${displayHour}:${displayMinute} ${period}`;
  };
  
  return (
    <div className="bg-white p-4 rounded-md shadow-sm">
      <h3 className="text-lg font-medium mb-4">Create New Task</h3>
      
      <Form {...form}>
        <form onSubmit={form.handleSubmit(handleSubmit)} className="space-y-4">
          <FormField
            control={form.control}
            name="taskName"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Task Name</FormLabel>
                <FormControl>
                  <Input placeholder="Enter task name" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          
          <FormField
            control={form.control}
            name="taskDetails"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Task Details</FormLabel>
                <FormControl>
                  <Textarea placeholder="Enter task details" {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          
          <div className="grid grid-cols-3 gap-4">
            <FormField
              control={form.control}
              name="dueMonth"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Month</FormLabel>
                  <FormControl>
                    <Input type="number" min={1} max={12} {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="dueDay"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Day</FormLabel>
                  <FormControl>
                    <Input type="number" min={1} max={31} {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="dueYear"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Year</FormLabel>
                  <FormControl>
                    <Input type="number" min={2025} {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          
          <div className="grid grid-cols-2 gap-4">
            <FormField
              control={form.control}
              name="dueHour"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Hour (24h)</FormLabel>
                  <FormControl>
                    <Input type="number" min={0} max={23} {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <FormField
              control={form.control}
              name="dueMinute"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Minute</FormLabel>
                  <FormControl>
                    <Input type="number" min={0} max={59} {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
          
          <FormField
            control={form.control}
            name="taskPriority"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Priority</FormLabel>
                <Select onValueChange={field.onChange} defaultValue={field.value}>
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select priority" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value={Priority.HIGH}>High</SelectItem>
                    <SelectItem value={Priority.MEDIUM}>Medium</SelectItem>
                    <SelectItem value={Priority.LOW}>Low</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage />
              </FormItem>
            )}
          />
          
          <div className="flex justify-end space-x-2 pt-4">
            {onCancel && (
              <Button type="button" variant="outline" onClick={onCancel}>
                Cancel
              </Button>
            )}
            <Button type="submit" className="bg-app-primary hover:bg-blue-700">
              Create Task
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
};

export default TaskForm;
