package Acceso;

import JefesDepto.VistaJefesDepto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VistaAcceso extends Application {

    private static ControlAcceso controlAcceso;

    @Override
    public void start(Stage acceso) throws IOException {
        FXMLLoader loader  = new FXMLLoader(VistaAcceso.class.getResource("Acceso.fxml"));
        Scene accesoEmpleados = new Scene(loader.load());
        acceso.setTitle("Taller Mecánico | Acceso");
        acceso.setScene(accesoEmpleados);
        acceso.setResizable(false);
        acceso.setMaximized(true);
        acceso.show();

        controlAcceso = loader.getController();

        acceso.setOnCloseRequest(event -> {
            try {
                controlAcceso.cerrarConexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

}
