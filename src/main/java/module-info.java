module com.vu.studentregistry {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.csv;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.vu.studentregistry to javafx.fxml;
    exports com.vu.studentregistry;
}