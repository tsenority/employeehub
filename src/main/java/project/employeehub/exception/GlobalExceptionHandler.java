package project.employeehub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //"This class contains exception-handling logic for my REST controllers."
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // returns 404 Not found message
    public String handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ex.getMessage(); //returns super(message); from EmployeeServiceImpl Class.
    }
}

/*  Postman
   ↓
GET /api/employees/999
   ↓
EmployeeController
   ↓
EmployeeService
   ↓
Repository
   ↓
Employee 999 doesn't exist
   ↓
EmployeeNotFoundException
   ↓
GlobalExceptionHandler
   ↓
@ExceptionHandler catches it
   ↓
@ResponseStatus → 404
   ↓
ex.getMessage()
   ↓
"Employee not found with id: 999"

So Postman should eventually show something like:

404 Not Found

and:

Employee not found with id: 999

 */
