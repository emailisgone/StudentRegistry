package com.vu.studentregistry;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    @FXML private TextField name;
    @FXML private ComboBox<String> specialty;
    @FXML private Spinner<Integer> course;
    @FXML private Spinner<Integer> group;
    @FXML private Button addStudentButton;
    private StudentListener listener;
    public void setOnStudentCreatedListener(StudentListener listener) {
        this.listener = listener;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinners();
        initMajorCBox();

        addStudentButton.setOnAction(e -> checkCredentials());
    }

    private void initSpinners(){
        SpinnerValueFactory<Integer> courseFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4);
        course.setValueFactory(courseFactory);
        SpinnerValueFactory<Integer> groupFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        group.setValueFactory(groupFactory);
    }

    private void initMajorCBox(){
        specialty.setItems(FXCollections.observableArrayList("Computer Science", "Bioinformatics", "Software Engineering", "Data Science", "Cybersecurity"));
        specialty.getSelectionModel().selectFirst();
    }

    private void checkCredentials(){
        if(!name.getText().isBlank() && !name.getText().isEmpty()){
            Student newStudent = new Student(name.getText(), setTheMajor(), course.getValue(), group.getValue());
            if (listener != null) {
                listener.onStudentCreated(newStudent);
            }
        }
    }

    private Major setTheMajor(){
        return switch (specialty.getSelectionModel().getSelectedItem()) {
            case "Computer Science" -> Major.COMPUTER_SCIENCE;
            case "Bioinformatics" -> Major.BIOINFORMATICS;
            case "Software Engineering" -> Major.SOFTWARE_ENGINEERING;
            case "Data Science" -> Major.DATA_SCIENCE;
            case "Cybersecurity" -> Major.CYBERSECURITY;
            default -> null;
        };
    }
}
