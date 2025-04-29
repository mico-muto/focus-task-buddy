
import { Navigate } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";

const Index = () => {
  const { user, isLoading } = useAuth();
  
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-app-blue">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-app-primary"></div>
      </div>
    );
  }
  
  // If user is logged in, show the dashboard
  if (user) {
    return <Navigate to="/dashboard" replace />;
  }
  
  // If not logged in, redirect to login page
  return <Navigate to="/login" replace />;
};

export default Index;
