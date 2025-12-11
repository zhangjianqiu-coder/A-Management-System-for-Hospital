package utils;
import template.Employee;
import template.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 全局的数据存储类
public class Datas {
    public static Object transferData;  // 存储要传递的数据
    // 或者使用Map存储多个数据
    public static Map<String, Object> dataMap = new HashMap<>();
    public static String username;
    public static Employee updatingEmployeeInfo = new Employee(); // 正在修改员工的数据的暂存
    public static ArrayList<Patient> patientList = new ArrayList<>(); // 存储所有病人的信息
    public static ArrayList<Patient> queryPatientList = new ArrayList<>(); // 存储查询到病人的结果
    public static Patient updatingPatientInfo = new Patient();
    public static String code; // 存储正确的验证码
}