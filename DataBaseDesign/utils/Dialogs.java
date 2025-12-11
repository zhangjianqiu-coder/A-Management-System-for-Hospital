package utils;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Test.Test;
import template.Patient;

import static pages.PatientManagementPage.renderData;

public class Dialogs {
    // 下拉菜单按照value值默认选中
    private static void setComboBoxSelection(JComboBox<String> comboBox, String value) {
        if (value != null && !value.trim().isEmpty()) {
            comboBox.setSelectedItem(value);
        } else {
            if (comboBox.getItemCount() > 0) {
                comboBox.setSelectedIndex(0);
            }
        }
    }

    public static Dbinter db = new Dbinter();

    public static void getAddDialog() throws SQLException {
        // 创建输入组件并保存引用
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"住院部", "门诊部", "员工管理部", "药品与仪器部"});
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{"主治医师", "主任医师", "门诊护士", "副主任医师",
                "住院护士长", "住院医师", "主管药师", "药剂师", "人事主管", "行政专员"});
        JComboBox<String> educationCombo = new JComboBox<>(new String[]{"大专", "本科", "硕士", "博士"});
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"男", "女"});
        JTextField birthField = new JTextField(15);
        JTextField originField = new JTextField(15);
        JTextField nationalityField = new JTextField(15);
        JTextField ethnicityField = new JTextField(15);
        JTextField idCardField = new JTextField(15);
        JComboBox<String> maritalCombo = new JComboBox<>(new String[]{"未婚", "已婚", "离异"});
        JComboBox<String> healthCombo = new JComboBox<>(new String[]{"优秀", "良好", "一般", "较差"});
        JTextField hireDateField = new JTextField(15);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"在职", "离职", "休假"});
        JTextField addressField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField jobCodeField = new JTextField(15);

        // 显示对话框
        int result = JOptionPane.showConfirmDialog(
                null,
                new Object[]{
                        "员工号 * :", idField,
                        "姓名 * :", nameField,
                        "部门:", deptCombo,
                        "岗位:", positionCombo,
                        "学历:", educationCombo,
                        "性别:", genderCombo,
                        "生日(yyyy-MM-dd):", birthField,
                        "祖籍:", originField,
                        "国籍:", nationalityField,
                        "民族:", ethnicityField,
                        "身份证号 * :", idCardField,
                        "婚姻状况:", maritalCombo,
                        "健康状况:", healthCombo,
                        "入职日期(yyyy-MM-dd):", hireDateField,
                        "状态:", statusCombo,
                        "家庭住址:", addressField,
                        "联系电话:", phoneField,
                        "邮箱:", emailField,
                        "工资（元/月）:", jobCodeField
                },
                "添加员工",
                JOptionPane.OK_CANCEL_OPTION
        );

        // 获取所有输入数据
        String employeeId = idField.getText();
        String name = nameField.getText();
        String department = (String) deptCombo.getSelectedItem();
        String position = (String) positionCombo.getSelectedItem();
        String education = (String) educationCombo.getSelectedItem();
        String gender = (String) genderCombo.getSelectedItem();
        String birthday = birthField.getText();
        String origin = originField.getText();
        String nationality = nationalityField.getText();
        String ethnicity = ethnicityField.getText();
        String idCard = idCardField.getText();
        String maritalStatus = (String) maritalCombo.getSelectedItem();
        String healthStatus = (String) healthCombo.getSelectedItem();
        String hireDate = hireDateField.getText();
        String workStatus = (String) statusCombo.getSelectedItem();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String jobCode = jobCodeField.getText();

        // 检查用户是否点击了"确定"
        if (result == JOptionPane.OK_OPTION) {
            // Not Null的三个属性必须填写，否则提交不了
            if(employeeId.equals("") || name.equals("") || idCard.equals("")) {
                // 弹出提示弹窗
                JOptionPane.showMessageDialog(
                        null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                        "带 * 的选项必须填写！", // 对话框提示内容
                        "缺少有效信息", // 对话框标题栏文字
                        JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                );
                // 重新打开窗口
                 Dialogs.getAddDialog();
            } else {
                // 执行添加操作
                db.addEmployee(employeeId, name, department, position,
                        education, gender, birthday, origin, nationality,
                        ethnicity, idCard, maritalStatus, healthStatus, hireDate,
                        workStatus, address, phone, email, jobCode);
            }
        }
    }

    public static void getUpdateDialog() throws SQLException {
        // 创建输入组件并保存引用
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"住院部", "门诊部", "员工管理部", "药品与仪器部"});
        JComboBox<String> positionCombo = new JComboBox<>(new String[]{"主治医师", "主任医师", "门诊护士", "副主任医师",
                "住院护士长", "住院医师", "主管药师", "药剂师", "人事主管", "行政专员"});
        JComboBox<String> educationCombo = new JComboBox<>(new String[]{"大专", "本科", "硕士", "博士"});
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"男", "女"});
        JTextField birthField = new JTextField(15);
        JTextField originField = new JTextField(15);
        JTextField nationalityField = new JTextField(15);
        JTextField ethnicityField = new JTextField(15);
        JTextField idCardField = new JTextField(15);
        JComboBox<String> maritalCombo = new JComboBox<>(new String[]{"未婚", "已婚", "离异"});
        JComboBox<String> healthCombo = new JComboBox<>(new String[]{"优秀", "良好", "一般", "较差"});
        JTextField hireDateField = new JTextField(15);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"在职", "离职", "休假"});
        JTextField addressField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField jobCodeField = new JTextField(15);

        // 先把Datas.updatingEmployeeInfo数据写上去
        idField.setText(Datas.updatingEmployeeInfo.emp_no);
        nameField.setText(Datas.updatingEmployeeInfo.emp_name);
        originField.setText(Datas.updatingEmployeeInfo.emp_hometown);
        nationalityField.setText(Datas.updatingEmployeeInfo.emp_country);
        ethnicityField.setText(Datas.updatingEmployeeInfo.emp_nation);
        idCardField.setText(Datas.updatingEmployeeInfo.emp_id);
        addressField.setText(Datas.updatingEmployeeInfo.emp_homeaddress);
        phoneField.setText(Datas.updatingEmployeeInfo.emp_teleno);
        birthField.setText(DateUtil.formatDateToString(Datas.updatingEmployeeInfo.emp_birthday)); // 日期特殊处理
        hireDateField.setText(DateUtil.formatDateToString(Datas.updatingEmployeeInfo.emp_startwork)); // 日期处理
        emailField.setText(Datas.updatingEmployeeInfo.emp_email);
        jobCodeField.setText(Datas.updatingEmployeeInfo.emp_job_id);
        setComboBoxSelection(deptCombo, Datas.updatingEmployeeInfo.emp_dept_id);
        setComboBoxSelection(positionCombo, Datas.updatingEmployeeInfo.emp_duty);
        setComboBoxSelection(educationCombo, Datas.updatingEmployeeInfo.emp_xl);
        setComboBoxSelection(genderCombo, Datas.updatingEmployeeInfo.emp_gender);
        setComboBoxSelection(maritalCombo, Datas.updatingEmployeeInfo.emp_marriage);
        setComboBoxSelection(healthCombo, Datas.updatingEmployeeInfo.emp_health);
        setComboBoxSelection(statusCombo, Datas.updatingEmployeeInfo.emp_state);

        // 显示对话框
        int result = JOptionPane.showConfirmDialog(
                null,
                new Object[]{
                        "员工号 * :", idField,
                        "姓名 * :", nameField,
                        "部门:", deptCombo,
                        "岗位:", positionCombo,
                        "学历:", educationCombo,
                        "性别:", genderCombo,
                        "生日(yyyy-MM-dd):", birthField,
                        "祖籍:", originField,
                        "国籍:", nationalityField,
                        "民族:", ethnicityField,
                        "身份证号 * :", idCardField,
                        "婚姻状况:", maritalCombo,
                        "健康状况:", healthCombo,
                        "入职日期(yyyy-MM-dd):", hireDateField,
                        "状态:", statusCombo,
                        "家庭住址:", addressField,
                        "联系电话:", phoneField,
                        "邮箱:", emailField,
                        "工资（元/月）", jobCodeField
                },
                "修改数据",
                JOptionPane.OK_CANCEL_OPTION
        );
        // 检查用户是否点击了"确定"
        if (result == JOptionPane.OK_OPTION) {
            // 重新设置数据（真正的更新数据）
            // 注意必须在点击的时候触发信息收集
            String employeeId = idField.getText();
            String name = nameField.getText();
            String department = (String) deptCombo.getSelectedItem();
            String position = (String) positionCombo.getSelectedItem();
            String education = (String) educationCombo.getSelectedItem();
            String gender = (String) genderCombo.getSelectedItem();
            String birthday = birthField.getText();
            String origin = originField.getText();
            String nationality = nationalityField.getText();
            String ethnicity = ethnicityField.getText();
            String idCard = idCardField.getText();
            String maritalStatus = (String) maritalCombo.getSelectedItem();
            String healthStatus = (String) healthCombo.getSelectedItem();
            String hireDate = hireDateField.getText();
            String workStatus = (String) statusCombo.getSelectedItem();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String jobCode = jobCodeField.getText();

            if(employeeId.equals("") || name.equals("") || idCard.equals("")) {
                // 弹出提示弹窗
                JOptionPane.showMessageDialog(
                        null, // 父窗口：null 表示对话框居中显示（不依附于任何窗口）
                        "带 * 的选项必须填写！", // 对话框提示内容
                        "缺少有效信息", // 对话框标题栏文字
                        JOptionPane.ERROR_MESSAGE // 对话框图标类型：错误图标（红色叉号）
                );
                // 重新打开窗口
                Dialogs.getAddDialog();
            } else {
                // 先监测是否真的做了修改
                //      是：先删除该员工信息，再重新把收集的数据添加到数据库里面，最后刷新以下即可
                //      否：提示框：你还没有对员工信息进行任何修改！
                if(employeeId.equals(Datas.updatingEmployeeInfo.emp_no) &&
                        name.equals(Datas.updatingEmployeeInfo.emp_name) &&
                        department.equals(Datas.updatingEmployeeInfo.emp_dept_id) &&
                        position.equals(Datas.updatingEmployeeInfo.emp_duty) &&
                        education.equals(Datas.updatingEmployeeInfo.emp_xl) &&
                        gender.equals(Datas.updatingEmployeeInfo.emp_gender) &&
                        DateUtil.compareDates(birthday, Datas.updatingEmployeeInfo.emp_birthday) &&
                        origin.equals(Datas.updatingEmployeeInfo.emp_hometown) &&
                        nationality.equals(Datas.updatingEmployeeInfo.emp_country) &&
                        ethnicity.equals(Datas.updatingEmployeeInfo.emp_nation) &&
                        idCard.equals(Datas.updatingEmployeeInfo.emp_id) &&
                        maritalStatus.equals(Datas.updatingEmployeeInfo.emp_marriage) &&
                        healthStatus.equals(Datas.updatingEmployeeInfo.emp_health) &&
                        DateUtil.compareDates(hireDate, Datas.updatingEmployeeInfo.emp_startwork) &&
                        workStatus.equals(Datas.updatingEmployeeInfo.emp_state) &&
                        address.equals(Datas.updatingEmployeeInfo.emp_homeaddress) &&
                        phone.equals(Datas.updatingEmployeeInfo.emp_teleno) &&
                        email.equals(Datas.updatingEmployeeInfo.emp_email) &&
                        jobCode.equals(Datas.updatingEmployeeInfo.emp_job_id))
                {
                    // 如果所有字段都没有修改,就弹出提示框
                    JOptionPane.showMessageDialog(null, "员工信息未做任何修改！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    // 重新获取对话框
                    Dialogs.getUpdateDialog();
                }
                else {
                    // 先删除原先的此员工信息(用主码查询)
                    db.deleteEmployee(Datas.updatingEmployeeInfo.emp_no);
                    // 执行添加操作
                    db.addEmployee(employeeId, name, department, position,
                            education, gender, birthday, origin, nationality,
                            ethnicity, idCard, maritalStatus, healthStatus, hireDate,
                            workStatus, address, phone, email, jobCode);
                    // 刷新
                    Test.refresh();
                    // 出现提示框->更新成功
                    JOptionPane.showMessageDialog(
                            null,
                            "✅ 更新成功",
                            "系统提示",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        }
    }

    public static void getAddPatientDialog() throws SQLException, ParseException {
        // 创建输入组件并保存引用
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"男", "女"});
        JTextField ageField = new JTextField(15);
        JTextField startDateField = new JTextField(15);
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"内科", "外科", "儿科", "妇产科",
                "急诊科", "心血管科", "神经科", "骨科"});
        JTextField roomField = new JTextField(15);
        JTextField bedField = new JTextField(15);
        JTextField stateField = new JTextField(15);
        JTextField doctorField = new JTextField(15);

        // 显示对话框
        int result = JOptionPane.showConfirmDialog(
                null,
                new Object[]{
                        "患者身份证号 * :", idField,
                        "姓名 * :", nameField,
                        "性别:", genderCombo,
                        "年龄:", ageField,
                        "入院日期(yyyy-MM-dd)(默认今天):", startDateField,
                        "所属部门:", deptCombo,
                        "病房号:", roomField,
                        "床位号:", bedField,
                        "状态:", stateField,
                        "主治医生:", doctorField
                },
                "添加患者",
                JOptionPane.OK_CANCEL_OPTION
        );

        // 检查用户是否点击了"确定"
        if (result == JOptionPane.OK_OPTION) {
            // Not Null的两个属性必须填写
            if(idField.getText().equals("") || nameField.getText().equals("")) {
                // 弹出提示弹窗
                JOptionPane.showMessageDialog(
                        null,
                        "带 * 的选项必须填写！",
                        "缺少有效信息",
                        JOptionPane.ERROR_MESSAGE
                );
                // 递归调用重新打开对话框
                getAddPatientDialog();
            } else {
                // 获取所有输入数据
                String patientId = idField.getText();
                String name = nameField.getText();
                String gender = (String) genderCombo.getSelectedItem();
                int age = 0;
                int room = 0;
                int bed = 0;
                try {
                    // 年龄
                    if(ageField.getText().equals("")) {
                        age = 0;
                    } else {
                        age = Integer.parseInt(ageField.getText());
                    }
                    // 病房号
                    if(roomField.getText().equals("")) {
                        room = 0;
                    } else {
                        room = Integer.parseInt(roomField.getText());
                    }

                    // 病床号
                    if(bedField.getText().equals("")) {
                        bed = 0;
                    } else {
                        bed = Integer.parseInt(bedField.getText());
                    }
                } catch(RuntimeException e) {
                    // 弹出弹窗提示：年龄、病房号、病床号必须为整数
                    JOptionPane.showMessageDialog(
                            null,
                            "年龄、病房号、病床号必须为整数",
                            "添加失败",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    // 重新弹出对话框
                    getAddPatientDialog();
                }
                java.util.Date utilDate = DateUtil.stringToSqlDate(startDateField.getText().trim());
                java.sql.Date startDate = (utilDate != null) ?
                        new java.sql.Date(utilDate.getTime()) :
                        new java.sql.Date(System.currentTimeMillis());
                String department = (String) deptCombo.getSelectedItem();
                String state = stateField.getText();
                String doctor = doctorField.getText();

                // 创建Patient对象
                // 执行sql,存入数据库
                db.addPatient(new Patient(patientId, name, gender, bed, room,
                                        department, startDate, age, state ,doctor));
                // 重新渲染
                Datas.patientList.clear();
                db.getPatientToArray(Datas.patientList);
                renderData(Datas.patientList);
            }
        }
    }

    public static void getUpdatePatientDialog() throws SQLException {
        // 创建输入组件
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"男", "女"});
        JTextField ageField = new JTextField(15);
        JTextField startDateField = new JTextField(15);
        JComboBox<String> deptCombo = new JComboBox<>(new String[]{"内科", "外科", "儿科", "妇产科",
                "急诊科", "心血管科", "神经科", "骨科"});
        JTextField roomField = new JTextField(15);
        JTextField bedField = new JTextField(15);
        JTextField stateField = new JTextField(15);
        JTextField doctorField = new JTextField(15);

        // 填充现有数据
        idField.setText(Datas.updatingPatientInfo.id);
        nameField.setText(Datas.updatingPatientInfo.name);
        if(Datas.updatingPatientInfo.age == 0) {
            ageField.setText("");
        } else {
            ageField.setText(Datas.updatingPatientInfo.age + ""); // 转化为字符串
        }
        startDateField.setText(DateUtil.formatDateToString(Datas.updatingPatientInfo.startDate));

        if(Datas.updatingPatientInfo.room == 0) {
            roomField.setText("");
        } else {
            roomField.setText(Datas.updatingPatientInfo.room + ""); // 转化为字符串
        }

        if(Datas.updatingPatientInfo.bed == 0) {
            bedField.setText("");
        } else {
            bedField.setText(Datas.updatingPatientInfo.bed + ""); // 转化为字符串
        }

        stateField.setText(Datas.updatingPatientInfo.state);
        doctorField.setText(Datas.updatingPatientInfo.doctor);
        setComboBoxSelection(genderCombo, Datas.updatingPatientInfo.gender);
        setComboBoxSelection(deptCombo, Datas.updatingPatientInfo.patientDept);

        // 显示对话框
        int result = JOptionPane.showConfirmDialog(
                null,
                new Object[]{
                        "患者身份证号 * :", idField,
                        "姓名 * :", nameField,
                        "性别:", genderCombo,
                        "年龄:", ageField,
                        "入院日期(yyyy-MM-dd):", startDateField,
                        "所属部门:", deptCombo,
                        "病房号:", roomField,
                        "床位号:", bedField,
                        "状态:", stateField,
                        "主治医生:", doctorField
                },
                "修改患者信息",
                JOptionPane.OK_CANCEL_OPTION
        );

        // 点击确定
        if (result == JOptionPane.OK_OPTION) {
            // 验证必填
            if(idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "带 * 的选项必须填写！", "缺少有效信息", JOptionPane.ERROR_MESSAGE);
                getUpdatePatientDialog();
                return;
            }

            try {
                // 获取输入
                String patientId = idField.getText().trim();
                String name = nameField.getText().trim();
                String gender = (String) genderCombo.getSelectedItem();
                int age = ageField.getText().isEmpty() ? 0 : Integer.parseInt(ageField.getText()); // 没填就存0
                int room = roomField.getText().isEmpty() ? 0 : Integer.parseInt(roomField.getText()); // 没填就存0
                int bed = bedField.getText().isEmpty() ? 0 : Integer.parseInt(bedField.getText()); // 没填就存0
                String department = (String) deptCombo.getSelectedItem();
                String state = stateField.getText().trim();
                String doctor = doctorField.getText().trim();
                String startDate = startDateField.getText();

                // 检查是否有修改
                if(patientId.equals(Datas.updatingPatientInfo.id) &&
                        name.equals(Datas.updatingPatientInfo.name) &&
                        gender.equals(Datas.updatingPatientInfo.gender) &&
                        age == Datas.updatingPatientInfo.age &&
                        DateUtil.compareDates(startDate, Datas.updatingPatientInfo.startDate) &&
                        department.equals(Datas.updatingPatientInfo.patientDept) &&
                        room == Datas.updatingPatientInfo.room &&
                        bed == Datas.updatingPatientInfo.bed &&
                        state.equals(Datas.updatingPatientInfo.state) &&
                        doctor.equals(Datas.updatingPatientInfo.doctor))
                {
                    JOptionPane.showMessageDialog(null, "患者信息未做任何修改！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    Dialogs.getUpdatePatientDialog(); // 重新获取对话框
                }
                else {
                    // 执行更新
                    java.sql.Date date = DateUtil.stringToSqlDate(startDate);
                    db.deletePatient(Datas.updatingPatientInfo.id);
                    Patient updatedPatient = new Patient(patientId,name,gender,bed,room,
                            department,date,age,state,doctor);
                    db.addPatient(updatedPatient);

                    // 刷新
                    Datas.patientList.clear();
                    db.getPatientToArray(Datas.patientList);
                    renderData(Datas.patientList);

                    JOptionPane.showMessageDialog(null, "✅ 修改成功", "修改成功", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "年龄、病房号、病床号必须为整数", "输入错误", JOptionPane.ERROR_MESSAGE);
                getUpdatePatientDialog();
            }
        }
    }
}