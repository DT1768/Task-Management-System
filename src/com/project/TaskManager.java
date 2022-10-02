package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Objects;

public class TaskManager extends JFrame implements ItemListener,ActionListener, MouseListener {
    int department, role;
    String userId;

    JButton create;
    JButton update;
    JButton fetch;
    JButton delete;
    JButton signOut;
    JButton reset;

    JLabel heading;
    JLabel taskNameLabel;
    JLabel taskDescriptionLabel;
    JLabel taskDepartmentLabel;
    JLabel taskAssigneeLabel;
    JLabel taskStatusLabel;
    JLabel status;

    JTextField taskName;
    JTextArea taskDescription;
    JComboBox taskDepartment;
    JComboBox taskAssignee;
    JComboBox taskStatus;

    DefaultTableModel model;
    JTable table;
    JScrollPane scrollpane;

    TaskManager(int role,int department, String userId) {
        this.role = role;
        this.department = department;
        this.userId = userId;
        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);
        Color color = new Color(0xFFFFFF);

        heading = new JLabel();
        heading.setText("Task Manager");
        heading.setBounds(0,0,500,50);
        heading.setHorizontalTextPosition(JLabel.LEFT);
        heading.setVerticalTextPosition(JLabel.CENTER);
        heading.setForeground(new Color(0x000000));
        heading.setFont(new Font("MV Boli",Font.BOLD,25));
        heading.setIconTextGap(10);
        heading.setBorder(border);
        heading.setVerticalAlignment(JLabel.TOP);
        heading.setHorizontalAlignment(JLabel.CENTER);

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
            departmentlist[0] = "select";
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
            departmentlist[0] = "select";

            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;

