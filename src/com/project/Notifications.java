package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Notifications extends JFrame implements ActionListener {
    int role,department;
    String userId;

    JButton back,signout;
    JLabel heading;

    DefaultTableModel model;
    JTable table;
    JScrollPane scrollpane;

    public Notifications(int role, int department, String userId) {
        this.role = role;
        this.department = department;
        this.userId = userId;

        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);
        Color color = new Color(0xFFFFFF);

        heading = new JLabel();
        heading.setText("Notifications");
        heading.setBounds(0,0,700,50);
        heading.setHorizontalTextPosition(JLabel.LEFT);
        heading.setVerticalTextPosition(JLabel.CENTER);
        heading.setForeground(new Color(0x000000));
        heading.setFont(new Font("MV Boli",Font.BOLD,25));
        heading.setIconTextGap(10);
        heading.setBorder(border);
        heading.setVerticalAlignment(JLabel.TOP);
        heading.setHorizontalAlignment(JLabel.CENTER);

        model = new DefaultTableModel(new String[]{"date","id","department","actor","action"},0);

        table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setMaxWidth(160);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(120);
        table.getColumnModel().getColumn(3).setMaxWidth(80);
        table.getColumnModel().getColumn(4).setMaxWidth(200);
        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(40);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.setBackground(new Color(0xdfcdcd));

        scrollpane = new JScrollPane(table);
        scrollpane.setBounds(30,60,605,600);
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setBackground(new Color(0xcddfcd));


        model.getDataVector().removeAllElements();
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            String sqltable = null;
            if(role == 1){
                sqltable = "SELECT * FROM notifications ORDER BY date DESC";

            }
            else if(role == 2){
                sqltable = "SELECT * FROM notifications WHERE department = " + this.department + " ORDER BY date DESC" ;
            }
            ps = con.prepareStatement(sqltable);
            ResultSet rs = ps.executeQuery(sqltable);
            while (rs.next()) {
                String a = rs.getString("date");
                int b = rs.getInt("id");
                int c = rs.getInt("department");
                int d = rs.getInt("actor");
                String e = rs.getString("action");
                String dept = null;
                String assign = null;
                try {
                    PreparedStatement psdept;
                    String sqldept = "SELECT name FROM department WHERE id =" + c;
                    psdept = con.prepareStatement(sqldept);
                    ResultSet rsdept = psdept.executeQuery(sqldept);
                    while (rsdept.next()) {
                        dept = rsdept.getString("name");
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                try {
                    PreparedStatement psassign;
                    String sqlassign = "SELECT userid FROM employees WHERE id =" + d;
                    psassign = con.prepareStatement(sqlassign);
                    ResultSet rsassign = psassign.executeQuery(sqlassign);
                    while (rsassign.next()) {
                        assign = rsassign.getString("userid");
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                model.addRow(new Object[]{a, b, dept, assign, e});
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

        back = new JButton();
        back.setBounds (100,670,100,50);
        back.addActionListener(this);
        back.setText("Back");
        back.setFocusable(false);
        back.setFont(new Font("Comic Sans",Font.BOLD,12));
        back.setForeground(Color.BLACK);

        signout = new JButton();
        signout.setBounds (500,670,100,50);
        signout.addActionListener(this);
        signout.setText("Log out");
        signout.setFocusable(false);
        signout.setFont(new Font("Comic Sans",Font.BOLD,12));
        signout.setForeground(Color.BLACK);

        this.setDefaultCloseOperation(TaskManager.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(700,760);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Notifications");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(heading);
        this.add(scrollpane);
        this.add(back);
        this.add(signout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            new Dashboard(role,department,userId);
            this.dispose();
        }
        else if(e.getSource() == signout){
            new SignInPage();
            this.dispose();
        };
    }
}
