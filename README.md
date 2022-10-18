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

## Installation Guide:(Windows)

1) Install JDK and any Java IDE. I have used IntelliJ IDEA.

	JDK: 
		
	https://download.oracle.com/java/19/latest/jdk-19_windows-x64_bin.exe.
		
	IntelliJ IDEA: 
		
	https://www.jetbrains.com/idea/download/#section=windows

2) Install XAMPP server for MYSQL.

	https://www.apachefriends.org/download.html

3) download java mysql connector library.
	
	https://dev.mysql.com/downloads/file/?id=513754

4) setting up project:

		a) download project 
	
