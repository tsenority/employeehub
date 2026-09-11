package project.employeehub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //"This class contains exception-handling logic for my REST controllers."
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // returns 404 Not found message
    public String handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ex.getMessage(); //returns super(message); from EmployeeServiceImpl Class.
    }

    // TO CREATE A SMALL ERROR MESSAGE OR CLEAN MESSAGE THAN GIANT STACK TRACE OF ERRORS FOR VALIDATION.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return errors;
    }
        }

   /* public String handleValidationException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

  getFieldError()
      ↓
     ONE error validation output

getFieldErrors()
      ↓
   ALL errors  validation output


  Excellent. 🎯 That means your EmployeeHub validation system is working end-to-end.

You now have a pretty realistic Spring Boot validation flow:

Postman
   ↓
EmployeeController
   ↓
@Valid
   ↓
Employee validation rules
   ↓
Validation fails
   ↓
MethodArgumentNotValidException
   ↓
GlobalExceptionHandler
   ↓
All validation errors
   ↓
400 Bad Request
*/

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

/*
Validation section is basically complete

You now have:

@NotBlank
@Email
@Positive
@Valid
MethodArgumentNotValidException
GlobalExceptionHandler
Map<String, String>

These are all useful concepts for an entry-level Spring Boot developer to understand.
 */

/*Excellent. 🎯 Validation is now complete for this stage of EmployeeHub.

You successfully tested both sides:

Operation	Invalid data	Valid data
POST Create	❌ 400	✅ 200
PUT Update	❌ 400	✅ 200
What you built

Your EmployeeHub now has:

Employee
 ├── @NotBlank firstName
 ├── @NotBlank lastName
 ├── @NotBlank + @Email email
 ├── @NotBlank phone
 └── @Positive salary

        ↓

Controller
 ├── POST @Valid
 └── PUT  @Valid

        ↓

GlobalExceptionHandler
 └── MethodArgumentNotValidException
        ↓
All validation errors
        ↓
                400 Bad Request

That's a real Spring Boot pattern, not just a tutorial exercise.

 */

/*  What should you actually learn?

You don't need to memorize the entire boilerplate.

For your entry-level Java training, understand these concepts:

        1. MethodArgumentNotValidException
→ Spring throws this when @Valid fails.

        2. getFieldErrors()
→ Get all validation errors.

        3. error.getField()
→ Get the field that failed.

4. error.getDefaultMessage()
→ Get the validation message.

        5. Map<String, String>
→ Store field → error message.

The exact HashMap/forEach syntax can easily be looked up or generated with AI at work.

        That's actually a good example of what we've been discussing: developers don't need to memorize every piece of boilerplate.
They need to understand what the code is doing and be able to modify/debug it.

You can use essentially the same pattern in:

Employee Management System
E-commerce API
Student Management System
Banking application
Inventory system
etc.

 */
