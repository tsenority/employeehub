package project.employeehub.service;

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
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

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
