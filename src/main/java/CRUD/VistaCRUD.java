package CRUD;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class VistaCRUD extends Application {
    @Override
    public void start(Stage stage2) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VistaCRUD.class.getResource("vista_crud.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load());
        stage2.setTitle("CRUD Básico | PGSQL");
        stage2.setScene(scene2);
        stage2.show();
    }

    @FXML
    public static void mostrarAlertInfo(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(texto);
        alert.showAndWait();
    }

    @FXML
    public static void mostrarAlertError(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(texto);
        alert.showAndWait();
    }

}