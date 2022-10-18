package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentManager extends JFrame implements ActionListener {
    JLabel heading,status;
    JLabel departmentNameLabel;

    int role,department;

    String userId;

    JTextField departmentName;

    JButton create, back, logout;

    DepartmentManager(int role, int department, String userId){

        this.role = role;
        this.department = department;
        this.userId = userId;

        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);
        Color color = new Color(0xFFFFFF);

        heading = new JLabel();
        heading.setText("Create Department");
        heading.setBounds(0,0,500,50);
        heading.setHorizontalTextPosition(JLabel.LEFT);
        heading.setVerticalTextPosition(JLabel.CENTER);
        heading.setForeground(new Color(0x000000));
        heading.setFont(new Font("MV Boli",Font.BOLD,25));
        heading.setIconTextGap(10);
        heading.setBorder(border);
        heading.setVerticalAlignment(JLabel.TOP);
        heading.setHorizontalAlignment(JLabel.CENTER);

        departmentNameLabel = new JLabel();
        departmentNameLabel.setText("Name:");
        departmentNameLabel.setBounds(10,100,200,50);
        departmentNameLabel.setHorizontalTextPosition(JLabel.LEFT);
        departmentNameLabel.setVerticalTextPosition(JLabel.CENTER);
        departmentNameLabel.setForeground(new Color(0x000000));
        departmentNameLabel.setFont(font);
        departmentNameLabel.setIconTextGap(10);
        departmentNameLabel.setBorder(border);
        departmentNameLabel.setVerticalAlignment(JLabel.TOP);
        departmentNameLabel.setHorizontalAlignment(JLabel.CENTER);

        departmentName = new JTextField();
        departmentName.setFont(font);
        departmentName.setBounds(250,100,200,50);
        departmentName.setBackground(color);
        departmentName.setBorder(border);

        create = new JButton();
        create.setBounds (50,200,100,50);
        create.addActionListener(this);
        create.setText("Create");
        create.setFocusable(false);
        create.setFont(new Font("Comic Sans",Font.BOLD,12));
        create.setForeground(Color.BLACK);

        back = new JButton();
        back.setBounds (200,200,100,50);
        back.addActionListener(this);
        back.setText("Back");
        back.setFocusable(false);
        back.setFont(new Font("Comic Sans",Font.BOLD,12));
        back.setForeground(Color.BLACK);

        logout = new JButton();
        logout.setBounds (350,200,100,50);
        logout.addActionListener(this);
        logout.setText("Log out");
        logout.setFocusable(false);
        logout.setFont(new Font("Comic Sans",Font.BOLD,12));
        logout.setForeground(Color.BLACK);

        status = new JLabel();
        status.setText("Status");
        status.setBounds(0,300,500,50);
        status.setHorizontalTextPosition(JLabel.LEFT);
        status.setVerticalTextPosition(JLabel.CENTER);
        status.setBackground(color);
        status.setOpaque(true);
        status.setForeground(new Color(0x000000));
        status.setFont(new Font("MV Boli",Font.BOLD,20));
        status.setIconTextGap(10);
        status.setBorder(border);
        status.setVerticalAlignment(JLabel.TOP);
        status.setHorizontalAlignment(JLabel.CENTER);

        this.setDefaultCloseOperation(TaskManager.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(520,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Task Manager");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(heading);
        this.add(departmentNameLabel);
        this.add(departmentName);
        this.add(create);
        this.add(back);
        this.add(logout);
        this.add(status);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == create){
           if(uniqueDepartment()){
               create();
           }
        }
        else if(e.getSource() == back){
            this.dispose();
            new TaskManager(role,department,userId);
        }
        else if(e.getSource()  == logout){
            this.dispose();
            new SignInPage();
        }
    }
    public void create(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String dname = departmentName.getText();
        try {
            String sql = "INSERT INTO department(name) values(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, dname);
            ps.execute();
        }
        catch (Exception e) {
            System.out.println(e);
            status.setText("SQL Error.");
        }
    }

    public boolean uniqueDepartment(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String dname = departmentName.getText();
        boolean unique = false;
        try {
            String sql = "select * from department where name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dname);
            ResultSet s = ps.executeQuery();

            if(s.next()){
                unique = false;
                status.setText("Department Already Exist.");
            }
            else{
                unique = true;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return unique;
    }
}
