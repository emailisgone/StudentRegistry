package com.vu.studentregistry;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAttendanceController implements Initializable {
    @FXML private Spinner<Integer> attSpinner;
    @FXML private Button setPlusButton;
    @FXML private Button erasePlusButton;
    private Student student;
    private Month month;
    private StudentAttendanceController mainController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPlusButton.setOnAction(e -> {
            int day = attSpinner.getValue();
            student.getAttendance(month).set(day - 1, true);
            mainController.updateTable();
        });

        erasePlusButton.setOnAction(e -> {
            int day = attSpinner.getValue();
            student.getAttendance(month).set(day - 1, false);
            mainController.updateTable();
        });
    }

    public void setStudent(Student student){
        this.student = student;
    }

    public void setMonth(Month month){
        this.month = month;
        attSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Date.daysInMonths.get(month)));
    }

    public void setMainController(StudentAttendanceController mainController){
        this.mainController = mainController;
    }
}

