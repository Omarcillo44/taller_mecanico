package CRUD;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class VistaCRUD extends Application {
    private static ControladorCRUD controladorCRUD;

    @Override
    public void start(Stage stage2) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(VistaCRUD.class.getResource("vista_crud.fxml"));
        Scene scene2 = new Scene(fxmlLoader.load());
        stage2.setTitle("CRUD Básico | PGSQL");
        stage2.setScene(scene2);
        stage2.show();

        controladorCRUD = fxmlLoader.getController();

        stage2.setOnCloseRequest(event -> {
            try {
                controladorCRUD.cerrarConexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    public static void mostrarAlertInfo(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public static void mostrarAlertWarning(String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Advertencia");
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

    @FXML
    public static boolean mostrarAlertConfirmacion(String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(texto);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }




    public static void launchApp(String[] args) {
        launch(args);
    }

}