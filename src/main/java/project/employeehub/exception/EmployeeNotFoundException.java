package project.employeehub.exception;

public class EmployeeNotFoundException extends RuntimeException { //Inheritance concept: extends
    //Without RuntimeException, java wont consider it as an exception class.

    public EmployeeNotFoundException(String message) { //Constructor same as class name
        super(message); // Super means pass the message to parent class. RuntimeException
    }
}
