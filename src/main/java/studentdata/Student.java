package studentdata;

public class Student {
    private String fullName;
    private int age;
    private Gender gender;
    private String major;
    private Group group;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age<0) throw new IllegalArgumentException("Student's age cannot be a negative number.");
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    Student(){
        this.setFullName("");
        this.setAge(0);
        this.setGender(Gender.PLACEHOLDER);
        this.setGroup(new Group());
        this.setMajor("");
    }

    Student(String fullName, int age, Gender gender, Group group, String major){
        this.setFullName(fullName);
        this.setAge(age);
        this.setGroup(group);
        this.setGender(gender);
        this.setMajor(major);
    }
}
