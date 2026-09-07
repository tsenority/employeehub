package com.ecommerce.employeehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity   //tells --this java class represents data that should be stored in database table.
public class Employee {

    @Id  //this field is primary key that uniquely identifies an Employee.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//database will create ID in order like 1,2,3. No manual needed.
    private Long id;

    //used private identifier so that other classes cant directly access the field. Encapsulation oops concept.
    //can be accessed through the use of methods. Not directly.
    private String firstName;
    private String lastName;
    private String email;
    private String phone; //phone numbers can have dashes and area codes.
    private double salary;  //can contain decimal values. example : 62500.50


    /*GETTER AND SETTER METHOD. can generate from intellij directly.
    private field=protects the data. Getter=read it. Setter =change in a controlled way.
    In simple terms, by setting the field private, we allow others to access only through our getter adn setter method
     and they can set or change values according to our setter method conditions only.
     This is the concept of OOPs encapsulation. The object controls how its internal data can be accessed and changed.
     */


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
