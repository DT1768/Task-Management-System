package com.project;

public class Employee {
    String fname;
    String lname;
    String userid;
    String password;
    String email;
    String role;
    String department;
    int tasksAssigned;

    Employee(){
        fname="abc";
        lname="def";
        userid="abc123";
        password="def456";
        email="abc123@gmail.com";
        role = "worker";
        department = "";
        tasksAssigned = 0;
    }

    Employee(String fname, String lname, String userid, String password, String email, String role, String department, int tasksAssigned) {
        this.fname = fname;
        this.lname = lname;
        this.userid = userid;
        this.password = password;
        this.email = email;
        this.role = role;
        this.department = department;
        this.tasksAssigned = tasksAssigned;
    }

    Employee(Employee employee){
        this.fname = employee.fname;
        this.lname = employee.lname;
        this.userid = employee.userid;
        this.password = employee.password;
        this.email = employee.email;
        this.role = employee.role;
        this.department = employee.department;
        this.tasksAssigned = employee.tasksAssigned;
    }
}
