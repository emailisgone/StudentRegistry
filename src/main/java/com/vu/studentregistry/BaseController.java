package com.vu.studentregistry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> studentNameColumn;
    @FXML private TableColumn<Student, Major> studentMajorColumn;
    @FXML private TableColumn<Student, Integer> studentCourseNrColumn;
    @FXML private TableColumn<Student, Integer> studentGroupNrColumn;
    @FXML private Button addStudentButton;
    @FXML private Button removeStudentButton;
    @FXML private Button saveStudentListButton;
    @FXML private Button loadStudentListButton;
    @FXML private ComboBox<Major> majorFilterComboBox;
    @FXML private Spinner<Integer> groupNrFilterSpinner;
    @FXML private Spinner<Integer> courseNrFilterSpinner;
    @FXML private Button resetFiltersButton;
    @FXML private Button editStudentButton;
    @FXML private Button filterButton;
    @FXML private CheckBox noCourseCheck;
    @FXML private CheckBox noGroupCheck;
    @FXML private CheckBox noMajorCheck;
    @FXML private Button attManageButton;

    private final ObservableList<Student> studentData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
        initMajorCBox();
        initSpinners();
        initOnActions();
    }

    private void initTableView(){
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        studentMajorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        studentGroupNrColumn.setCellValueFactory(new PropertyValueFactory<>("groupNr"));
        studentCourseNrColumn.setCellValueFactory(new PropertyValueFactory<>("courseNr"));
    }

    private void initMajorCBox(){
        majorFilterComboBox.setItems(FXCollections.observableArrayList(Major.COMPUTER_SCIENCE, Major.BIOINFORMATICS, Major.SOFTWARE_ENGINEERING, Major.DATA_SCIENCE, Major.CYBERSECURITY));
        majorFilterComboBox.getSelectionModel().selectFirst();
    }

    private void initSpinners(){
        SpinnerValueFactory<Integer> courseFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4);
        courseNrFilterSpinner.setValueFactory(courseFactory);
        SpinnerValueFactory<Integer> groupFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        groupNrFilterSpinner.setValueFactory(groupFactory);
    }

    private void initOnActions(){
        addStudentButton.setOnAction(e -> {
            try {
                addStudentWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        removeStudentButton.setOnAction(e -> {
            Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                studentData.remove(selectedStudent);
                updateTableView();
            } else {
                CustomError.customError("Please select a student from the table.");
            }
        });

        editStudentButton.setOnAction(e -> {
            Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                try {
                    editStudentWindow(selectedStudent);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                CustomError.customError("Please select a student from the table.");
            }
        });

        loadStudentListButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                FileHandler fileHandler = getFileHandler(file);
                if (fileHandler != null) {
                    try {
                        List<Student> students = fileHandler.load(file);
                        studentData.setAll(students);
                        updateTableView();
                    } catch (IOException ex) {
                        CustomError.customError("Error loading file.");
                    }
                }
            }
        });

        saveStudentListButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            FileChooser.ExtensionFilter extFilterXLSX = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().addAll(extFilterCSV, extFilterXLSX);
            fileChooser.setSelectedExtensionFilter(extFilterCSV);

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileHandler fileHandler = getFileHandler(file);
                if (fileHandler != null) {
                    try {
                        fileHandler.save(new ArrayList<>(studentTableView.getItems()), file);
                    } catch (IOException ex) {
                        CustomError.customError("Error saving file.");
                    }
                }
            }
        });

        filterButton.setOnAction(e -> {
            ObservableList<Student> filteredData = FXCollections.observableArrayList();
            for (Student student : studentData) {
                Major major = noMajorCheck.isSelected() ? null : majorFilterComboBox.getValue();
                Integer groupNr = noGroupCheck.isSelected() ? null : groupNrFilterSpinner.getValue();
                Integer courseNr = noCourseCheck.isSelected() ? null : courseNrFilterSpinner.getValue();
                if ((major == null || student.getMajor() == major) &&
                        (groupNr == null || student.getGroupNr() == groupNr) &&
                        (courseNr == null || student.getCourseNr() == courseNr)) {
                    filteredData.add(student);
                }
            }
            studentTableView.setItems(filteredData);
        });

        resetFiltersButton.setOnAction(e -> {
            studentTableView.setItems(studentData);
        });

        attManageButton.setOnAction(e -> {
            try {
                manageAttendance();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private FileHandler getFileHandler(File file) {
        String extension = getFileExtension(file);
        if ("csv".equals(extension)) {
            return new CSVFileHandler();
        } else if ("xlsx".equals(extension)) {
            return new ExcelFileHandler();
        } else {
            CustomError.customError("Unsupported file type.");
            return null;
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private void addStudentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addStudent.fxml"));
        Parent root = loader.load();
        AddStudentController controller = loader.getController();
        controller.setOnStudentCreatedListener(this::addStudent);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Student Registration");
        stage.setResizable(false);
        stage.showAndWait();
    }
    private void editStudentWindow(Student student) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyStudent.fxml"));
        Parent root = loader.load();
        ModifyStudentController controller = loader.getController();
        controller.setOnStudentCreatedListener(this::updateStudent);
        controller.setStudent(student);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Student Modification");
        stage.setResizable(false);
        stage.showAndWait();
    }

    private void updateStudent(Student updatedStudent){
        for (int i = 0; i < studentData.size(); i++) {
            Student student = studentData.get(i);
            if (student.getFullName().equals(updatedStudent.getFullName()) &&
                    student.getMajor() == updatedStudent.getMajor() &&
                    student.getCourseNr() == updatedStudent.getCourseNr() &&
                    student.getGroupNr() == updatedStudent.getGroupNr()) {
                studentData.set(i, updatedStudent);
                break;
            }
        }
        updateTableView();
    }

    private void addStudent(Student student){
        studentData.add(student);
        updateTableView();
    }

    private void updateTableView(){
        studentTableView.setItems(studentData);
    }

    private void manageAttendance() throws IOException{
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("attWindow.fxml"));
            Parent root = loader.load();
            StudentAttendanceController controller = loader.getController();
            controller.setStudent(selectedStudent);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Attendance");
            stage.setResizable(false);
            stage.showAndWait();
        } else {
            CustomError.customError("Please select a student from the table.");
        }
    }
}