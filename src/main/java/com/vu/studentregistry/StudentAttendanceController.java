package com.vu.studentregistry;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;

public class StudentAttendanceController implements Initializable {
    @FXML private TableView<Student> studentAttendance;
    @FXML private TableColumn<Student, String> studentName;
    @FXML private ChoiceBox<Month> month;
    @FXML private Button saveToPdfButton;
    private ObservableList<Student> students;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        initMonthBox();

        studentName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        students = FXCollections.observableArrayList();
        studentAttendance.setItems(students);

        month.setOnAction(e -> {
            updateTable();
        });

        studentAttendance.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                openAttendanceWindow(studentAttendance.getSelectionModel().getSelectedItem());
            }
        });

        updateTable();

        saveToPdfButton.setOnAction(e -> saveToPdf());
    }

    void updateTable() {
        studentAttendance.getColumns().clear();
        studentAttendance.getColumns().add(studentName);

        int days = Date.daysInMonths.get(month.getValue());

        for (int i = 0; i < days; ++i) {
            TableColumn<Student, String> tableColumn = createMonthColumns(i);
            studentAttendance.getColumns().add(tableColumn);
        }
    }

    private TableColumn<Student, String> createMonthColumns(int i) {
        final int day = i;
        TableColumn<Student, String> tableColumn = new TableColumn<>(Integer.toString(day + 1));

        tableColumn.setCellValueFactory(studentStringCellDataFeatures -> {
            boolean value = studentStringCellDataFeatures.getValue().getAttendance(month.getValue()).get(day);

            return new SimpleStringProperty(value ? "*" : "");
        });

        tableColumn.setPrefWidth(30);
        return tableColumn;
    }

    public void setStudent(Student student){
        if (this.students == null) {
            this.students = FXCollections.observableArrayList();
        }
        this.students.add(student);
    }

    private void initMonthBox(){
        month.getItems().addAll(Month.values());
        month.setValue(Month.January);
    }

    private void openAttendanceWindow(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyAtt.fxml"));
            Parent root = loader.load();

            ModifyAttendanceController attendanceController = loader.getController();
            attendanceController.setStudent(student);
            attendanceController.setMonth(month.getValue());
            attendanceController.setMainController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void saveToPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                PdfPTable table = new PdfPTable(studentAttendance.getColumns().size());
                for (TableColumn<Student, ?> column : studentAttendance.getColumns()) {
                    table.addCell(column.getText());
                }
                for (Student student : studentAttendance.getItems()) {
                    table.addCell(student.getFullName());
                    ArrayList<Boolean> attendance = student.getAttendance(month.getValue());
                    for (Boolean present : attendance) {
                        table.addCell(present ? "*" : "");
                    }
                }
                document.add(table);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
