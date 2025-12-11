package utils;

import pages.PatientManagementPage;
import template.Employee;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import Test.Test;
import template.Patient;

import static pages.PatientManagementPage.renderData;

public class Dbinter {
    // 存储所有员工的信息
    public static ArrayList<Employee> list = new ArrayList<>();

    // 查询结果保存数组
    public static ArrayList<Employee> queryList = new ArrayList<>();

    // 将中文字段名转化为英语字段（数据库存储的字段）
    static HashMap<String,String> transform = new HashMap<>();
    static {
        transform.put("按姓名","emp_name");
        transform.put("按工号","emp_no");
        transform.put("按身份证号","emp_id");
        transform.put("按电话","emp_teleno");
    }

    // 准备数据
    static String url = "jdbc:mysql://127.0.0.1:3306/databasedesign" +
            "?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    static String username = "root";
    static String password = "123456";
    static Connection conn;

    // 类加载就加载，连接好数据库
    static {
        // 1.注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 2.获取连接
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 登录查询函数
    public boolean loginCheck(String username,String password) throws SQLException {
        String sql = "Select * from user_tab where username = ? and password = ?";
        // 创建语句
        PreparedStatement ps = conn.prepareStatement(sql);
        // 设置问号
        ps.setString(1,username);
        ps.setString(2,password);
        // 执行语句
        ResultSet rs = ps.executeQuery();

        return rs.next();// 有:true
                         // 无:false
    }

    // 员工数据渲染函数(返回一个二维数组)
    public ArrayList<Employee> getEmployeeInfo() throws SQLException {
        String sql = "Select * from personnel;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
                String emp_no = rs.getString(1);
                String emp_name = rs.getString(2);
                String emp_dept_id = rs.getString(3);
                String emp_duty = rs.getString(4);
                String emp_xl = rs.getString(5);
                String emp_gender = rs.getString(6);
                Date emp_birthday = rs.getDate(7);
                String emp_hometown = rs.getString(8);
                String emp_country = rs.getString(9);
                String emp_nation = rs.getString(10);
                String emp_id = rs.getString(11);
                String emp_marriage = rs.getString(12);
                String emp_health = rs.getString(13);
                Date emp_startwork = rs.getDate(14);
                String emp_state = rs.getString(15);
                String emp_homeaddress = rs.getString(16);
                String emp_teleno = rs.getString(17);
                String emp_email = rs.getString(18);
                String emp_job_id = rs.getString(19);
                Employee e = new Employee(emp_no, emp_name, emp_dept_id, emp_duty, emp_xl, emp_gender,
                    emp_hometown, emp_birthday, emp_country, emp_nation, emp_id,
                    emp_marriage, emp_health, emp_startwork, emp_state, emp_homeaddress,
                    emp_teleno, emp_email, emp_job_id);
                list.add(e);
        }
        return list;
    }

    // 增加员工信息
    // 增加员工信息
    public void addEmployee(String employeeId, String name, String department, String position,
                            String education, String gender, String birthday, String origin,
                            String nationality, String ethnicity, String idCard, String maritalStatus,
                            String healthStatus, String hireDate, String workStatus, String address, String phone,
                            String email, String jobCode) throws SQLException {

        // 处理生日 - 检查是否为空
        java.sql.Date sqlBirthDate = null;
        if (birthday != null && !birthday.trim().isEmpty()) {
            try {
                LocalDate birthdate = LocalDate.parse(birthday.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                sqlBirthDate = java.sql.Date.valueOf(birthdate);  // 直接转换为sql.Date
            } catch (DateTimeParseException e) {
                System.out.println("生日格式错误: " + birthday);
                throw new IllegalArgumentException("生日格式错误，应该为yyyy-MM-dd格式");
            }
        }

        // 处理入职日期 - 检查是否为空
        java.sql.Date sqlHireDate = null;
        if (hireDate != null && !hireDate.trim().isEmpty()) {
            try {
                LocalDate hiredate = LocalDate.parse(hireDate.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                sqlHireDate = java.sql.Date.valueOf(hiredate);  // 直接转换为sql.Date
            } catch (DateTimeParseException e) {
                System.out.println("入职日期格式错误: " + hireDate);
                throw new IllegalArgumentException("入职日期格式错误，应该为yyyy-MM-dd格式");
            }
        }

        // 创建Employee对象（如果Employee构造函数接受java.sql.Date）
        // 注意：需要修改Employee类的构造方法参数类型
        Employee e = new Employee(employeeId, name, department, position,
                education, gender, origin, sqlBirthDate, nationality,
                ethnicity, idCard, maritalStatus, healthStatus, sqlHireDate,
                workStatus, address, phone, email, jobCode);

        // 使用PreparedStatement
        String sql = "Insert into personnel " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeId);
            pstmt.setString(2, name);
            pstmt.setString(3, department);
            pstmt.setString(4, position);
            pstmt.setString(5, education);
            pstmt.setString(6, gender);
            pstmt.setDate(7, sqlBirthDate);  // 直接使用java.sql.Date
            pstmt.setString(8, origin);
            pstmt.setString(9, nationality);
            pstmt.setString(10, ethnicity);
            pstmt.setString(11, idCard);
            pstmt.setString(12, maritalStatus);
            pstmt.setString(13, healthStatus);
            pstmt.setDate(14, sqlHireDate);  // 直接使用java.sql.Date
            pstmt.setString(15, workStatus);
            pstmt.setString(16, address);
            pstmt.setString(17, phone);
            pstmt.setString(18, email);
            pstmt.setString(19, jobCode);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("插入了 " + rowsAffected + " 行数据");
        }
        // 点击刷新即可刷新数据
    }


