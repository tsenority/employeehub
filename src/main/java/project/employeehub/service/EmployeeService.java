package project.employeehub.service;

import jakarta.validation.Valid;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.entity.Employee;

import java.util.List;

//This class is for Contract. employeeServiceImpl class is where we do the actual work.
public interface EmployeeService {  //Interface meaning must follow its contract and methods.
                                    //it allows to declare methods without implementation or logic. so, no braces.
    //Return type, method name, object as method parameter.

    //Just Declaring methods in this Interface. Actual logic goes in the Implementation class.

    //FIVE CRUD METHODS
    //1. CREATE EMPLOYEE METHOD
    Employee createEmployee(@Valid EmployeeRequest employeeRequest);//method to create employees.

    //2. GET OR FIND ALL THE EMPLOYEES method
    List<Employee> getAllEmployees();//method to return list of all Employee objects


    //2.. GET OR FIND EMPLOYEE BY ID(Particular Employee) Method
    Employee getEmployeeById(Long Id);

    //4.. UPDATE EMPLOYEE METHOD
    Employee updateEmployee(Long id, Employee employee); /* Long id = Which id should be updated
    Example : updateEmployee(5, employee)
             ↓
    Find employee #5
            ↓
    Change its information
             ↓
    Save it
             ↓
    Return updated employee
    */

    // 5. DELETE EMPLOYEE METHOD , No need of object parameter here because we don't need other fields here.
    void deleteEmployee(Long id);  // void because it doesn't return any employee. it deletes.

}
