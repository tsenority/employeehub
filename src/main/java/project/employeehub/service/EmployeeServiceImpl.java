package project.employeehub.service;

import jakarta.validation.Valid;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.entity.Employee;
import project.employeehub.exception.EmployeeNotFoundException;
import project.employeehub.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service //tells it is a service class.
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired //creates the connection to Repo class and creates its object.
    private EmployeeRepository employeeRepository;


    @Override //overrides method from EmployeeService interface
    public Employee createEmployee(@Valid EmployeeRequest employeeRequest) {

        Employee employee = new Employee();//EmployeeRequest is the incoming data, while Employee is the database entity.

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhone(employeeRequest.getPhone());
        employee.setSalary(employeeRequest.getSalary());

        return employeeRepository.save(employee);
    }
    /*
        Postman JSON
     ↓
EmployeeRequest
     ↓
EmployeeService
     ↓
Employee entity
     ↓
employeeRepository.save(employee)
     ↓
MySQL

        The important thing to understand is why we did this:

The controller receives a DTO because we don't want the outside API directly controlling our database entity.

Then the service converts the DTO into an Employee entity before saving it.
     */

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    /*  getAllEmployees()
      ↓
    employeeRepository.findAll()
      ↓
    Repository talks to database
      ↓
    Gets all Employee records
      ↓
    Returns List<Employee>  Now, we have Create And ReadALl employee methods.We didn't write Any SQL. Spring Data JPA handles that.
    */

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> //-> meaning lambda arrow expression to do the below codes.
                        //Means throw a default exception if there is no employee with particular id. E.g new employee();

                    new EmployeeNotFoundException( //Creates an object without referencing a variable.
                            "Employee not found with id: " + id));// 2 brackets because we are inside 2 method calls.
        /*Custom Exception: "If no employee is found, create an EmployeeNotFoundException containing a message that includes
         the employee ID, and throw it." */

    }



    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        //OBJET TYPE, VARIABLE NAME CREATION = GETS INFO FROM REPO
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow();

        //Using the Getter and Setter methods used in the Employee entity class.
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);


    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
