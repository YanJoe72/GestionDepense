module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;



    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;
    exports com.example.javafx.models;
    opens com.example.javafx.models to javafx.fxml;
    exports com.example.javafx.controllers;
    opens com.example.javafx.controllers to javafx.fxml;
    exports com.example.javafx.DAO to javafx.fxml;
    opens com.example.javafx.DAO to javafx.fxml;

}