package studentdata;

public class Student {
    private String fullName;
    private Major major;
    private int groupNr;
    private int courseNr;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public int getGroupNr() {
        return groupNr;
    }

    public void setGroupNr(int groupNr) {
        this.groupNr = groupNr;
    }

    Student(){
        this.setFullName("");
        this.setGroupNr(0);
        this.setMajor(null);
    }

    Student(String fullName, int groupNr, Major major){
        this.setFullName(fullName);
        this.setGroupNr(groupNr);
        this.setMajor(major);
    }

    public int getCourseNr() {
        return courseNr;
    }

    public void setCourseNr(int courseNr) {
        this.courseNr = courseNr;
    }
}
