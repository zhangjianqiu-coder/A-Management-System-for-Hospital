package Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import pages.*;
import template.*;
import utils.*;

import static pages.PatientManagementPage.renderData;

public class Test {
    // 辅助方法：安全获取字符串
    private static String getSafeString(Object value) {
        return value != null ? value.toString() : "";
    }

    // db提供各种查询的函数,直接连接数据库
    public static Dbinter db = new Dbinter();

    // LoginPage
    public static LoginPage loginPage = new LoginPage();

    // RegisterPage
    public static RegisterPage registerPage = new RegisterPage();

    // MainPage
    public static MainPage mainPage = new MainPage();

    // EmployeeManagementPage
    public static EmployeeManagementPage employeeManagementPage;

    // PatientManagementPage
    public static PatientManagementPage patientManagementPage;

    // 员工/病人表格
    public static JTable employeeTable;
    public static JTable patientTable;
    static {
        try {
            employeeManagementPage = new EmployeeManagementPage();
            patientManagementPage = new PatientManagementPage();
            employeeTable = employeeManagementPage.getEmployeeTable();
            patientTable = patientManagementPage.getPatientTable();
            patientTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // 设置可以选中多行
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 刷新函数
    public static void refresh() {
        try {
            // 清空表格
            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
            model.setRowCount(0);

            // 重新查询数据
            Dbinter.list.clear();
            Dbinter.list = db.getEmployeeInfo();

            // 重新填充数据
            for (Employee emp : Dbinter.list) {
                model.addRow(new Object[]{
                        emp.emp_no, emp.emp_name, emp.emp_dept_id, emp.emp_duty, emp.emp_xl,
                        emp.emp_gender, emp.emp_birthday, emp.emp_hometown, emp.emp_country,
                        emp.emp_nation, emp.emp_id, emp.emp_marriage, emp.emp_health,
                        emp.emp_startwork, emp.emp_state, emp.emp_homeaddress, emp.emp_teleno,
                        emp.emp_email, emp.emp_job_id
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // 获取查询结果并渲染到表格的函数
    public static void queryRender() {
        // 清空表格
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        // 渲染queryList里面的数据
        for (Employee emp : Dbinter.queryList) {
            model.addRow(new Object[]{
                    emp.emp_no, emp.emp_name, emp.emp_dept_id, emp.emp_duty, emp.emp_xl,
                    emp.emp_gender, emp.emp_birthday, emp.emp_hometown, emp.emp_country,
                    emp.emp_nation, emp.emp_id, emp.emp_marriage, emp.emp_health,
                    emp.emp_startwork, emp.emp_state, emp.emp_homeaddress, emp.emp_teleno,
                    emp.emp_email, emp.emp_job_id
            });
        }
    }

    public static void main(String[] args) throws SQLException {
        // 主窗口
        JFrame frame = new JFrame("医院管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // ===========================实现页面跳转==============================
        // 1. 创建CardLayout
        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);

        // 2. 添加页面到容器（给每个页面一个名字）
        container.add(loginPage.getContentPane(), "loginPage");
        container.add(mainPage.getContentPane(), "mainPage");
        container.add(employeeManagementPage, "employeeManagementPage");
        container.add(patientManagementPage, "patientManagementPage");
        container.add(registerPage,"registerPage");

        // 3. 把容器添加到主窗口里面
        frame.add(container);

        // 给登录按钮添加点击事件 -> 跳转至主页mainPage
        loginPage.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 如果验证码错误，直接return
                if(!loginPage.getCodeField().getText().equals(Datas.code)) {
                    JOptionPane.showMessageDialog(null,
                            "验证码输入错误！",
                            "登录失败",
                            JOptionPane.WARNING_MESSAGE);
                    // 验证码输入框清空
                    loginPage.getCodeField().setText("");
                    return;
                }
                // 获取输入框内容
                String username = loginPage.getUsernameField().getText();
                char[] passwordChars = loginPage.getPasswordField().getPassword(); // 正确获取密码数组
                String password = new String(passwordChars); // 正确转为字符串
                // 检验登录成不成功
                boolean ans;
                try {
                    ans = db.loginCheck(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (ans) {
                    // 把用户名传入
                    Datas.username = username;
                    System.out.println(Datas.username);
                    // 成功则跳转
                    layout.show(container, "mainPage");
                    // 更新用户名
                    mainPage.updateUsername();
                } else {
                    // 失败则弹出弹窗
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "用户名或密码错误，请重新输入！", // 对话框提示内容
                            "登录失败", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                    // 清空输入框
                    loginPage.getUsernameField().setText("");
                    loginPage.getPasswordField().setText("");

                    System.out.println("登录失败");

                }
            }
        });

        // 点击注册 -> 跳转到注册页面
        loginPage.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(375, 295);
                frame.setLocationRelativeTo(null); // 屏幕中间显示
                layout.show(container,"registerPage");
            }
        });

        // 注册页面的返回
        registerPage.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(500, 400); // 把大小设回原来的
                frame.setLocationRelativeTo(null); // 屏幕中间显示
                layout.show(container, "loginPage");
            }
        });

        // 点击员工管理的事件监听
        // 页面跳转 + 数据渲染（在EmployManagementPage完成）
        JButton btn3 = mainPage.getBtn3();
        JButton btn1 = mainPage.getBtn1();
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 全屏显示
                layout.show(container, "employeeManagementPage");
            }
        });

        // 点击 住院部 -> 跳转到PatientManagementPage
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 全屏显示
                layout.show(container, "patientManagementPage");
            }
        });

        // 点击返回按钮 -> 跳转到mainPage
        employeeManagementPage.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(500, 400); // 把大小设回原来的
                frame.setLocationRelativeTo(null); // 屏幕中间显示
                layout.show(container, "mainPage");
            }
        });

        //============================================================================

        //=====================员工管理页面按钮事件==========================================
        JButton addButton = employeeManagementPage.getAddButton();
        JButton deleteButton = employeeManagementPage.getDeleteButton();
        JButton queryButton = employeeManagementPage.getQueryButton();
        JButton updateButton = employeeManagementPage.getUpdateButton();
        JButton refreshButton = employeeManagementPage.getRefreshButton();
        JTextField searchFild = employeeManagementPage.getSearchField();
        JComboBox<String> searchTypeComboBox = employeeManagementPage.getSearchTypeComboBox();
        JComboBox<String> departmentComboBox = employeeManagementPage.getDepartmentComboBox();
        //================新增员工 按钮========================
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add");
                // 弹窗填表并添加数据
                try {
                    Dialogs.getAddDialog();
                } catch(SQLIntegrityConstraintViolationException exception) {
                    // 员工已经存在（主码冲突）
                    JOptionPane.showMessageDialog(
                            null,
                            "该员工已存在",
                            "添加失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                // 收集表中数据
                refresh();
            }
        });
        // ===================================================

        //================删除选中 按钮=========================
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 先获取选中的数据（可能多行）
                int[] rows = employeeTable.getSelectedRows(); // 行号存入数组中
                // 没有选择数据的时候弹出弹窗提示
                if(rows.length == 0) {
                    // 没有选择行的时候提示：您还未选择数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "您还未选择数据！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                }
                // 获取主键信息，通过主键信息查询即可
                // 弹窗确认
                else if (JOptionPane.showConfirmDialog(null, "确定删除" +
                                rows.length + "条数据？", "确认",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int r : rows) {
                        String emp_no = employeeTable.getValueAt(r, 0).toString();
                        try {
                            db.deleteEmployee(emp_no);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Test.refresh();
                }

            }
        });
        //=====================================================

        //===============选中更新按钮============================
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 先获取选中的数据
                int[] rows = employeeTable.getSelectedRows(); // 行号存入数组中
                // 如果选中的有多行，则不支持修改
                if(rows.length > 1) {
                    // 弹出弹窗提示：不能选择多行数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "抱歉！暂不支持多行修改！请重新选择一行！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                } else if(rows.length == 0) {
                    // 没有选择行的时候提示：您还未选择数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "您还未选择数据！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                } else {
                    // 只选择一行数据的时候
                    // 获取各列的数据
                    // 获取信息,存入Datas.updatingEmployeeInfo对象中
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        int r = rows[0];
                        Datas.updatingEmployeeInfo.emp_no = employeeTable.getValueAt(r, 0).toString();
                        Datas.updatingEmployeeInfo.emp_name = employeeTable.getValueAt(r,1).toString();
                        Datas.updatingEmployeeInfo.emp_dept_id = employeeTable.getValueAt(r,2).toString();
                        Datas.updatingEmployeeInfo.emp_duty = employeeTable.getValueAt(r,3).toString();
                        Datas.updatingEmployeeInfo.emp_xl = employeeTable.getValueAt(r,4).toString();
                        Datas.updatingEmployeeInfo.emp_gender = employeeTable.getValueAt(r,5).toString();
                        try {
                            String birthdayStr = getSafeString(employeeTable.getValueAt(r, 6));
                            if (!birthdayStr.isEmpty()) {
                                Datas.updatingEmployeeInfo.emp_birthday = sdf.parse(birthdayStr);
                            } else {
                                Datas.updatingEmployeeInfo.emp_birthday = null;
                            }
                        } catch (ParseException err) {
                            Datas.updatingEmployeeInfo.emp_birthday = null;
                            System.err.println("生日日期格式错误: " + employeeTable.getValueAt(r, 6));
                        }
                        Datas.updatingEmployeeInfo.emp_hometown = employeeTable.getValueAt(r,7).toString();
                        Datas.updatingEmployeeInfo.emp_country = employeeTable.getValueAt(r,8).toString();
                        Datas.updatingEmployeeInfo.emp_nation = employeeTable.getValueAt(r,9).toString();
                        Datas.updatingEmployeeInfo.emp_id = employeeTable.getValueAt(r,10).toString();
                        Datas.updatingEmployeeInfo.emp_marriage = employeeTable.getValueAt(r,11).toString();
                        Datas.updatingEmployeeInfo.emp_health = employeeTable.getValueAt(r,12).toString();
                        try {
                            String hireDateStr = getSafeString(employeeTable.getValueAt(r, 13));
                            if (!hireDateStr.isEmpty()) {
                                Datas.updatingEmployeeInfo.emp_startwork = sdf.parse(hireDateStr);
                            } else {
                                Datas.updatingEmployeeInfo.emp_startwork = null;
                            }
                        } catch (ParseException err) {
                            Datas.updatingEmployeeInfo.emp_startwork = null;
                            System.err.println("入职日期格式错误: " + employeeTable.getValueAt(r, 13));
                        }
                        Datas.updatingEmployeeInfo.emp_state = employeeTable.getValueAt(r,14).toString();
                        Datas.updatingEmployeeInfo.emp_homeaddress = employeeTable.getValueAt(r,15).toString();
                        Datas.updatingEmployeeInfo.emp_teleno = employeeTable.getValueAt(r,16).toString();
                        Datas.updatingEmployeeInfo.emp_email = employeeTable.getValueAt(r,17).toString();
                        Datas.updatingEmployeeInfo.emp_job_id = employeeTable.getValueAt(r,18).toString();
                    }
                    // getUpdateDialog 获取一个弹窗（里面已经通过Datas.updatingEmployeeInfo对象中的数据，填写了提示框）
                    try {
                        Dialogs.getUpdateDialog();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //===================查询按钮============================
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 如果关键词为空->提示框：您当前还没有输入任何关键词！
                String key = searchFild.getText();
                if(key.equals("")) {
                    JOptionPane.showMessageDialog(
                            null,
                            "您当前还没有输入任何关键词！",
                            "操作失败",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    // 不为空：
                    // 先获取查询类型和部门筛选
                    String type = searchTypeComboBox.getSelectedItem().toString(); // 查询类型的字符串
                    String deptName = departmentComboBox.getSelectedItem().toString(); // 返回部门的字符串
                    // 再执行查询sql
                    try {
                        db.queryEmployeeInfo(type,deptName,key);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
        //======================================================

        //===================刷新按钮=============================
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        //==============================================================================

        //==============================病人管理页面按钮点击事件=============================
        JButton pexitButton = patientManagementPage.getExitButton();
        JButton paddButton = patientManagementPage.getAddButton();
        JButton prefreshButton = patientManagementPage.getRefreshButton();
        JButton pupdateButton = patientManagementPage.getUpdateButton();
        JButton pdeleteButton = patientManagementPage.getDeleteButton();
        JButton getQueryButton = patientManagementPage.getQueryButton();
        //================添加病人 按钮========================
        paddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 出现弹窗，要求填表
                // 把输入款/下拉菜单里面的数据，new一个Patient对象
                // 把这个Patient添加到patientList里面，重新调用
                // 调用renderData(ArrayList<Patient> list)函数（相当于是刷新了）
                // 这些动作都在Dialogs类里面封装的函数中实现
                try {
                    Dialogs.getAddPatientDialog();
                } catch(SQLIntegrityConstraintViolationException exception) {
                    // 病人已经存在（主码冲突）
                    JOptionPane.showMessageDialog(
                            null,
                            "该病人已存在",
                            "添加失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //===================================================

        //================返回 按钮========================
        pexitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到MainPage
                frame.setSize(500, 400); // 把大小设回原来的
                frame.setLocationRelativeTo(null); // 屏幕中间显示
                layout.show(container, "mainPage");
            }
        });
        //===================================================

        //================修改选中 按钮========================
        pupdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable pmt = patientManagementPage.getPatientTable();
                // 先获取选中的数据
                int[] rows = pmt.getSelectedRows(); // 行号存入数组中
                // 如果选中的有多行，则不支持修改
                if(rows.length > 1) {
                    // 弹出弹窗提示：不能选择多行数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "抱歉！暂不支持多行修改！请重新选择一行！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                } else if(rows.length == 0) {
                    // 没有选择行的时候提示：您还未选择数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "您还未选择数据！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                } else {
                    // 只选择一行数据的时候
                    // 获取各列的数据
                    // 获取信息,存入Datas.updatingPatientInfo对象中
                    {
                        // 收集选中的那一行的信息，并存入updatingPatientInfo对象
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        int r = rows[0];
                        Datas.updatingPatientInfo.setId(pmt.getValueAt(r, 0).toString());
                        Datas.updatingPatientInfo.setName(pmt.getValueAt(r, 1).toString());
                        Datas.updatingPatientInfo.setGender(pmt.getValueAt(r, 2).toString());

                        if(pmt.getValueAt(r,3).toString() == "") {
                            Datas.updatingPatientInfo.setAge(0);
                        } else {
                            Datas.updatingPatientInfo.setAge(Integer.parseInt(pmt.getValueAt(r, 3).toString()));
                        }

                        try {
                            String startDateStr = getSafeString(pmt.getValueAt(r, 4));
                            Datas.updatingPatientInfo.setStartDate(
                                    startDateStr.isEmpty() ? null :
                                            new java.sql.Date(sdf.parse(startDateStr).getTime())
                            );
                        } catch (ParseException err) {
                            Datas.updatingPatientInfo.setStartDate(null);
                            System.err.println("入院日期格式错误: " + pmt.getValueAt(r, 4));
                        }
                        Datas.updatingPatientInfo.setPatientDept(pmt.getValueAt(r, 5).toString());

                        if(pmt.getValueAt(r,6).toString() == "") {
                            Datas.updatingPatientInfo.setRoom(0);
                        } else {
                            Datas.updatingPatientInfo.setRoom(Integer.parseInt(pmt.getValueAt(r, 6).toString()));
                        }

                        if(pmt.getValueAt(r,7).toString() == "") {
                            Datas.updatingPatientInfo.setBed(0);
                        } else {
                            Datas.updatingPatientInfo.setBed(Integer.parseInt(pmt.getValueAt(r, 7).toString()));
                        }

                        Datas.updatingPatientInfo.setState(pmt.getValueAt(r, 8).toString());
                        Datas.updatingPatientInfo.setDoctor(pmt.getValueAt(r, 9).toString());
                    }
                    // getUpdatePatientDialog 获取一个弹窗
                    // 在此函数中，把里面把updatingPatientInfo的数据写入对话框
                    try {
                        Dialogs.getUpdatePatientDialog();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        //===================================================

        //================删除选中 按钮========================
        pdeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取病人表格
                int[] rows = patientTable.getSelectedRows();
                // 没有选中则弹出提示框
                if(rows.length == 0) {
                    // 没有选择行的时候提示：您还未选择数据
                    JOptionPane.showMessageDialog(
                            null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                            "您还未选择数据！", // 对话框提示内容
                            "温馨提示", // 对话框标题栏文字
                            JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                    );
                }
                else if (JOptionPane.showConfirmDialog(null, "确定删除" +
                                rows.length + "条数据？", "确认",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    for (int r : rows) {
                        String id = patientTable.getValueAt(r, 0).toString();
                        try {
                            db.deletePatient(id);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    // 刷新页面
                    Datas.patientList.clear();
                    try {
                        db.getPatientToArray(Datas.patientList);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    renderData(Datas.patientList);
                }
            }
        });
        //===================================================

        //================刷新 按钮===========================
        prefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 刷新页面
                Datas.patientList.clear();
                try {
                    db.getPatientToArray(Datas.patientList);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                renderData(Datas.patientList);
            }
        });
        //===================================================

        //================搜索 按钮============================
         getQueryButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String name = patientManagementPage.getSearchField().getText();
                 if(name.equals("")) {
                     // 没有任何输入的时候
                     JOptionPane.showMessageDialog(
                             null, // 父窗口，null表示居中显示
                             "您还没有输入所查询的患者的姓名！", // 提示内容
                             "查询失败", // 标题
                             JOptionPane.INFORMATION_MESSAGE // 消息类型
                     );
                 } else {
                     try {
                         db.queryPatientInfo(name);
                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }
                 }
             }
         });
        //====================================================
        //==============================================================================

        // ===============================注册页面的注册按钮================================
        JButton submitButton = registerPage.getSubmitButton();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField usernameText = registerPage.getInputField1(); // 用户名输入框
                JTextField passwordText = registerPage.getInputField2(); // 密码输入框
                String username = usernameText.getText().trim();
                String password = passwordText.getText();

                // 1. 输入框内容为空的检测
                if(username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "用户名或密码不能为空！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return; // 立即返回，不再执行后续代码
                }

                // 2. 密码检验
                // 2.1 长度检查
                if(password.length() < 5) {
                    JOptionPane.showMessageDialog(
                            null,
                            "密码长度必须至少5位！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // 2.2 字符类型检查
                boolean hasValidChars = true;
                for(int i = 0; i < password.length(); i++) {
                    char ch = password.charAt(i);
                    if(!(ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z' || ch >= '0' && ch <= '9')) {
                        hasValidChars = false;
                        break;
                    }
                }
                if(!hasValidChars) {
                    JOptionPane.showMessageDialog(
                            null,
                            "密码只能包含字母和数字！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // 3. 尝试注册到数据库
                try {
                    db.addUser(username, password);

                    // 注册成功
                    JOptionPane.showMessageDialog(
                            null,
                            "✅ 用户 " + username + " 注册成功！",
                            "注册成功",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // 清空输入框
                    usernameText.setText("");
                    passwordText.setText("");

                    // 页面跳转
                    frame.setSize(500, 400);
                    frame.setLocationRelativeTo(null);
                    layout.show(container, "loginPage");

                } catch(SQLIntegrityConstraintViolationException exception) {
                    // 用户名已存在
                    JOptionPane.showMessageDialog(
                            null,
                            "用户名 \"" + username + "\" 已存在！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        // 显示窗口
        frame.setVisible(true);
    }
}
