
import { ReactNode } from "react";
import { Link, useLocation } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";
import { Button } from "@/components/ui/button";
import { LogOut, User } from "lucide-react";

interface AppLayoutProps {
  children: ReactNode;
  title: string;
}

const AppLayout: React.FC<AppLayoutProps> = ({ children, title }) => {
  const { user, logout } = useAuth();
  const location = useLocation();
  
  const navItems = [
    { name: "Dashboard", path: "/" },
    { name: "My Tasks", path: "/my-tasks" },
    { name: "Completed", path: "/completed" }
  ];
  
  return (
    <div className="min-h-screen bg-app-blue">
      {/* Main content area */}
      <div className="flex flex-col md:flex-row">
        {/* Side navigation */}
        <div className="w-full md:w-40 md:min-h-screen p-4 bg-app-blue flex flex-col">
          <div className="space-y-2 flex-1">
            {navItems.map((item) => (
              <Link 
                key={item.path} 
                to={item.path}
                className={`block p-2 ${
                  location.pathname === item.path 
                    ? "font-bold text-app-primary" 
                    : "text-gray-700 hover:text-app-primary"
                }`}
              >
                {item.name}
              </Link>
            ))}
          </div>
          
          {/* Profile link at bottom */}
          <div className="pt-4">
            <Link 
              to="/profile"
              className={`block p-2 ${
                location.pathname === "/profile" 
                  ? "font-bold text-app-primary" 
                  : "text-gray-700 hover:text-app-primary"
              }`}
            >
              Profile
            </Link>
            <Button 
              variant="ghost" 
              className="w-full justify-start p-2 text-gray-700 hover:text-red-500"
              onClick={logout}
            >
              <LogOut className="mr-2 h-4 w-4" />
              Log Out
            </Button>
          </div>
        </div>
        
        {/* Main content */}
        <div className="flex-1 p-6">
          <h1 className="text-2xl font-bold mb-6">{title}</h1>
          {children}
        </div>
      </div>
    </div>
  );
};

export default AppLayout;
