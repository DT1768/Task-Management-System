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
    JButton back;

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

    JLabel taskNameEmployee;
    JLabel taskDescriptionEmployee;
    JLabel taskDepartmentEmployee;

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

        taskNameEmployee = new JLabel();
        taskNameEmployee.setText("Select a Task");
        taskNameEmployee.setBounds(250,100,200,50);
        taskNameEmployee.setHorizontalAlignment(JLabel.CENTER);
        taskNameEmployee.setFont(font);
        taskNameEmployee.setBackground(color);
        taskNameEmployee.setBorder(border);


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

        taskDescriptionEmployee = new JLabel();
        taskDescriptionEmployee.setText("Select a Task");
        taskDescriptionEmployee.setBounds(250,160,200,150);
        taskDescriptionEmployee.setVerticalAlignment(JLabel.TOP);
        taskDescriptionEmployee.setHorizontalAlignment(JLabel.LEFT);
        taskDescriptionEmployee.setBackground(color);
        taskDescriptionEmployee.setBorder(border);

        taskDepartmentLabel = new JLabel();
        taskDepartmentLabel.setText("Department:");
        taskDepartmentLabel.setBounds(10,320,200,50);
        taskDepartmentLabel.setHorizontalTextPosition(JLabel.CENTER);
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

        taskDepartmentEmployee = new JLabel();
        taskDepartmentEmployee.setText("Select a Task");
        taskDepartmentEmployee.setBounds(250,320,200,50);
        taskDepartmentEmployee.setHorizontalAlignment(JLabel.CENTER);
        taskDepartmentEmployee.setFont(font);
        taskDepartmentEmployee.setBackground(color);
        taskDepartmentEmployee.setBorder(border);


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
        create.setText("Create");
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
        signOut.setText("Log out");
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
        if(role ==1 || role == 2){
            update.setText("Update");
        }
        else if(role == 3){
            update.setText("Assign");
        }
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

        back = new JButton();
        back.setBounds (210,620,100,50);
        back.addActionListener(this);
        back.setText("Back");
        back.setFocusable(false);
        back.setFont(new Font("Comic Sans",Font.BOLD,12));
        back.setForeground(Color.BLACK);

        status = new JLabel();
        status.setText("Status");
        status.setBounds(0,680,500,50);
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
        this.setSize(1100,800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Task Manager");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(taskNameLabel);
        this.add(taskDescriptionLabel);
        this.add(taskDepartmentLabel);
        this.add(taskAssigneeLabel);
        this.add(taskAssignee);
        this.add(taskStatusLabel);
        this.add(taskStatus);
        this.add(status);
        this.add(scrollpane);
        this.add(fetch);
        this.add(signOut);
        this.add(back);
       if(role == 1 || role == 2){
           this.add(taskName);
           this.add(taskDescription);
           this.add(taskDepartment);
           this.add(create);
           this.add(reset);
       }
       if(role == 1){
           this.add(delete);
           this.add(update);
       }
       if(role == 3){
           this.add(taskNameEmployee);
           this.add(taskDescriptionEmployee);
           this.add(taskDepartmentEmployee);
           this.add(update);
       }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == create){
            if(validCreate()){
                create();
                fetch();
                reset();
                successMessage("Task Created Successfully.");
            }
        }
        else if(e.getSource() == signOut){
            this.dispose();
            new SignInPage();
        }
        else if(e.getSource() == fetch){
            fetch();
            successMessage("Tasks Fetched.");
        }
        else if(e.getSource() == reset){
            reset();
            successMessage("reset");
        }
        else if(e.getSource() == update){
            if(validUpdate()){
                update();
                fetch();
                reset();
            }
        }
        else if(e.getSource() == delete){
            delete();
            fetch();
        }
        else if(e.getSource() == back){
            this.dispose();
            new Dashboard(role,department,userId);
        }
    }

    String taskStatusBeforeUpdate = "";
    String assigneeBeforeUpdate = "";
    int assigneeBeforeUpdateId;

    @Override
    public void mouseClicked(MouseEvent e) {
        int index = table.getSelectedRow();
        taskName.setText(model.getValueAt(index,1).toString());
        taskNameEmployee.setText(model.getValueAt(index,1).toString());
        taskDescription.setText(model.getValueAt(index,2).toString());
        taskDescriptionEmployee.setText(model.getValueAt(index,2).toString());
        taskDepartment.setSelectedItem(model.getValueAt(index,3).toString());
        taskDepartmentEmployee.setText(model.getValueAt(index,3).toString());
        if(model.getValueAt(index,4) != null) {
            taskAssignee.setSelectedItem(model.getValueAt(index, 4).toString());
            assigneeBeforeUpdate = model.getValueAt(index,4).toString();
        }
        else{
            taskAssignee.setSelectedItem("select");
        }
        taskStatus.setSelectedItem(model.getValueAt(index,5).toString());
        taskStatusBeforeUpdate = model.getValueAt(index,5).toString();
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
                if (role != 3){
                    String[] assigneeList = new String[i+1];
                    assigneeList[0] = "select";
                    try {
                        String SQL = "SELECT DISTINCT(userid) FROM employees WHERE department = " + id + "";
                        ps = con.prepareStatement(SQL);
                        ResultSet rs = ps.executeQuery(SQL);
                        int j = 1;
                        while (rs.next() && j < i + 1) {
                            assigneeList[j] = rs.getString("userid");
                            j++;
                        }
                    } catch (Exception e1) {
                        System.out.println(e1);
                    }
                    taskAssignee.removeAllItems();

                    for (int k = 0; k < assigneeList.length; k++) {
                        taskAssignee.addItem(assigneeList[k]);
                    }
                }
                else{
                    String[] assigneeList = new String[1];
                    assigneeList[0] = userId;
                    taskAssignee.removeAllItems();
                    taskAssignee.addItem(assigneeList[0]);
                }
            }
            else{
                String[] assigneeList = new String[1];
                assigneeList[0] = "select";
                taskAssignee.removeAllItems();
                taskAssignee.addItem(assigneeList[0]);

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
                if(role == 1 || role == 2){
                    String[] Tstatus = {"Assigned", "Pending", "Resolved"};
                    for(int k=0; k<Tstatus.length; k++){
                        taskStatus.addItem(Tstatus[k]);
                    }
                }
                else if(role == 3){
                    String[] Tstatus = {"Assigned"};
                    taskStatus.addItem(Tstatus[0]);
                }
            }
        }
    }

    public boolean emptyCheck(){
        if(taskName.getText().isEmpty()){
            errorMessage("Task Name cannot be empty.");
        }
        else if(taskDescription.getText().isEmpty()){
            errorMessage("Task Description cannot be empty.");
        }
        else if(Objects.equals(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex())), "select")){
            errorMessage("Please Select Department.");
        }
        return taskName.getText().isEmpty() || taskDescription.getText().isEmpty() || Objects.equals(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex())), "select");
    }

    public int getDepartmentId(String s){

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;
        int departmentId= -1;
        try{
            String SQL = "SELECT id from department WHERE name="+"\""+s+"\"";
            ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery(SQL);

            while(rs.next()){
                departmentId = rs.getInt("id");
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return departmentId;
    }

    public int getAssigneeId(String a){
        int assigneeId = -1;

        if(!Objects.equals(a, "select")){
            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;
            try{
                String SQL = "SELECT id from employees WHERE userid="+"\""+a+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    assigneeId = rs.getInt("id");
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return  assigneeId;
    }

    public void addTask(int a){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            String sql = "SELECT tasksassigned FROM employees WHERE id = " + a;
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            int taskassigned = -1;
            while (rs.next()) {
                taskassigned = rs.getInt("tasksassigned");
            }
            taskassigned = taskassigned + 1;
            try {
                sql = "UPDATE employees SET tasksassigned =" + taskassigned + " WHERE id = " + a;
                ps = con.prepareStatement(sql);
                ps.execute();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void subtractTask(int a){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            String sql = "SELECT tasksassigned FROM employees WHERE id = " + a;
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            int taskassigned = -1;
            while (rs.next()) {
                taskassigned = rs.getInt("tasksassigned");
            }
            taskassigned = taskassigned - 1;
            try {
                sql = "UPDATE employees SET tasksassigned =" + taskassigned + " WHERE id = " + a;
                ps = con.prepareStatement(sql);
                ps.execute();
            } catch (Exception e1) {
                System.out.println(e1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean uniqueTaskName(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String tname = taskName.getText();
        String dept = String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()));
        boolean unique = true;
        int deptid = getDepartmentId(dept);
        try {
            String sql = "select * from task where BINARY name = ? AND department = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1,tname);
            ps.setInt(2,deptid);
            ResultSet s = ps.executeQuery();

            if(s.next()){
                unique = false;
                errorMessage("Task Name Already Exist.");
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

    public boolean validUpdate(){
        boolean validate;
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
                    System.out.println(assignedtasks);
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
            if(Objects.equals(assigneeBeforeUpdate, assignee)){
                if (assignedtasks >= 0 && assignedtasks <= 3) {
                    validate = true;
                } else {
                    validate = false;
                }
            }
            else{
                if (assignedtasks >= 0 && assignedtasks < 3) {
                    validate = true;
                } else {
                    validate = false;
                }
            }
        }
        else{
            validate = true;
        }
        return !emptyCheck() && validate;
    }

    public boolean validCreate(){
        boolean validate;
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
                    System.out.println(assignedtasks);
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
            if(assignedtasks >=0 && assignedtasks <3){
                validate = true;
            }
            else{
                validate = false;
            }
        }
        else{
            validate = true;
        }
        if(validate == false){
            errorMessage("Tasks Limit Reached.");
        }

        return uniqueTaskName() && !emptyCheck() && validate;
    }

    public void notifications(String action){


        int actor = getAssigneeId(userId);

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try{
            String SQL = "insert into notifications(department,actor,action) values(?,?,?)";
            ps = con.prepareStatement(SQL);
            ps.setInt(1,getDepartmentId(String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()))));
            ps.setInt(2, actor);
            ps.setString(3,action);
            ps.execute();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void create(){
        String name = taskName.getText();
        String description = taskDescription.getText();
        String department1 = String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()));
        String assignee = String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex()));
        String status = String.valueOf(taskStatus.getItemAt(taskStatus.getSelectedIndex()));

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        int departmentId = getDepartmentId(department1);
        int assigneeId = getAssigneeId(assignee);


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
            notifications("New task created: "+ name);
            if(!Objects.equals(status, "Resolved") || !Objects.equals(status, "Unassigned")){
                addTask(assigneeId);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            errorMessage("SQL Error.");
        }
    }

    public void fetch(){
        model.getDataVector().removeAllElements();
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            String sqltable = null;
            if(role == 1){
                sqltable = "SELECT * FROM task";

            }
            else if(role == 2){
                sqltable = "SELECT * FROM task WHERE department = " + department ;
            }
            else if(role == 3){
                sqltable = "SELECT * FROM task WHERE (department = "+ department+" AND status = 'Unassigned') OR (department = "+ department +" AND assignee = "+ getAssigneeId(userId)+")";
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
                    errorMessage("SQL Error.");
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
                    errorMessage("SQL Error.");
                }

                String f = rs.getString("status");
                model.addRow(new Object[]{a, b, c, dept, assign, f});
            }
        }
        catch(Exception e) {
            System.out.println(e);
            errorMessage("SQL Error.");
        }
    }

    public void update(){
        if(role == 1){
            int i = -1;
            try{
                int index = table.getSelectedRow();
                i = Integer.parseInt(model.getValueAt(index, 0).toString());
            }
            catch (Exception e){
                System.out.println(e);
            }
            String name = taskName.getText();
            String description = taskDescription.getText();
            String department = String.valueOf(taskDepartment.getItemAt(taskDepartment.getSelectedIndex()));
            String assignee = String.valueOf(taskAssignee.getItemAt(taskAssignee.getSelectedIndex()));
            String status = String.valueOf(taskStatus.getItemAt(taskStatus.getSelectedIndex()));

            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;

            int departmentId= getDepartmentId(department);
            int assigneeId = getAssigneeId(assignee);

            assigneeBeforeUpdateId = getAssigneeId(assigneeBeforeUpdate);
            try{
                String SQL = "UPDATE task SET name = ?,description = ?, department = ?,assignee = ?, status = ? WHERE id = ?";
                ps = con.prepareStatement(SQL);
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setInt(3, departmentId);
                if(Objects.equals(assignee, "select")){
                    ps.setNull(4,Types.NULL);
                }
                else{
                    ps.setInt(4, assigneeId);
                }
                ps.setString(5, status);
                ps.setInt(6, i);
                ps.execute();
                notifications("Task updated: "+ name);
                if((Objects.equals(taskStatusBeforeUpdate, "Unassigned") || Objects.equals(taskStatusBeforeUpdate, "Resolved")) && (Objects.equals(status, "Assigned") || Objects.equals(status, "Pending"))){
                    System.out.println("add");
                    addTask(assigneeId);
                }
                else if((Objects.equals(taskStatusBeforeUpdate, "Assigned") || Objects.equals(taskStatusBeforeUpdate, "Pending")) && (Objects.equals(status, "Unassigned") || Objects.equals(status, "Resolved"))){
                    System.out.println("Subtract");
                    subtractTask(assigneeBeforeUpdateId);
                }
                else if((Objects.equals(taskStatusBeforeUpdate, "Assigned") || Objects.equals(taskStatusBeforeUpdate, "Pending")) && (Objects.equals(status, "Assigned") || Objects.equals(status, "Pending"))){
                    subtractTask(assigneeBeforeUpdateId);
                    addTask(assigneeId);
                }
            }
            catch(Exception e){
                System.out.println(e);
                errorMessage("SQL Error.");
            }
            successMessage("task updated successfully.");
        }
        else if(role == 3){
            int i = -1;
            try{
                int index = table.getSelectedRow();
                i = Integer.parseInt(model.getValueAt(index, 0).toString());
            }
            catch (Exception e){
                System.out.println(e);
            }
            String name = taskNameEmployee.getText();
            String status = String.valueOf(taskStatus.getItemAt(taskStatus.getSelectedIndex()));

            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;


            int userID = getAssigneeId(userId);

            if(Objects.equals(taskStatusBeforeUpdate, "Unassigned")){
                try {
                    String SQL = "UPDATE task SET assignee = ?, status = ? WHERE id = ?";
                    ps = con.prepareStatement(SQL);
                    ps.setInt(1, userID);
                    ps.setString(2, status);
                    ps.setInt(3, i);
                    ps.execute();
                    addTask(userID);
                    notifications("Task assigned: "+ name);
                    successMessage("task updated successfully.");
                } catch (Exception e) {
                    System.out.println(e);
                    errorMessage("SQL Error.");
                }
            }
            else if(Objects.equals(taskStatusBeforeUpdate, "Pending")){
                try {
                    String SQL = "UPDATE task SET assignee = ?, status = ? WHERE id = ?";
                    ps = con.prepareStatement(SQL);
                    ps.setInt(1, userID);
                    ps.setString(2, status);
                    ps.setInt(3, i);
                    ps.execute();
                    notifications("Task assigned: "+ name);
                    successMessage("task updated successfully.");
                } catch (Exception e) {
                    System.out.println(e);
                    errorMessage("SQL Error.");
                }
            }
            else{
                errorMessage("can't update task.");
            }
        }
    }

    public void reset(){
        taskName.setText("");
        taskDescription.setText("");
    }

    public void delete(){
        if(role == 1) {
            int i = -1;
            String name = "";
            try {
                int index = table.getSelectedRow();
                i = Integer.parseInt(model.getValueAt(index, 0).toString());
                name = model.getValueAt(index,1).toString();
            } catch (Exception e) {
                System.out.println(e);
            }

            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;

            assigneeBeforeUpdateId = getAssigneeId(assigneeBeforeUpdate);
            try{
                String SQL = "DELETE FROM task WHERE id = ?";
                ps = con.prepareStatement(SQL);
                ps.setInt(1,i);
                ps.execute();
                notifications("Task deleted: "+ name);
                if(Objects.equals(taskStatusBeforeUpdate, "Assigned") || Objects.equals(taskStatusBeforeUpdate, "Pending")){
                    subtractTask(assigneeBeforeUpdateId);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void errorMessage(String e){
        status.setText(e);
    }

    public void successMessage(String e){
        status.setText(e);
    }
}