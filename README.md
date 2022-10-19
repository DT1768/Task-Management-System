# Task Management System

## Description
This task management system is a scalable system developed in java for the organization's task management. It uses MYSQL as a database to store employee and task data. The GUI is developed using swing and AWT.

This system contains major 5 components:
1) **Employees** :  Where all employees data is stored.
2) **Task** :  Where all task details is stored.
3) **department** :  Used to store all department's name.
4) **roles** :  Used to store all role's name.
5) **notifications**:  Used to store activities done in task. such as creation, updation, deletion.
 
The system has 3 main roles.
1) General Manager
2) Department Manager
3) Employee

General Manager have all permission to create, modify, delete tasks in all departments. Each department can have one Department Manager only. Department Manager can create and assign tasks to the employees of their respective department only. Where as employees can only access tasks which are unassigned or assigned to them. employees can assign tasks to themselves only.

## Project Installation and Setup:

1) Install JDK and any Java IDE. I have used IntelliJ IDEA.

	JDK: 
		
	https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.exe
		
	IntelliJ IDEA: 
		
	https://www.jetbrains.com/idea/download/#section=windows

2) Install XAMPP server for MYSQL.

	https://www.apachefriends.org/download.html

3) Download java mysql connector library.
	
	https://dev.mysql.com/downloads/file/?id=513754

4) Setting up project and libraries:

	a) download project and extract zip file.
	
	b) open folder as a project in IntelliJ IDEA. (src/com/project)
	
	c) extract jar file from java MYSQL connector from step-3.
	
	d) go to `file > project structure > project settings > libraries`. click on + sign then locate and add jar file of java mysql connector.
		
5) Setting up Database:

	a) open XAMPP control panel and start Apache and MySQL module.
	
	b) open MySQL Admin and create new database called "cts". (you can name whatever you want, but you need to make changes in code accordingly.)
	
	c) Go to SQL and run the following queries in order.
	
	`SET foreign_key_checks = 0;`
	
	Roles Table:
	
	`CREATE TABLE roles (id int primary key not null AUTO_INCREMENT, name varchar(20));`
	
	`INSERT INTO roles(name) values("General Manager");`
	
	`INSERT INTO roles(name) values("Department Manager");`
	
	`INSERT INTO roles(name) values("Employee");`
	
	(Make sure General Manager, Department Manager & employees has id 1,2 & 3 respectively.)
	
	Department Table:
	
	`CREATE TABLE department (id int primary key AUTO_INCREMENT, name varchar(20));`
	
	`INSERT INTO department(id,name) values(0,"General Manager");`
	
	(Other departments can be created in system using GUI.)
	
	Employees Table:
	
	`CREATE TABLE employees (id int primary key AUTO_INCREMENT, firstname varchar(20), lastname varchar(20), userid varchar(20), password varchar(20), email varchar(30),role int, department int, tasksassigned int DEFAULT 0, FOREIGN KEY (role) REFERENCES roles(id), FOREIGN KEY (department) REFERENCES department(id));`

	`INSERT INTO employees(firstname,lastname,userid,password,email,role,department) values("Dhruv","Thakkar","dhruv17","Dhruv17@","dhruv17@gmail.com",1,0);`
	
	(dhruv17 and Dhruv17@ will serve as id password for general manager.)
	
	Task Table:
	
	`CREATE TABLE task (id int PRIMARY KEY NOT NULL AUTO_INCREMENT,name varchar(20),description varchar(200),department int, assignee int,status varchar(20),FOREIGN KEY (assignee) REFERENCES employees(id),FOREIGN KEY (department) REFERENCES department(id));`
	
	Notifications Table:
	
	`CREATE TABLE notifications (date DATETIME DEFAULT CURRENT_TIMESTAMP,id int PRIMARY KEY NOT NULL AUTO_INCREMENT,department int, actor int,action varchar(200),FOREIGN KEY (actor) REFERENCES employees(id),FOREIGN KEY (department) REFERENCES department(id));`
	
	go to privileges in phpMyAdmin from XAMPP server MySQL admin create a new localhost user with password.
	
	i.e I have user - dhruv and password - 1234
	
	## Give database access to that user.
	
6) Configuring database path:

	Go to `connection.java` file:
	
	In line 11:
		
		`Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cts", "dhruv", "1234");`
		
	Change:
		`Connection con = DriverManager.getConnection("jdbc:mysql://localhost:<your MySQL port (From XAMPP server)>/<database name>", "<User id>", "<password>");`
	OR
		`Connection con = DriverManager.getConnection("jdbc:mysql://localhost:<your MySQL port (From XAMPP server)>/<database name>");`

7)Go to Demo.java and run the main method.
