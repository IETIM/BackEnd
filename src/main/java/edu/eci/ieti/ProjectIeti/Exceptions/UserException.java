package edu.eci.ieti.ProjectIeti.Exceptions;

public class UserException extends Exception {
    public static final String USER_REGISTERED = "User already registered" ;
    public static final String USER_NOT_FOUND = "User not found";

    public UserException(){
        super();
    }
    public UserException(String message){
        super(message);
    }
}
