package com.project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeManager extends JFrame implements ActionListener, MouseListener, ItemListener {
    int role,department;
    String userId;

    JLabel heading,status,firstNameLabel,lastNameLabel,userIdLabel,passwordLabel,emailLabel,roleLabel,departmentLabel,userwarn,passwarn;
    JButton create, signout,back,delete,reset,fetch;

    JTextField firstName, lastName, userIdText, passwordText, email;

    JComboBox roleSelection,departmentSelection;

    DefaultTableModel model;
    JTable table;
    JScrollPane scrollPane;

    public EmployeeManager(int role, int department, String userId){
        this.role = role;
        this.department = department;
        this.userId = userId;

        Border border = BorderFactory.createLineBorder(new Color(0x000000),5);
        Font font =  new Font("Monospaced",Font.BOLD,25);
        Color color = new Color(0xFFFFFF);

        heading = new JLabel();
        heading.setText("Employee Manager");
        heading.setBounds(0,0,500,50);
        heading.setHorizontalTextPosition(JLabel.LEFT);
        heading.setVerticalTextPosition(JLabel.CENTER);
        heading.setForeground(new Color(0x000000));
        heading.setFont(new Font("MV Boli",Font.BOLD,25));
        heading.setIconTextGap(10);
        heading.setBorder(border);
        heading.setVerticalAlignment(JLabel.TOP);
        heading.setHorizontalAlignment(JLabel.CENTER);

        firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name:");
        firstNameLabel.setBounds(10,100,200,50);
        firstNameLabel.setHorizontalTextPosition(JLabel.LEFT);
        firstNameLabel.setVerticalTextPosition(JLabel.CENTER);
        firstNameLabel.setForeground(new Color(0x000000));
        firstNameLabel.setFont(font);
        firstNameLabel.setIconTextGap(10);
        firstNameLabel.setBorder(border);
        firstNameLabel.setVerticalAlignment(JLabel.TOP);
        firstNameLabel.setHorizontalAlignment(JLabel.CENTER);

        firstName = new JTextField();
        firstName.setFont(font);
        firstName.setBounds(250,100,200,50);
        firstName.setBackground(color);
        firstName.setBorder(border);

        lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name:");
        lastNameLabel.setBounds(10,160,200,50);
        lastNameLabel.setHorizontalTextPosition(JLabel.LEFT);
        lastNameLabel.setVerticalTextPosition(JLabel.CENTER);
        lastNameLabel.setForeground(new Color(0x000000));
        lastNameLabel.setFont(font);
        lastNameLabel.setIconTextGap(10);
        lastNameLabel.setBorder(border);
        lastNameLabel.setVerticalAlignment(JLabel.TOP);
        lastNameLabel.setHorizontalAlignment(JLabel.CENTER);

        lastName = new JTextField();
        lastName.setFont(font);
        lastName.setBounds(250,160,200,50);
        lastName.setBackground(color);
        lastName.setBorder(border);

        userIdLabel = new JLabel();
        userIdLabel.setText("User ID:");
        userIdLabel.setBounds(10,220,200,50);
        userIdLabel.setHorizontalTextPosition(JLabel.LEFT);
        userIdLabel.setVerticalTextPosition(JLabel.CENTER);
        userIdLabel.setForeground(new Color(0x000000));
        userIdLabel.setFont(font);
        userIdLabel.setIconTextGap(10);
        userIdLabel.setBorder(border);
        userIdLabel.setVerticalAlignment(JLabel.TOP);
        userIdLabel.setHorizontalAlignment(JLabel.CENTER);

        userwarn = new JLabel();
        userwarn.setText("<html>UserID:   6-20 characters,  Must start with an alphabet.Can contain only alphabets, numbers and underscore.(No spaces)</html>");
        userwarn.setBounds(10,280,470,40);
        userwarn.setHorizontalTextPosition(JLabel.LEFT);
        userwarn.setVerticalTextPosition(JLabel.CENTER);
        userwarn.setOpaque(true);
        userwarn.setBackground(Color.BLACK);
        userwarn.setForeground(Color.WHITE);
        userwarn.setFont(new Font("Courier", Font.BOLD,12));
        userwarn.setBorder(BorderFactory.createLineBorder(new Color(0x000000),1));
        userwarn.setIconTextGap(10);
        userwarn.setVerticalAlignment(JLabel.TOP);
        userwarn.setHorizontalAlignment(JLabel.CENTER);

        userIdText = new JTextField();
        userIdText.setFont(font);
        userIdText.setBounds(250,220,200,50);
        userIdText.setBackground(color);
        userIdText.setBorder(border);

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setBounds(10,330,200,50);
        passwordLabel.setHorizontalTextPosition(JLabel.LEFT);
        passwordLabel.setVerticalTextPosition(JLabel.CENTER);
        passwordLabel.setForeground(new Color(0x000000));
        passwordLabel.setFont(font);
        passwordLabel.setIconTextGap(10);
        passwordLabel.setBorder(border);
        passwordLabel.setVerticalAlignment(JLabel.TOP);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);

        passwarn = new JLabel();
        passwarn.setText("<html>Password:    8-20 characters, Must Contain a number,special character,upper case & lower case letter.(No spaces)</html>");
        passwarn.setBounds(10,390,470,40);
        passwarn.setHorizontalTextPosition(JLabel.LEFT);
        passwarn.setVerticalTextPosition(JLabel.CENTER);
        passwarn.setOpaque(true);
        passwarn.setBackground(Color.BLACK);
        passwarn.setForeground(Color.WHITE);
        passwarn.setFont(new Font("Courier", Font.BOLD,12));
        passwarn.setBorder(BorderFactory.createLineBorder(new Color(0x000000),1));
        passwarn.setIconTextGap(10);
        passwarn.setVerticalAlignment(JLabel.TOP);
        passwarn.setHorizontalAlignment(JLabel.CENTER);

        passwordText = new JTextField();
        passwordText.setFont(font);
        passwordText.setBounds(250,330,200,50);
        //passwordText.setEchoChar('*');
        passwordText.setBackground(color);
        passwordText.setBorder(border);

        emailLabel = new JLabel();
        emailLabel.setText("Email:");
        emailLabel.setBounds(10,440,200,50);
        emailLabel.setHorizontalTextPosition(JLabel.LEFT);
        emailLabel.setVerticalTextPosition(JLabel.CENTER);
        emailLabel.setForeground(new Color(0x000000));
        emailLabel.setFont(font);
        emailLabel.setIconTextGap(10);
        emailLabel.setBorder(border);
        emailLabel.setVerticalAlignment(JLabel.TOP);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);

        email = new JTextField();
        email.setFont(new Font("MV Boli",Font.BOLD,20));
        email.setBounds(250,440,200,50);
        email.setBackground(color);
        email.setBorder(border);

        departmentLabel = new JLabel();
        departmentLabel.setText("Department:");
        departmentLabel.setBounds(10,500,200,50);
        departmentLabel.setHorizontalTextPosition(JLabel.CENTER);
        departmentLabel.setVerticalTextPosition(JLabel.CENTER);
        departmentLabel.setForeground(new Color(0x000000));
        departmentLabel.setFont(font);
        departmentLabel.setIconTextGap(10);
        departmentLabel.setBorder(border);
        departmentLabel.setVerticalAlignment(JLabel.TOP);
        departmentLabel.setHorizontalAlignment(JLabel.CENTER);

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
            String[] departmentlist = new String[i];
            departmentlist[0] = "select";
            try{
                String SQL = "SELECT name FROM department WHERE name <> "+"\""+"General Manager"+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);
                int j = 1;
                while(rs.next() && j<i){
                    departmentlist[j] = rs.getString("name");
                    j++;
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
            departmentSelection = new JComboBox(departmentlist);
        }
        else{
            String[] departmentlist = new String[1];
            departmentlist[0] = "select";
            departmentSelection = new JComboBox(departmentlist);
        }
        departmentSelection.setBounds(250,500,200,50);
        departmentSelection.addItemListener(this);

        roleLabel = new JLabel();
        roleLabel.setText("Role:");
        roleLabel.setBounds(10,560,200,50);
        roleLabel.setHorizontalTextPosition(JLabel.LEFT);
        roleLabel.setVerticalTextPosition(JLabel.CENTER);
        roleLabel.setForeground(new Color(0x000000));
        roleLabel.setFont(font);
        roleLabel.setIconTextGap(10);
        roleLabel.setBorder(border);
        roleLabel.setVerticalAlignment(JLabel.TOP);
        roleLabel.setHorizontalAlignment(JLabel.CENTER);

        roleSelection = new JComboBox();
        roleSelection.addItemListener(this);
        roleSelection.setBounds(250,560,200,50);

        create = new JButton();
        create.setBounds (100,620,100,50);
        create.addActionListener(this);
        create.setText("Create");
        create.setFocusable(false);
        create.setFont(new Font("Comic Sans",Font.BOLD,12));
        create.setForeground(Color.BLACK);

        reset = new JButton();
        reset.setBounds (210,620,100,50);
        reset.addActionListener(this);
        reset.setText("Reset");
        reset.setFocusable(false);
        reset.setFont(new Font("Comic Sans",Font.BOLD,12));
        reset.setForeground(Color.BLACK);

        signout = new JButton();
        signout.setBounds (320,620,100,50);
        signout.addActionListener(this);
        signout.setText("Log out");
        signout.setFocusable(false);
        signout.setFont(new Font("Comic Sans",Font.BOLD,12));
        signout.setForeground(Color.BLACK);

        fetch = new JButton();
        fetch.setBounds (100,680,100,50);
        fetch.addActionListener(this);
        fetch.setText("Fetch");
        fetch.setFocusable(false);
        fetch.setFont(new Font("Comic Sans",Font.BOLD,12));
        fetch.setForeground(Color.BLACK);

        delete = new JButton();
        delete.setBounds (210,680,100,50);
        delete.addActionListener(this);
        delete.setText("Delete");
        delete.setFocusable(false);
        delete.setFont(new Font("Comic Sans",Font.BOLD,12));
        delete.setForeground(Color.BLACK);

        back = new JButton();
        back.setBounds (320,680,100,50);
        back.addActionListener(this);
        back.setText("Back");
        back.setFocusable(false);
        back.setFont(new Font("Comic Sans",Font.BOLD,12));
        back.setForeground(Color.BLACK);

        status = new JLabel();
        status.setText("Status");
        status.setBounds(0,740,500,50);
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

        model = new DefaultTableModel(new String[]{"Id","First Name","Last Name","User ID","Password","Email","Role","Department","Tasks Assigned"},0);

        table = new JTable(model);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(3).setMaxWidth(80);
        table.getColumnModel().getColumn(4).setMaxWidth(80);
        table.getColumnModel().getColumn(5).setMaxWidth(205);
        table.getColumnModel().getColumn(6).setMaxWidth(120);
        table.getColumnModel().getColumn(7).setMaxWidth(120);
        table.getColumnModel().getColumn(8).setMaxWidth(120);
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(205);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        table.getColumnModel().getColumn(7).setPreferredWidth(120);
        table.getColumnModel().getColumn(8).setPreferredWidth(120);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.setBackground(new Color(0xdfcdcd));
        table.addMouseListener(this);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(510,10,930,680);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0xcddfcd));

        this.setDefaultCloseOperation(TaskManager.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1450,850);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Task Manager");
        this.getContentPane().setBackground(new Color(0xADD8E6));
        this.add(heading);
        this.add(firstNameLabel);
        this.add(firstName);
        this.add(lastNameLabel);
        this.add(lastName);
        this.add(userIdLabel);
        this.add(userwarn);
        this.add(userIdText);
        this.add(passwordLabel);
        this.add(passwordText);
        this.add(passwarn);
        this.add(emailLabel);
        this.add(email);
        this.add(roleLabel);
        this.add(roleSelection);
        this.add(departmentLabel);
        this.add(departmentSelection);
        this.add(create);
        this.add(signout);
        this.add(back);
        this.add(delete);
        this.add(reset);
        this.add(fetch);
        this.add(status);
        this.add(scrollPane);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == create){
            if(validCreate()){
                create();
            }
            else{
                errorMessage();
            }
        }
        else if(e.getSource() == signout){
            this.dispose();
            new SignInPage();
        }
        else if(e.getSource() == back){
            this.dispose();
            new Dashboard(role,department,userId);
        }
        else if(e.getSource() == delete){
            if(validDelete()){
                delete();
            }
            else{
                errorMessage();
            }
        }
        else if(e.getSource() == reset){
            reset();
        }
        else if(e.getSource() == fetch){
            fetch();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int index = table.getSelectedRow();
        firstName.setText(model.getValueAt(index,1).toString());
        lastName.setText(model.getValueAt(index,2).toString());
        userIdText.setText(model.getValueAt(index,3).toString());
        passwordText.setText(model.getValueAt(index,4).toString());
        email.setText(model.getValueAt(index,5).toString());
        departmentSelection.setSelectedItem(model.getValueAt(index,6));
        roleSelection.setSelectedItem(model.getValueAt(index,6));
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
        if(e.getSource() == departmentSelection){
            if(!Objects.equals(String.valueOf(departmentSelection.getItemAt(departmentSelection.getSelectedIndex())), "select")){
                String employeeDepartment = String.valueOf(departmentSelection.getItemAt(departmentSelection.getSelectedIndex()));
                Connection con;
                con = connection.connectDB();
                PreparedStatement ps;
                int deptid = 0;
                int k =0;
                String[] roles;
                boolean deptManager = false;
                try{
                    String SQL = "SELECT id FROM department WHERE name="+"\""+employeeDepartment+"\"";
                    ps = con.prepareStatement(SQL);
                    ResultSet rs = ps.executeQuery(SQL);
                    while(rs.next()){
                        deptid = rs.getInt("id");
                    }
                }catch(Exception e1){
                    System.out.println(e1);
                }
                try{
                    String SQL = "SELECT * FROM employees WHERE department = " + deptid + " AND role = 2";
                    ps = con.prepareStatement(SQL);
                    ResultSet rs = ps.executeQuery(SQL);
                    if(rs.next()){
                        deptManager = true;
                    }
                }
                catch (Exception e1){
                    System.out.println(e1);
                }
                if(deptManager){
                    k=2;
                    roles = new String[k];
                    roles[0] = "select";
                    roles[1] = "Employee";
                }
                else{
                    k=3;
                    roles = new String[k];
                    roles[0] = "select";
                    roles[1] = "Department Manager";
                    roles[2] = "Employee";
                }
                roleSelection.removeAllItems();

                for(k = 0; k< roles.length;k++){
                    roleSelection.addItem(roles[k]);
                }
            }
        }
    }

    public boolean emailValidation(){
        String Email = email.getText();
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }

    public boolean passwordValidation(){
        String pass = passwordText.getText();
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public boolean userIDValidation(){
        String user = userIdText.getText();
        String regex = "^[A-Za-z]\\w{5,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user);
        return matcher.matches();
    }

    public boolean uniqueUserID(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String user = userIdText.getText();
        boolean unique = true;

        try {

            String sql = "select * from employees where BINARY UserID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,user);
            ResultSet s = ps.executeQuery();

            if(s.next()){
                unique = false;
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

    public boolean uniqueEmail(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String Email = email.getText();
        boolean unique = true;

        try {

            String sql = "select * from employees where BINARY Email = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,Email);
            ResultSet s = ps.executeQuery();

            if(s.next()){
                unique = false;
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

    public boolean emptyCheck(){
        return firstName.getText().isEmpty() || lastName.getText().isEmpty() || Objects.equals(String.valueOf(departmentSelection.getItemAt(departmentSelection.getSelectedIndex())), "select") || Objects.equals(String.valueOf(roleSelection.getItemAt(roleSelection.getSelectedIndex())), "select");
    }

    public boolean validCreate(){
        return emailValidation() && passwordValidation() && userIDValidation() && uniqueUserID() && uniqueEmail() && !emptyCheck();
    }

    public boolean validDelete(){
        boolean valid = false;

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        String user = userIdText.getText();

        try{
            String sql = "SELECT * FROM employees WHERE tasksassigned = 0 AND userid = "+"\""+user+"\"";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                valid = true;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return valid;
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

    public int getRoleId(String a){
        int roleID = -1;

        if(!Objects.equals(a, "select")){
            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;
            try{
                String SQL = "SELECT id from roles WHERE name="+"\""+a+"\"";
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery(SQL);

                while(rs.next()){
                    roleID = rs.getInt("id");
                }

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        return roleID;
    }

    public void create(){
        String first = firstName.getText();
        String last = lastName.getText();
        String id = userIdText.getText();
        String pass = passwordText.getText();
        String em = email.getText();
        String role = String.valueOf(roleSelection.getItemAt(roleSelection.getSelectedIndex()));
        String department = String.valueOf(departmentSelection.getItemAt(departmentSelection.getSelectedIndex()));

        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            String sql = "insert into employees(firstname,lastname,userid,password,email,role,department) values(?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,first);
            ps.setString(2,last);
            ps.setString(3,id);
            ps.setString(4,pass);
            ps.setString(5,em);
            ps.setInt(6,getRoleId(role));
            ps.setInt(7,getDepartmentId(department));
            ps.execute();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void delete(){
        try{
            int index = table.getSelectedRow();
            int i = Integer.parseInt(model.getValueAt(index,0).toString());

            Connection con;
            con = connection.connectDB();
            PreparedStatement ps;

            try {
                String sql = "DELETE FROM employees WHERE id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1,i);
                ps.execute();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void fetch(){
        Connection con;
        con = connection.connectDB();
        PreparedStatement ps;

        try {
            model.getDataVector().removeAllElements();

            String sqltable = "SELECT * FROM employees ORDER BY role ASC, department ASC";
            ps = con.prepareStatement(sqltable);
            ResultSet rs = ps.executeQuery(sqltable);

            while (rs.next()) {
                String a = rs.getString("id");
                String b = rs.getString("firstname");
                String c = rs.getString("lastname");
                String d = rs.getString("userid");
                String e = rs.getString("password");
                String f = rs.getString("email");
                int g = rs.getInt("role");
                int h = rs.getInt("department");
                int i = rs.getInt("tasksassigned");

                String role = "";
                String department = "";
                try{
                    PreparedStatement psd;
                    String sql = "SELECT name from roles WHERE id = "+ g ;
                    psd = con.prepareStatement(sql);
                    ResultSet rsd = psd.executeQuery(sql);

                    while(rsd.next()){
                        role = rsd.getString("name");
                    }
                }
                catch (Exception e1){
                    System.out.println(e1);
                }
                try{
                    PreparedStatement psd;
                    String sql = "SELECT name from department WHERE id = "+ h ;
                    psd = con.prepareStatement(sql);
                    ResultSet rsd = psd.executeQuery(sql);

                    while(rsd.next()){
                        department = rsd.getString("name");
                    }
                }
                catch (Exception e1){
                    System.out.println(e1);
                }


                model.addRow(new Object[]{a, b, c, d, e, f,role,department,i});
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public void reset(){
        firstName.setText("");
        lastName.setText("");
        userIdText.setText("");
        passwordText.setText("");
        email.setText("");
    }

    public void errorMessage(){
        if(!userIDValidation()){
            status.setText("Invalid UserID");
        }
        else if(!passwordValidation()){
            status.setText("Invalid Password");
        }
        else if(!emailValidation()){
            status.setText("Invalid Email");
        }
        else if(!uniqueUserID()){
            status.setText("User Already Exist");
        }
        else if(!uniqueEmail()){
            status.setText("Email Already Exist");
        }
        else if(emptyCheck()){
            if(firstName.getText().isEmpty()){
                status.setText("First Name can't be empty");
            }
            else if(lastName.getText().isEmpty()){
                status.setText("Last Name can't be empty");
            }
            else if(Objects.equals(String.valueOf(departmentSelection.getItemAt(departmentSelection.getSelectedIndex())), "select")){
                status.setText("Please select Department.");
            }
            else if(Objects.equals(String.valueOf(roleSelection.getItemAt(roleSelection.getSelectedIndex())), "select")){
                status.setText("Please select Role.");
            }
        }
        else if(!validDelete()){
            status.setText("Cannot Delete User.");
        }
    }
}
