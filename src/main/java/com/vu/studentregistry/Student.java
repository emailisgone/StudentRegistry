package com.vu.studentregistry;

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

    public Student(String fullName, Major major, int courseNr, int groupNr){
        this.setFullName(fullName);
        this.setCourseNr(courseNr);
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
