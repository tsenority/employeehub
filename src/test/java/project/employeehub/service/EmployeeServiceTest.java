package project.employeehub.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.dto.EmployeeResponse;
import project.employeehub.entity.Department;
import project.employeehub.entity.Employee;
import project.employeehub.repository.DepartmentRepository;
import project.employeehub.repository.EmployeeRepository;
import project.employeehub.service.EmployeeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks  //injects the mocks to employeeService
    private EmployeeServiceImpl employeeService;
   /*  EmployeeRepository mock ──┐
            ├──→ EmployeeServiceImpl
         DepartmentRepository mock ─┘*/


    @BeforeEach
        //Run this method immediately before every test method.
    void setUp() {
        MockitoAnnotations.openMocks(this);
        /*
        "Look at this test class and initialize its Mockito annotations."
        this = "This current EmployeeServiceTest object."
        means: "Before every test, Mockito, initialize all the Mockito objects in this test class."

            That's the mental model I'd remember.

                JUnit controls WHEN the setup happens.
                Mockito controls WHAT gets initialized.
         */
    }

    @Test
        // "This method is a test. First, we'll create a fake Employee.
        //JUnit + Mockito are testing the methods/Behaviors inside your real EmployeeServiceImpl class.
    void testGeEmployedById() {
        Employee employee = new Employee();
        employee.setId(7L);
        employee.setFirstName("John");
        employee.setLastName("Updated");
        employee.setEmail("john@example.com");

        when(employeeRepository.findById(7L))
                .thenReturn(Optional.of(employee));
        //"When the employee repository is asked for employee 7, return this employee."

        EmployeeResponse response = employeeService.getEmployeeById(7L);

        assertEquals("John", response.getFirstName());
        assertEquals(7L, response.getId());
        assertEquals("Updated", response.getLastName());
        assertEquals("john@example.com", response.getEmail());

        verify(employeeRepository).findById(7L); //to verify if the service called repository
        /*  JUnit → runs the test
            Mockito → provides fake dependencies
            EmployeeServiceImpl → REAL code being tested */
    }

    @Test
        //to test non existent employee
    void testGetEmployeeByIdNotFound() {

        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());
        //"When the repository can't find employee 99, verify that the real service throws a RuntimeException."
        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById(99L);

            verify(employeeRepository, never()).deleteById(99L); //never() = Never call this line.
        });
    }

    @Test
    void testCreateEmployee() {
        //1. Arrange — create the input
        EmployeeRequest request = new EmployeeRequest();

        request.setFirstName("Mike");
        request.setLastName("Smith");
        request.setEmail("mike@example.com");
        request.setPhone("801-555-2222");
        request.setSalary(60000);
        request.setDepartmentId(1L);

        //2. Create a fake Department
        Department department = new Department();

        department.setId(1L);
        department.setName("Human Resource");

        //3. Tell Mockito what DepartmentRepository should return
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));


        //4. Create the fake saved Employee
        Employee savedEmployee = new Employee();

        savedEmployee.setId(10L);
        savedEmployee.setFirstName("Mike");
        savedEmployee.setLastName("Smith");
        savedEmployee.setEmail("mike@example.com");
        savedEmployee.setPhone("801-555-2222");
        savedEmployee.setSalary(60000);
        savedEmployee.setDepartment(department);

        //5. Tell Mockito what save() should return
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        //6. Act — call the REAL service
        EmployeeResponse response =
                employeeService.createEmployee(request);

        //7. Assert — check the result
        assertEquals(10L, response.getId());
        assertEquals("Mike", response.getFirstName());
        assertEquals("Smith", response.getLastName());
        assertEquals("mike@example.com", response.getEmail());
        assertEquals("Human Resource", response.getDepartmentName());

        verify(departmentRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
        /*
        These assertions check that the real service correctly converted the request + department + saved employee
         into the EmployeeResponse.
         */
    }

    @Test
    void testDeleteEmployee() {

        Employee employee = new Employee();//Each test is isolated, and the Employee object you create in one test
        // is not automatically available in another test. we aren't actually storing these employees in MySQL.
        employee.setId(10L);

        when(employeeRepository.findById(10L))
                .thenReturn(Optional.of(employee));//"When the service looks for employee #10, pretend that employee exists."

        employeeService.deleteEmployee(10L);

        verify(employeeRepository, times(1))
                // times: 1 or 2 ,"Verify that deleteById(10L) was called once or twice etc.."
                .deleteById(10L);

    }

    //SOME JUNIT KEYWORDS. no mockito involved here.
    //testAssertTrue() , testAssertTrue() ,testAssertNotNull(), testAssertNotEquals()
    @Test
    void testAssertTrue() { //Asks if this condition is true

        int salary = 60000;

        assertTrue(salary > 0);

    }

    @Test
    void testAssertFalse() { //opposite of testAsserttrue

        int salary = 60000;

        assertFalse(salary < 0);
    }

    @Test
    void testAssertNotNull() {

        Employee employee = new Employee();

        assertNotNull(employee);
    }

    @Test
    void testAssertNotEquals() {

        int salary = 60000;

        assertNotEquals(50000, salary);
    }

    @BeforeEach
        //You've already used it for Mockito, But @BeforeEach is actually a JUnit feature
    void beforeEachTest() {

        System.out.println("Before Test!");
    }

    @AfterEach
        //After Test
    void tearDown() {
        System.out.println("After test!");
    }

    @Test
    void testAssertAll() { //Group multiple assertions in one group.

        int salary = 60000;

        assertAll(
                () -> assertEquals(60000, salary), //lambda expression
                () -> assertTrue(salary > 0),
                () -> assertNotEquals(50000, salary)
        );

    }

    @BeforeAll //Runs only once for the whole project.
    static void beforeAllTests() { //Runs once for the whole class, so it is normally static.
        System.out.println("Starting all tests!");
    }

}


/*  ✅ You just completed another common unit-test pattern.

You now have tests for:

✅ getEmployeeById()
✅ getEmployeeById() — not found
✅ createEmployee()
✅ deleteEmployee()

/*
Mock the repository

Your real EmployeeServiceImpl needs:

EmployeeRepository

and:

DepartmentRepository

But during a unit test, we don't want to connect to MySQL.

So Mockito will create fake versions of those repositories.

Add these fields:

@Mock
private EmployeeRepository employeeRepository;

@Mock
private DepartmentRepository departmentRepository;

Read the test like a story
GIVEN
an employee named John
        ↓
WHEN
repository searches for employee #7
        ↓
MOCK says "Here is John"
        ↓
WHEN
EmployeeService gets employee #7
        ↓
THEN
the response should contain "John"
        ↓
assertEquals("John", response.getFirstName())

This is the basic pattern you'll see constantly:

Arrange → Act → Assert

ARRANGE
Set up test data + mock behavior
        ↓
ACT
Call the method you're testing
        ↓
ASSERT
Check whether the result is correct

Run the test.
 */