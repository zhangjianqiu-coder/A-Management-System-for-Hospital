package pages;

import template.Employee;
import utils.Dbinter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManagementPage extends JPanel {
    // 按钮组件
    private JButton addButton;
    private JButton deleteButton;
    private JButton queryButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JButton exitButton;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JComboBox<String> departmentComboBox;
    private JTable employeeTable;

    Dbinter db = new Dbinter();

    public EmployeeManagementPage() throws SQLException {
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 初始化界面
        initUI();
    }

    private void initUI() throws SQLException {
        // 1. 顶部面板 - 查询区域和操作按钮
        JPanel topPanel = new JPanel(new BorderLayout());

        // 查询面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("查询类型:"));
        searchTypeComboBox = new JComboBox<>(new String[]{"按姓名", "按工号", "按身份证号", "按电话"});
        searchPanel.add(searchTypeComboBox);

        searchPanel.add(new JLabel("关键词:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);

        searchPanel.add(new JLabel("部门筛选:"));
        departmentComboBox = new JComboBox<>(new String[]{"全部部门", "门诊部", "住院部", "管理部", "药品与仪器部"});
        searchPanel.add(departmentComboBox);

        queryButton = new JButton("查询");
        searchPanel.add(queryButton);

        // 操作按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        addButton = new JButton("新增员工");
        deleteButton = new JButton("删除选中");
        updateButton = new JButton("更新选中");
        refreshButton = new JButton("刷新");
        exitButton = new JButton("返回");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(exitButton);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);


        // 2.表格部分
        // ===========================建立表格并渲染数据=====================================
        {
            String[] columnNames = {"员工号", "姓名", "部门", "岗位", "学历",
                    "性别", "生日", "祖籍", "国籍", "民族", "身份证号", "婚姻状况", "健康状况",
                    "入职日期", "状态", "家庭住址", "联系电话", "邮箱", "工资"};
            // 获取员工信息list
            ArrayList<Employee> employeeList = db.getEmployeeInfo();
            // 创建二维数组
            Object[][] data = new Object[employeeList.size()][19];
            // 将ArrayList数据填充到二维数组
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
            // 数据渲染到表格上
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // 不可编辑
                }
            };
            employeeTable = new JTable(tableModel);
        }
        //=========================================================================================


        // ============ 设置表格显示（单元格高度和滑动显示） ====================
        // 1. 增加行高
        employeeTable.setRowHeight(30);
        // 2. 设置列宽
        int[] columnWidths = {80, 80, 80, 100, 60, 50, 90, 120, 60, 60,
                150, 70, 70, 90, 60, 200, 110, 150, 90};
        for (int i = 0; i < columnWidths.length && i < employeeTable.getColumnCount(); i++) {
            employeeTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        // 3. 允许水平滚动
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //=================================================================

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        // 添加到主面板
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    // ==================== 暴露的接口方法 =====================================
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getQueryButton() {
        return queryButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getSearchTypeComboBox() {
        return searchTypeComboBox;
    }

    public JComboBox<String> getDepartmentComboBox() {
        return departmentComboBox;
    }

    public JTable getEmployeeTable() {
        return employeeTable;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
    // ======================================================================


}