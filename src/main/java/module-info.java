module com.vu.studentregistry {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vu.studentregistry to javafx.fxml;
    exports com.vu.studentregistry;
}