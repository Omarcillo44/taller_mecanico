module com.example.taller_mecanico {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports CRUD;
    opens CRUD to javafx.fxml;

    exports JefesDepto;
    opens JefesDepto to javafx.fxml;

    exports Acceso;
    opens Acceso to javafx.fxml;

}