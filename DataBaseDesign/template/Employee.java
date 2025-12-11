package template;

import java.util.Date;

public class Employee {
    public String emp_no;
    public String emp_name;
    public String emp_dept_id;
    public String emp_duty;
    public String emp_xl;
    public String emp_gender;
    public Date emp_birthday;
    public String emp_hometown;
    public String emp_country;
    public String emp_nation;
    public String emp_id;
    public String emp_marriage;
    public String emp_health;
    public Date emp_startwork;
    public String emp_state ;
    public String emp_homeaddress;
    public String emp_teleno;
    public String emp_email ;
    public String emp_job_id;
    public static int patientNum = 0;

    public Employee() {}

    public Employee(String emp_no, String emp_name, String emp_dept_id, String emp_duty,
                    String emp_xl, String emp_gender, String emp_hometown, Date emp_birthday,
                    String emp_country, String emp_nation, String emp_id, String emp_marriage,
                    String emp_health, Date emp_startwork, String emp_state, String emp_homeaddress,
                    String emp_teleno, String emp_email, String emp_job_id)
    {
        this.emp_no = emp_no;
        this.emp_name = emp_name;
        this.emp_dept_id = emp_dept_id;
        this.emp_duty = emp_duty;
        this.emp_xl = emp_xl;
        this.emp_gender = emp_gender;
        this.emp_hometown = emp_hometown;
        this.emp_birthday = emp_birthday;
        this.emp_country = emp_country;
        this.emp_nation = emp_nation;
        this.emp_id = emp_id;
        this.emp_marriage = emp_marriage;
        this.emp_health = emp_health;
        this.emp_startwork = emp_startwork;
        this.emp_state = emp_state;
        this.emp_homeaddress = emp_homeaddress;
        this.emp_teleno = emp_teleno;
        this.emp_email = emp_email;
        this.emp_job_id = emp_job_id;
    }
}
