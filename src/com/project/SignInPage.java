package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignInPage extends JFrame implements ActionListener {
    JLabel label;
    JLabel label1;
    JTextField id;
    JLabel label2;
    JPasswordField password;
    JButton signin;
    JLabel message;

    SignInPage(){
        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);

        label = new JLabel();
        label.setText("Employee Task Management App");
        label.setBounds(0,0,480,50);
        label.setHorizontalTextPosition(JLabel.LEFT);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setForeground(new Color(0x000000));
        label.setFont(new Font("MV Boli",Font.BOLD,25));
        label.setIconTextGap(10);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);

        //label for first name
        label1 = new JLabel();
        label1.setText("User ID:");
        label1.setBounds(10,100,200,50);
        label1.setHorizontalTextPosition(JLabel.LEFT);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setForeground(new Color(0x000000));
        label1.setFont(font);
        label1.setIconTextGap(10);
        label1.setBorder(border);
        label1.setVerticalAlignment(JLabel.TOP);
        label1.setHorizontalAlignment(JLabel.CENTER);

        //First Name Text Field
        id = new JTextField();
        id.setFont(font);
        id.setBounds(250,100,200,50);
        id.setBackground(new Color(0xFFFFFF));
        id.setBorder(border);

        //Last Name Label
        label2 = new JLabel();
        label2.setText("Password:");
        label2.setBounds(10,160,200,50);
        label2.setHorizontalTextPosition(JLabel.LEFT);
        label2.setVerticalTextPosition(JLabel.CENTER);
        label2.setForeground(new Color(0x000000));
        label2.setFont(font);
        label2.setIconTextGap(10);
        label2.setBorder(border);
        label2.setVerticalAlignment(JLabel.TOP);
        label2.setHorizontalAlignment(JLabel.CENTER);


        password = new JPasswordField();
        password.setFont(font);
        password.setBounds(250,160,200,50);
        password.setEchoChar('*');
        password.setBackground(new Color(0xFFFFFF));
        password.setBorder(border);

        signin = new JButton();
        signin.setBounds (200,220,100,50);
        signin.addActionListener(this);
        signin.setText("Sign In");
        signin.setFocusable(false);
        signin.setFont(new Font("Comic Sans",Font.BOLD,12));
        signin.setForeground(Color.BLACK);

        message = new JLabel();
        message.setText("Message");
        message.setBounds(0,280,480,50);
        message.setHorizontalTextPosition(JLabel.LEFT);
        message.setVerticalTextPosition(JLabel.CENTER);
        message.setBackground(new Color(0xFFFFFF));
        message.setOpaque(true);
        message.setForeground(new Color(0x000000));
        message.setFont(new Font("MV Boli",Font.BOLD,20));
        message.setIconTextGap(10);
        message.setBorder(border);
        message.setVerticalAlignment(JLabel.TOP);
        message.setHorizontalAlignment(JLabel.CENTER);

        this.setDefaultCloseOperation(SignInPage.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Sign In");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(label);
        this.add(label1);
        this.add(label2);
        this.add(id);
        this.add(password);
        this.add(signin);
        this.add(message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signin){
            if(authentication()){
                Connection con = null;
                con = connection.connectDB();
                PreparedStatement ps = null;

                String user = id.getText();
                char[] p = password.getPassword();
                String pass = new String(p);

                try {

                    String sql = "select * from employees where BINARY UserID = ? and BINARY Password = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1,user);
                    ps.setString(2,pass);
                    ResultSet rs = ps.executeQuery();
                    int department,role;
                    String userId;
                    //String tasksassigned = "";
                    while(rs.next()){
                        role = rs.getInt("role");
                        department = rs.getInt("department");
                        userId = rs.getString("userid");
                        message.setText(String.valueOf(role));
                        new Dashboard(role,department,userId);
                        this.dispose();
                    }
                }
                catch (Exception e1) {
                    System.out.println(e1);
                }
            }
            else{
                message.setText("Invalid UserID/Password.");
            }
        }
    }

    public boolean authentication(){
        Connection con = null;
        con = connection.connectDB();
        PreparedStatement ps = null;

        String user = id.getText();
        char[] p = password.getPassword();
        String pass = new String(p);
        boolean valid = false;
        try {

            String sql = "select * from employees where BINARY UserID = ? and BINARY Password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2,pass);
            ResultSet s = ps.executeQuery();

            if(s.next()){
                valid = true;
            }
            else{
                valid = false;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return valid;
    }
}
