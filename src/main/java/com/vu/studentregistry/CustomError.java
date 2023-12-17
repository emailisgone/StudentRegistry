package com.vu.studentregistry;

import javafx.scene.control.Alert;

public class CustomError {
    public static void customError(String errorMsg){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error!");
        errorAlert.setContentText(errorMsg);
        errorAlert.showAndWait();
    }
}