            try{
                String SQL = "SELECT name FROM department WHERE id = "+department;
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);
                while(rs.next()){
                    departmentlist[1] = rs.getString("name");
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
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
        taskAssignee.setBounds(250,380,200,50);

        taskStatusLabel = new JLabel();
        taskStatusLabel.setText("Status:");
        taskStatusLabel.setBounds(10,440,200,50);
        taskStatusLabel.setHorizontalTextPosition(JLabel.LEFT);
        taskStatusLabel.setVerticalTextPosition(JLabel.CENTER);
        taskStatusLabel.setForeground(new Color(0x000000));
        taskStatusLabel.setFont(font);
        taskStatusLabel.setIconTextGap(10);
        taskStatusLabel.setBorder(border);
        taskStatusLabel.setVerticalAlignment(JLabel.TOP);
        taskStatusLabel.setHorizontalAlignment(JLabel.CENTER);

        taskStatus = new JComboBox();
        taskStatus.addItemListener(this);
        taskStatus.setBounds(250,440,200,50);

        create = new JButton();
        create.setBounds (100,500,100,50);
        create.addActionListener(this);
        if(role == 1 || role == 2){
            create.setText("Create");
        }
        else{
            create.setText("Assign");
        }
        create.setFocusable(false);
        create.setFont(new Font("Comic Sans",Font.BOLD,12));
        create.setForeground(Color.BLACK);

        reset = new JButton();
        reset.setBounds (210,500,100,50);
        reset.addActionListener(this);
        reset.setText("Reset");
        reset.setFocusable(false);
        reset.setFont(new Font("Comic Sans",Font.BOLD,12));
        reset.setForeground(Color.BLACK);

        signOut = new JButton();
        signOut.setBounds (320,500,100,50);
        signOut.addActionListener(this);
        signOut.setText("sign out");
        signOut.setFocusable(false);
        signOut.setFont(new Font("Comic Sans",Font.BOLD,12));
        signOut.setForeground(Color.BLACK);

        fetch = new JButton();
        fetch.setBounds (100,560,100,50);
        fetch.addActionListener(this);
        fetch.setText("Fetch");
        fetch.setFocusable(false);
        fetch.setFont(new Font("Comic Sans",Font.BOLD,12));
        fetch.setForeground(Color.BLACK);

        update = new JButton();
        update.setBounds (210,560,100,50);
        update.addActionListener(this);
        update.setText("Update");
        update.setFocusable(false);
        update.setFont(new Font("Comic Sans",Font.BOLD,12));
        update.setForeground(Color.BLACK);

        delete = new JButton();
        delete.setBounds (320,560,100,50);
        delete.addActionListener(this);
        delete.setText("Delete");
        delete.setFocusable(false);
        delete.setFont(new Font("Comic Sans",Font.BOLD,12));
        delete.setForeground(Color.BLACK);

        status = new JLabel();
        status.setText("Status");
        status.setBounds(0,620,500,50);
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

        model = new DefaultTableModel(new String[]{"Id","Name","Description","Department","Assignee","status"},0);

        table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(205);
        table.getColumnModel().getColumn(3).setMaxWidth(80);
        table.getColumnModel().getColumn(4).setMaxWidth(80);
        table.getColumnModel().getColumn(5).setMaxWidth(80);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(205);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.setBackground(new Color(0xdfcdcd));
        table.addMouseListener(this);

        scrollpane = new JScrollPane(table);
        scrollpane.setBounds(510,10,570,680);
        scrollpane.setOpaque(false);
        scrollpane.getViewport().setBackground(new Color(0xcddfcd));


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
        this.add(taskStatusLabel);
        this.add(taskStatus);
        this.add(status);
        this.add(scrollpane);
        this.add(create);
        this.add(fetch);
        this.add(signOut);
       if(role == 1 || role == 2){
           this.add(update);
           this.add(reset);
       }
       if(role == 1){
           this.add(delete);
       }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == create){
            if(validCreate()){
                create();
            }
        }
        else if(e.getSource() == signOut){
            this.dispose();
            new SignInPage();
        }
        else if(e.getSource() == fetch){
            fetch();
        }
        else if(e.getSource() == reset){
            //TODO
        }
        else if(e.getSource() == update){
            //TODO
        }
        else if(e.getSource() == delete){
            //TODO
        }
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
            if(!Objects.equals(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex())), "select")){
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
                assigneeList[0] = "select";
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
            else{
                String[] assigneeList = new String[1];
                assigneeList[0] = "select";
                taskAssignee.removeAllItems();

                for(int k=0; k<assigneeList.length; k++){
                    taskAssignee.addItem(assigneeList[k]);
                }

            }
        }
         else if(e.getSource() == taskAssignee){
            if(Objects.equals(String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex())), "select") || Objects.equals(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex())), "select"))
            {
                taskStatus.removeAllItems();
                String[] Tstatus = new String[1];
                Tstatus[0] = "Unassigned";
                for(int k=0; k<Tstatus.length; k++){
                taskStatus.addItem(Tstatus[k]);
            }
            }
            else if(!Objects.equals(String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex())), "select")){
                taskStatus.removeAllItems();
                String[] Tstatus = {"Assigned", "Pending", "Resolved"};
                for(int k=0; k<Tstatus.length; k++){
                    taskStatus.addItem(Tstatus[k]);
                }
            }
        }
    }

    //if emptyCheck it true then error if false go ahead.
    public boolean emptyCheck(){
        return taskName.getText().isEmpty() || taskDescription.getText().isEmpty() || Objects.equals(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex())), "select");
    }

    public boolean validCreate(){
        boolean validate = false;
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String assignee = String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex()));
        if(!Objects.equals(assignee, "select")){
            int assignedtasks = -1;
            try{
                String SQL = "SELECT tasksassigned from employees WHERE userid="+"\""+assignee+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    assignedtasks = rs.getInt("tasksassigned");
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
            if(assignedtasks >=0 || assignedtasks <3){
                validate = true;
            }
            else{
                validate = false;
            }
        }

        return !emptyCheck() && validate;
    }

    public void create(){
        String name = taskName.getText();
        String description = taskDescription.getText();
        String department = String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()));
        String assignee = String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex()));
        String status = String.valueOf(taskStatus.getItemAt(taskStatus.getSelectedIndex()));

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        int departmentId= -1;
        int assigneeId = -1;
        try{
            String SQL = "SELECT id from department WHERE name="+"\""+department+"\"";
            ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);

            while(rs.next()){
                departmentId = rs.getInt("id");
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        if(!Objects.equals(assignee, "select")){
            try{
                String SQL = "SELECT id from employees WHERE userid="+"\""+assignee+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    departmentId = rs.getInt("departmentusers");
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
        }

        try {
            String sql = "insert into task(name,description,department,assignee,status) values(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,description);
            ps.setInt(3,departmentId);
            if(assigneeId != -1){
                ps.setInt(4,assigneeId);
            }
            else{
                ps.setNull(4, Types.NULL);
            }
            ps.setString(5,status);
            ps.execute();
        }
        catch (Exception e) {
            System.out.println(e);
            this.status.setText("Unable to add due to SQL Error.");
        }
    }

    public void fetch(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {

            model.getDataVector().removeAllElements();
            String sqltable = null;
            if(role == 1){
                sqltable = "SELECT * FROM task";
            }
            else{
                sqltable = "SELECT * FROM task WHERE department = " + department ;
            }
            ps = con.prepareStatement(sqltable);
            ResultSet rs = ps.executeQuery(sqltable);

            while (rs.next()) {
                String a = rs.getString("id");
                String b = rs.getString("name");
                String c = rs.getString("description");
                int d = rs.getInt("department");
                String dept = null;
                String assign = null;
                try {
                    PreparedStatement psdept;
                    String sqldept = "SELECT name FROM department WHERE id =" + d;
                    psdept = con.prepareStatement(sqldept);
                    ResultSet rsdept = psdept.executeQuery(sqldept);
                    while (rsdept.next()) {
                        dept = rsdept.getString("name");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                int e = rs.getInt("assignee");
                try {
                    PreparedStatement psassign;
                    String sqlassign = "SELECT userid FROM employees WHERE id =" + e;
                    psassign = con.prepareStatement(sqlassign);
                    ResultSet rsassign = psassign.executeQuery(sqlassign);
                    while (rsassign.next()) {
                        assign = rsassign.getString("userid");
                    }
                } catch (Exception e1) {
                    System.out.println(e1);
                }

                String f = rs.getString("status");
                model.addRow(new Object[]{a, b, c, dept, assign, f});
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}
