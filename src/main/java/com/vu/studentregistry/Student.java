package com.vu.studentregistry;

import java.util.ArrayList;
import java.util.Collections;

public class Student {
    private String fullName;
    private Major major;
    private int groupNr;
    private int courseNr;
    private final ArrayList<ArrayList<Boolean>> attendance;

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

        attendance = new ArrayList<>() {{
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.January), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.February), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.March), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.April), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.May), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.June), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.July), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.August), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.September), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.October), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.November), false)));
            add(new ArrayList<>(Collections.nCopies(Date.daysInMonths.get(Month.December), false)));
        }};
    }

    public int getCourseNr() {
        return courseNr;
    }

    public void setCourseNr(int courseNr) {
        this.courseNr = courseNr;
    }

    public ArrayList<Boolean> getAttendance(Month month) {
        return attendance.get(month.ordinal());
    }

    public void setAttendance(Month month, int day, boolean value) {
        var days = attendance.get(month.ordinal());
        --day;

        if (day >= days.size()) {
            return;
        }

        days.set(day, value);
    }
}
