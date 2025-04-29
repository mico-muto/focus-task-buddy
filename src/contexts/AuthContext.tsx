
import React, { createContext, useContext, useState, useEffect } from "react";
import { User } from "@/types/models";
import dataService from "@/services/dataService";
import { useToast } from "@/components/ui/use-toast";

interface AuthContextType {
  user: User | null;
  login: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
  register: (userData: Omit<User, "userId" | "isAdmin">) => Promise<boolean>;
  isAdmin: boolean;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const { toast } = useToast();
  
  useEffect(() => {
    // Check for existing session
    const currentUser = dataService.getCurrentUser();
    setUser(currentUser);
    setIsLoading(false);
  }, []);
  
  const login = async (username: string, password: string): Promise<boolean> => {
    try {
      const loggedInUser = dataService.login(username, password);
      
      if (loggedInUser) {
        setUser(loggedInUser);
        toast({
          title: "Login successful",
          description: `Welcome back, ${loggedInUser.firstName}!`,
        });
        return true;
      } else {
        toast({
          variant: "destructive",
          title: "Login failed",
          description: "Invalid username or password",
        });
        return false;
      }
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Login error",
        description: "An error occurred during login",
      });
      return false;
    }
  };
  
  const logout = () => {
    dataService.logout();
    setUser(null);
    toast({
      title: "Logged out",
      description: "You have been successfully logged out",
    });
  };
  
  const register = async (userData: Omit<User, "userId" | "isAdmin">): Promise<boolean> => {
    try {
      const existingUsers = dataService.getAllUsers();
      if (existingUsers.some(u => u.username === userData.username)) {
        toast({
          variant: "destructive",
          title: "Registration failed",
          description: "Username already exists",
        });
        return false;
      }
      
      const newUser = dataService.registerUser(userData);
      toast({
        title: "Registration successful",
        description: "Your account has been created",
      });
      return true;
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Registration error",
        description: "An error occurred during registration",
      });
      return false;
    }
  };
  
  return (
    <AuthContext.Provider 
      value={{ 
        user, 
        login, 
        logout, 
        register, 
        isAdmin: user?.isAdmin || false,
        isLoading
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