    //删除员工数据
    public void deleteEmployee(String emp_no) throws SQLException {
        // 定义+ 执行sql
        String sql = "Delete from personnel where emp_no = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,emp_no);
        ps.executeUpdate();
    }

    // 查询员工信息功能
    public void queryEmployeeInfo(String type,String deptName, String key) throws SQLException {
        String sql;
        // 如果是不限制部门（所有部门）
        ResultSet rs;
        if(deptName.equals("全部部门")) {
            sql = "Select * from personnel where " + transform.get(type) + " = " + key;
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(sql);
        } else {
            String dept = deptName;
            sql = "Select * from personnel where " + transform.get(type) + " = " + key + " and emp_dept = " + "'" + dept + "'";
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(sql);
        }
        // 调用Test中的queryRender函数实现数据的渲染
        // 把数据先转化为一个ArrayList<Employee>，传入queryRender函数
        // queryRender函数根据这个数组里面的内容渲染
        int i = 0; // 来记录有多少条数据
        queryList.clear();
        while(rs.next()) {
            i++;
            String emp_no = rs.getString(1);
            String emp_name = rs.getString(2);
            String emp_dept_id = rs.getString(3);
            String emp_duty = rs.getString(4);
            String emp_xl = rs.getString(5);
            String emp_gender = rs.getString(6);
            Date emp_birthday = rs.getDate(7);
            String emp_hometown = rs.getString(8);
            String emp_country = rs.getString(9);
            String emp_nation = rs.getString(10);
            String emp_id = rs.getString(11);
            String emp_marriage = rs.getString(12);
            String emp_health = rs.getString(13);
            Date emp_startwork = rs.getDate(14);
            String emp_state = rs.getString(15);
            String emp_homeaddress = rs.getString(16);
            String emp_teleno = rs.getString(17);
            String emp_email = rs.getString(18);
            String emp_job_id = rs.getString(19);
            Employee e = new Employee(emp_no, emp_name, emp_dept_id, emp_duty, emp_xl, emp_gender,
                    emp_hometown, emp_birthday, emp_country, emp_nation, emp_id,
                    emp_marriage, emp_health, emp_startwork, emp_state, emp_homeaddress,
                    emp_teleno, emp_email, emp_job_id);
            queryList.add(e);
        }
        Test.queryRender();

        // 出现提示框：
        //       i = 0：没有符合条件的数据
        //       i != 0：查询到i条数据
        if(i == 0) {
            JOptionPane.showMessageDialog(
                    null,
                    "没有符合条件的数据",
                    "查询结果",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "成功查询到" + i + "条数据！",
                    "查询结果",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // 获取所有的病人信息并把他们存入ArrayList
    //                         patientList -> 初始化渲染可用
    //                         queryPatientList -> 渲染查询结果
    public void getPatientToArray(ArrayList<Patient> list) throws SQLException {
        // 执行查询语句并获取ResultSet
        Statement stat = conn.createStatement();
        String sql = "Select * from patient;";
        ResultSet rs = stat.executeQuery(sql);
        // ResultSet -> ArrayList（list）
        convertPatientReslutSetToArray(list,rs);
    }

    // 把一个ResultSet转化为ArrayList
    public void convertPatientReslutSetToArray(ArrayList<Patient> list,ResultSet rs) throws SQLException {
        // 先把list清空
        list.clear();
        // 转化
        while(rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            String gender = rs.getString(3);
            int age = rs.getInt(10);
            Date startDate = rs.getDate(4);
            String patientDept = rs.getString(5);
            int room = rs.getInt(8);
            int bed = rs.getInt(9);
            String state = rs.getString(6);
            String doctor = rs.getString(7);

            // 创建Patient对象并添加到list
            Patient patient = new Patient(id, name, gender, bed, room,
                    patientDept, startDate, age, state, doctor);
            list.add(patient);
        }
    }

    // 传入一个新的Patient对象，执行sql语句，把数据存入数据库
    public void addPatient(Patient p) throws SQLException {
        String sql = "Insert into patient values" +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,p.getId());
        ps.setString(2,p.getName());
        ps.setString(3,p.getGender());
        ps.setDate(4,p.getStartDate());
        ps.setString(5,p.getPatientDept());
        ps.setString(6,p.getState());
        ps.setString(7,p.getDoctor());
        ps.setInt(8,p.getRoom());
        ps.setInt(9,p.getBed());
        ps.setInt(10,p.getAge());
        ps.executeUpdate();
    }

    public void deletePatient(String id) throws SQLException {
        String sql = "delete from patient where patient_id = " + "'" + id + "';";
        Statement stat = conn.createStatement();
        stat.executeUpdate(sql);
    }

    public void queryPatientInfo(String name) throws SQLException {
        String sql = "Select * from patient where patient_name = " + "'" + name +"'";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        // 结果的渲染
        // ResultSet -> ArrayList
        convertPatientReslutSetToArray(Datas.queryPatientList, rs);
        if(Datas.queryPatientList.size() == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "没有找到姓名为 '" + name + "' 的患者！",
                        "查询结果",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
        }
        // ArrayList -> 渲染
        renderData(Datas.queryPatientList);
    }

    public void addUser(String username, String password) throws SQLException {
        String sql = "insert into user_tab values ('" + username + "','" + password + "','医生');";
        Statement stat = conn.createStatement();
        int res = stat.executeUpdate(sql);
    }
}
