
import { useAuth } from "@/contexts/AuthContext";
import AppLayout from "@/components/AppLayout";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import dataService from "@/services/dataService";
import { useEffect, useState } from "react";
import { Task } from "@/types/models";

const ProfilePage = () => {
  const { user } = useAuth();
  const [pendingTaskCount, setPendingTaskCount] = useState(0);
  const [completedTaskCount, setCompletedTaskCount] = useState(0);
  
  useEffect(() => {
    if (user) {
      const userTasks = dataService.getTasksByUserId(user.userId);
      setPendingTaskCount(userTasks.filter(task => !task.completed).length);
      setCompletedTaskCount(userTasks.filter(task => task.completed).length);
    }
  }, [user]);
  
  if (!user) return null;
  
  const getInitials = () => {
    return `${user.firstName.charAt(0)}${user.lastName.charAt(0)}`;
  };
  
  return (
    <AppLayout title="Profile">
      <div className="flex flex-col md:flex-row gap-6">
        <Card className="md:w-1/3">
          <CardContent className="pt-6">
            <div className="flex flex-col items-center">
              <Avatar className="h-24 w-24 mb-4">
                <AvatarImage src={user.profilePic || ""} alt={`${user.firstName} ${user.lastName}`} />
                <AvatarFallback className="text-lg">{getInitials()}</AvatarFallback>
              </Avatar>
              
              <h2 className="text-xl font-bold">{user.firstName} {user.lastName}</h2>
              <p className="text-gray-500">@{user.username}</p>
              <p className="text-sm text-gray-500 mt-2">{user.email}</p>
            </div>
          </CardContent>
        </Card>
        
        <div className="flex-1">
          <Card className="mb-6">
            <CardContent className="pt-6">
              <h3 className="font-bold mb-4">User Information</h3>
              <div className="grid grid-cols-2 gap-y-4">
                <div>
                  <p className="text-sm text-gray-500">Full Name</p>
                  <p>{user.firstName} {user.middleName} {user.lastName}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Email</p>
                  <p>{user.email}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Age</p>
                  <p>{user.age}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Gender</p>
                  <p>{user.gender}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Birthday</p>
                  <p>{user.birthMonth}/{user.birthDay}/{user.birthYear}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Account Type</p>
                  <p>{user.isAdmin ? "Administrator" : "Regular User"}</p>
                </div>
              </div>
            </CardContent>
          </Card>
          
          <Card>
            <CardContent className="pt-6">
              <h3 className="font-bold mb-4">Tasks Overview</h3>
              <div className="grid grid-cols-2 gap-4">
                <div className="p-4 bg-blue-50 rounded-md">
                  <p className="text-sm text-gray-500">Tasks Pending</p>
                  <p className="text-2xl font-bold text-app-primary">{pendingTaskCount}</p>
                </div>
                <div className="p-4 bg-green-50 rounded-md">
                  <p className="text-sm text-gray-500">Tasks Completed</p>
                  <p className="text-2xl font-bold text-green-600">{completedTaskCount}</p>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </AppLayout>
  );
};

export default ProfilePage;
