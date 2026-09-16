package project.employeehub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import project.employeehub.entity.Department;


@Entity   //tells --this java class represents data that should be stored in database table.
public class Employee {

    @Id  //this field is primary key that uniquely identifies an Employee.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database will create ID in order like 1,2,3. No manual needed.
    private Long id;

    //used private identifier so that other classes cant directly access the field. Encapsulation oops concept.
    //can be accessed through the use of methods. Not directly.
    @NotBlank   //Valiidation Concept : Firstname shouldn't be empty.
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email  //should be email format
    private String email;

    @NotBlank
    private String phone;//phone numbers can have dashes and area codes.

    @Positive  //Means must be greater than 0.
    private double salary;  //can contain decimal values. example : 62500.50



    @ManyToOne    //many employees belong to 1 department. also known as JPA relationship concept.
    @JoinColumn(name = "department_id") //connecting column
    private Department department; //Employee has a reference to a Department object. just like entity,service connection.

// GETTER AND SETTER METHOD: Because department is a private field, the rest of your application needs a way to read and change it.
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    /*GETTER AND SETTER METHOD. can generate from intellij directly.
    private field=protects the data. Getter=read it. Setter =change in a controlled way.
    In simple terms, by setting the field private, we allow others to access only through our getter adn setter method
     and they can set or change values according to our setter method conditions only.
     This is the concept of OOPs encapsulation. The object controls how its internal data can be accessed and changed.
     */

    public Long getId() {  //without this getter id method, id will be missing in postman.
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

//Most real work project use private for its fields. In that way, Other class can access indirectly
// through its methods with certain conditions

// private fields + getters/setters = a common way to implement encapsulation.
//This getter and setter methods are used later in the Service Implementation class.