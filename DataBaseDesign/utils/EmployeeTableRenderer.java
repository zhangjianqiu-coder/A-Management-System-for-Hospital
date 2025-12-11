package utils;
import template.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeTableRenderer {

    // 固定列名
    private static final String[] COLUMN_NAMES = {
            "员工号", "姓名", "部门", "岗位", "学历", "性别", "生日",
            "祖籍", "国籍", "民族", "身份证号", "婚姻状况", "健康状况",
            "入职日期", "状态", "家庭住址", "联系电话", "邮箱", "工资（元/月）"
    };

    /**
     * @param employeeList 员工列表
     * @return 渲染好的JTable
     */
    public static JTable render(ArrayList<Employee> employeeList) {
        // 创建数据数组
        Object[][] data = new Object[employeeList.size()][19];
        for (int i = 0; i < employeeList.size(); i++) {
            Employee emp = employeeList.get(i);
            data[i][0] = emp.emp_no;
            data[i][1] = emp.emp_name;
            data[i][2] = emp.emp_dept_id;
            data[i][3] = emp.emp_duty;
            data[i][4] = emp.emp_xl;
            data[i][5] = emp.emp_gender;
            data[i][6] = emp.emp_birthday;
            data[i][7] = emp.emp_hometown;
            data[i][8] = emp.emp_country;
            data[i][9] = emp.emp_nation;
            data[i][10] = emp.emp_id;
            data[i][11] = emp.emp_marriage;
            data[i][12] = emp.emp_health;
            data[i][13] = emp.emp_startwork;
            data[i][14] = emp.emp_state;
            data[i][15] = emp.emp_homeaddress;
            data[i][16] = emp.emp_teleno;
            data[i][17] = emp.emp_email;
            data[i][18] = emp.emp_job_id;
        }

        // 创建并返回表格
        return new JTable(new DefaultTableModel(data, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    /**
     * 从ResultSet渲染员工表格
     * @param rs 数据库结果集
     * @return 渲染好的JTable
     */
    public static JTable renderFromResultSet(ResultSet rs) throws SQLException {
        // 收集数据
        ArrayList<Object[]> rows = new ArrayList<>();
        while (rs.next()) {
            Object[] row = new Object[19];
            row[0] = rs.getString("emp_no");
            row[1] = rs.getString("emp_name");
            row[2] = rs.getString("emp_dept_id");
            row[3] = rs.getString("emp_duty");
            row[4] = rs.getString("emp_xl");
            row[5] = rs.getString("emp_gender");
            row[6] = rs.getDate("emp_birthday");
            row[7] = rs.getString("emp_hometown");
            row[8] = rs.getString("emp_country");
            row[9] = rs.getString("emp_nation");
            row[10] = rs.getString("emp_id");
            row[11] = rs.getString("emp_marriage");
            row[12] = rs.getString("emp_health");
            row[13] = rs.getDate("emp_startwork");
            row[14] = rs.getString("emp_state");
            row[15] = rs.getString("emp_homeaddress");
            row[16] = rs.getString("emp_teleno");
            row[17] = rs.getString("emp_email");
            row[18] = rs.getString("emp_job_id");
            rows.add(row);
        }

        // 转换为二维数组
        Object[][] data = rows.toArray(new Object[0][]);

        // 创建并返回表格
        return new JTable(new DefaultTableModel(data, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}