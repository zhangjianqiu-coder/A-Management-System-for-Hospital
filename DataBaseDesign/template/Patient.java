package template;

import java.sql.Date;
import java.util.ArrayList;

public class Patient {
    public String id; // 身份证
    public String name;
    public String gender;
    public int age;
    public Date startDate;
    public String patientDept;
    public int room;
    public int bed;
    public String state;
    public String doctor;

    public Patient(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setPatientDept(String patientDept) {
        this.patientDept = patientDept;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Patient(String id, String doctor, String state,
                   int bed, int room, String patientDept, Date startDate,
                   int age, String gender, String name) {
        this.id = id;
        this.doctor = doctor;
        this.state = state;
        this.bed = bed;
        this.room = room;
        this.patientDept = patientDept;
        this.startDate = startDate;
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    // 把ArrayList<Patient>转化为一个Object[][]
    public static Object[][] convertPatientsToArray(ArrayList<Patient> patients) {
        if (patients == null || patients.isEmpty()) {
            return new Object[0][0];
        }

        Object[][] result = new Object[patients.size()][10]; // 10个字段

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            result[i][0] = p.getId();
            result[i][1] = p.getName();
            result[i][2] = p.getGender();
            if(p.getAge() == 0) {
                result[i][3] = "";
            } else {
                result[i][3] = p.getAge();
            }
            result[i][4] = p.getStartDate();
            result[i][5] = p.getPatientDept();
            if(p.getRoom() == 0) {
                result[i][6] = "";
            } else {
                result[i][6] = p.getRoom();
            }
            if(p.getBed() == 0) {
                result[i][7] = "";
            } else {
                result[i][7] = p.getBed();

            }
            result[i][8] = p.getState();
            result[i][9] = p.getDoctor();
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public String getDoctor() {
        if(doctor == null) return "";
        return doctor;
    }

    public String getState() {
        if(state == null) return "";
        return state;
    }

    public int getBed() {
        return bed;
    }

    public int getRoom() {
        return room;
    }

    public String getPatientDept() {
        if(patientDept == null) return "";
        return patientDept;
    }

    public int getAge() {
        return age;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getGender() {
        if(gender == null) return "";
        return gender;
    }

    public String getName() {
        if(name == null) return "";
        return name;
    }


}
