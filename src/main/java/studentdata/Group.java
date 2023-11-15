package studentdata;

public class Group {
    private String groupName;
    private String groupDescription;
    private int studentAmount;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(int studentAmount) {
        if(studentAmount<0) throw new IllegalArgumentException("Amount of students in a group cannot be a negative number.");
        this.studentAmount = studentAmount;
    }

    Group(){
        this.setGroupName("");
        this.setGroupDescription("");
        this.setStudentAmount(0);
    }
    Group(String groupName, String groupDescription, int studentAmount){
        this.setGroupName(groupName);
        this.setGroupDescription(groupDescription);
        this.setStudentAmount(studentAmount);
    }
}
