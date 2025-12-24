module com.example.gestionmatch {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires jbcrypt;
    requires mysql.connector.j;

    opens com.example.gestionmatch to javafx.fxml;
    opens com.example.gestionmatch.controller to javafx.fxml;

    exports com.example.gestionmatch;
    exports com.example.gestionmatch.controller;
    exports com.example.gestionmatch.database;
    exports com.example.gestionmatch.model;
    exports com.example.gestionmatch.dao;
    exports com.example.gestionmatch.services;
    opens com.example.gestionmatch.services to javafx.fxml;
}
