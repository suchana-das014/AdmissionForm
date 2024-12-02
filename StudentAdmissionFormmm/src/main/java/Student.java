import java.io.Serializable;

public class Student implements Serializable {
    private String name, gender, dob, bloodGroup, course, semester, year, presentAddress, permanentAddress;

    public Student(String name, String gender, String dob, String bloodGroup, String course, String semester, String year, String presentAddress, String permanentAddress) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
        this.course = course;
        this.semester = semester;
        this.year = year;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
    }

    public String getName() { return name; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }
    public String getBloodGroup() { return bloodGroup; }
    public String getCourse() { return course; }
    public String getSemester() { return semester; }
    public String getYear() { return year; }
    public String getPresentAddress() { return presentAddress; }
    public String getPermanentAddress() { return permanentAddress; }
}