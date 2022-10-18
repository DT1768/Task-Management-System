package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {
    int role, department;
    String userId;

    JButton createUser,createDepartment, taskManager, notifications, signOut;
    JLabel welcome;

    public Dashboard(int role, int department, String userId) {
        this.role = role;
        this.department = department;
        this.userId = userId;

        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);

        welcome = new JLabel();
        welcome.setText( userId +"'s Dashboard");
        welcome.setBounds(0,0,500,50);
        welcome.setHorizontalTextPosition(JLabel.LEFT);
        welcome.setVerticalTextPosition(JLabel.CENTER);
        welcome.setForeground(new Color(0x000000));
        welcome.setFont(new Font("MV Boli",Font.BOLD,25));
        welcome.setIconTextGap(10);
        welcome.setBorder(border);
        welcome.setVerticalAlignment(JLabel.TOP);
        welcome.setHorizontalAlignment(JLabel.CENTER);

        createUser = new JButton();
        createUser.setBounds (25,60,200,50);
        createUser.addActionListener(this);
        createUser.setText("Employee Manager");
        createUser.setFocusable(false);
        createUser.setFont(new Font("Comic Sans",Font.BOLD,12));
        createUser.setForeground(Color.BLACK);


        createDepartment = new JButton();
        createDepartment.setBounds (250,60,200,50);
        createDepartment.addActionListener(this);
        createDepartment.setText("Department Manager");
        createDepartment.setFocusable(false);
        createDepartment.setFont(new Font("Comic Sans",Font.BOLD,12));
        createDepartment.setForeground(Color.BLACK);

        taskManager = new JButton();
        taskManager.setBounds (150,120,200,50);
        taskManager.addActionListener(this);
        taskManager.setText("Task Manager");
        taskManager.setFocusable(false);
        taskManager.setFont(new Font("Comic Sans",Font.BOLD,12));
        taskManager.setForeground(Color.BLACK);

        notifications = new JButton();
        notifications.setBounds (25,180,200,50);
        notifications.addActionListener(this);
        notifications.setText("Notifications");
        notifications.setFocusable(false);
        notifications.setFont(new Font("Comic Sans",Font.BOLD,12));
        notifications.setForeground(Color.BLACK);

        signOut = new JButton();
        signOut.setBounds (250,180,200,50);
        signOut.addActionListener(this);
        signOut.setText("Logout");
        signOut.setFocusable(false);
        signOut.setFont(new Font("Comic Sans",Font.BOLD,12));
        signOut.setForeground(Color.BLACK);

        this.setDefaultCloseOperation(SignInPage.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Dashboard");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(welcome);
        if(role == 1){
            this.add(createUser);
            this.add(createDepartment);
        }
        this.add(taskManager);
        if(role != 3){
            this.add(notifications);
        }
        this.add(signOut);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createUser){
            new EmployeeManager(role,department,userId);
        }
        else if(e.getSource() == createDepartment){
            new DepartmentManager(role,department,userId);
        }
        else if(e.getSource() == taskManager){
            new TaskManager(role, department, userId);
        }
        else if(e.getSource() == notifications){
            new Notifications(role, department, userId);
        }
        else if(e.getSource() == signOut){
            new SignInPage();
        }
        this.dispose();
    }
}
