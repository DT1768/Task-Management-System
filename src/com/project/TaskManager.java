package com.project;

import javax.print.attribute.ResolutionSyntax;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaskManager extends JFrame implements ItemListener,ActionListener, MouseListener {
    int department, role;
    String userId;

    JButton create;
    JButton update;
    JButton fetch;
    JButton delete;

    JLabel taskNameLabel;
    JLabel taskDescriptionLabel;
    JLabel taskDepartmentLabel;
    JLabel taskAssigneeLabel;
    JLabel taskStatusLabel;

    JTextField taskName;
    JTextArea taskDescription;
    JComboBox taskDepartment;
    JComboBox taskAssignee;
    JComboBox taskStatus;

    TaskManager(int role,int department, String userId) {
        this.role = role;
        this.department = department;
        this.userId = userId;
        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);
        Color color = new Color(0xFFFFFF);

        taskNameLabel = new JLabel();
        taskNameLabel.setText("Task Name:");
        taskNameLabel.setBounds(10,100,200,50);
        taskNameLabel.setHorizontalTextPosition(JLabel.LEFT);
        taskNameLabel.setVerticalTextPosition(JLabel.CENTER);
        taskNameLabel.setForeground(new Color(0x000000));
        taskNameLabel.setFont(font);
        taskNameLabel.setIconTextGap(10);
        taskNameLabel.setBorder(border);
        taskNameLabel.setVerticalAlignment(JLabel.TOP);
        taskNameLabel.setHorizontalAlignment(JLabel.CENTER);

        taskName = new JTextField();
        taskName.setFont(font);
        taskName.setBounds(250,100,200,50);
        taskName.setBackground(color);
        taskName.setBorder(border);

        taskDescriptionLabel = new JLabel();
        taskDescriptionLabel.setText("Description:");
        taskDescriptionLabel.setBounds(10,160,200,50);
        taskDescriptionLabel.setHorizontalTextPosition(JLabel.LEFT);
        taskDescriptionLabel.setVerticalTextPosition(JLabel.CENTER);
        taskDescriptionLabel.setForeground(new Color(0x000000));
        taskDescriptionLabel.setFont(font);
        taskDescriptionLabel.setIconTextGap(10);
        taskDescriptionLabel.setBorder(border);
        taskDescriptionLabel.setVerticalAlignment(JLabel.TOP);
        taskDescriptionLabel.setHorizontalAlignment(JLabel.CENTER);

        taskDescription = new JTextArea();
        taskDescription.setBounds(250,160,200,150);
        taskDescription.setBackground(color);
        taskDescription.setBorder(border);

        taskDepartmentLabel = new JLabel();
        taskDepartmentLabel.setText("Department:");
        taskDepartmentLabel.setBounds(10,320,200,50);
        taskDepartmentLabel.setHorizontalTextPosition(JLabel.LEFT);
        taskDepartmentLabel.setVerticalTextPosition(JLabel.CENTER);
        taskDepartmentLabel.setForeground(new Color(0x000000));
        taskDepartmentLabel.setFont(font);
        taskDepartmentLabel.setIconTextGap(10);
        taskDepartmentLabel.setBorder(border);
        taskDepartmentLabel.setVerticalAlignment(JLabel.TOP);
        taskDepartmentLabel.setHorizontalAlignment(JLabel.CENTER);

        if(department == 0){
            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;
            int i = 0;
            try{
                String SQL = "SELECT COUNT(DISTINCT(name)) AS departmenttotal FROM department";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    i = rs.getInt("departmenttotal");
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            String[] departmentlist = new String[i+1];
            departmentlist[0] = "Select";
            try{
                String SQL = "SELECT name FROM department";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);
                int j = 1;
                while(rs.next() && j<i+1){
                    departmentlist[j] = rs.getString("name");
                    j++;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            taskDepartment = new JComboBox(departmentlist);
        }
        else{
            String[] departmentlist = new String[2];
            departmentlist[0] = "Select";
            departmentlist[1] = String.valueOf(department);
            taskDepartment = new JComboBox(departmentlist);
        }
        taskDepartment.setBounds(250,320,200,50);
        taskDepartment.addItemListener(this);


        taskAssigneeLabel = new JLabel();
        taskAssigneeLabel.setText("Assignee:");
        taskAssigneeLabel.setBounds(10,380,200,50);
        taskAssigneeLabel.setHorizontalTextPosition(JLabel.LEFT);
        taskAssigneeLabel.setVerticalTextPosition(JLabel.CENTER);
        taskAssigneeLabel.setForeground(new Color(0x000000));
        taskAssigneeLabel.setFont(font);
        taskAssigneeLabel.setIconTextGap(10);
        taskAssigneeLabel.setBorder(border);
        taskAssigneeLabel.setVerticalAlignment(JLabel.TOP);
        taskAssigneeLabel.setHorizontalAlignment(JLabel.CENTER);




        taskAssignee = new JComboBox();
        taskAssignee.addItemListener(this);
        //taskAssignee.setEnabled(false);
        taskAssignee.setBounds(250,380,200,50);


        this.setDefaultCloseOperation(TaskManager.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1100,750);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Task Manager");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(taskNameLabel);
        this.add(taskName);
        this.add(taskDescriptionLabel);
        this.add(taskDescription);
        this.add(taskDepartmentLabel);
        this.add(taskDepartment);
        this.add(taskAssigneeLabel);
        this.add(taskAssignee);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == taskDepartment){
            String assigneeDepartment = String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()));
            //System.out.println(assigneeDepartment);
            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;
            int i = 0;
            int id = 0;
            try{
                String SQL = "SELECT id FROM department WHERE name="+"\""+assigneeDepartment+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);
                while(rs.next()){
                    id = rs.getInt("id");
                }
            }
            catch (Exception e3){
                System.out.println(e3);
            }
            try{
                String SQL = "SELECT COUNT(DISTINCT(userid)) AS departmentusers FROM employees WHERE department = "+ id + "";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    i = rs.getInt("departmentusers");
                }
                //System.out.println(i);
            }
            catch(Exception e2){
                System.out.println(e2);
            }
            String[] assigneeList = new String[i+1];
            assigneeList[0] = "Select";
            try{
                String SQL = "SELECT DISTINCT(userid) FROM employees WHERE department = "+ id + "";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);
                int j = 1;
                while(rs.next() && j<i+1){
                    assigneeList[j] = rs.getString("userid");
                    j++;
                }
            }
            catch(Exception e1){
                System.out.println(e1);
            }
            taskAssignee.removeAllItems();

            for(int k=0; k<assigneeList.length; k++){
                taskAssignee.addItem(assigneeList[k]);
            }
        }
    }
}
