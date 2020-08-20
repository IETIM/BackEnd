package edu.eci.ieti.ProjectIeti.services;

public class ExceptionProject extends Exception {
    public static final String USER_REGISTERED = "User already registered" ;
    public static final String USER_NOT_FOUND = "User not found";

    public ExceptionProject(){
        super();
    }
    public ExceptionProject(String message){
        super(message);
    }
}
