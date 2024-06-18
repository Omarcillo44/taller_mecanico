package Servicios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VistaServicios extends Application{

    private static ControladorServicios controladorServicios;

    @Override
    public void start(Stage servicios) throws IOException {
        FXMLLoader mainLoader  = new FXMLLoader(VistaServicios.class.getResource("agendarServicios.fxml"));
        Scene altaServicio = new Scene(mainLoader.load());
        servicios.setTitle("Taller Mecánico | Agendar servicio");
        servicios.setScene(altaServicio);
        servicios.setResizable(false);
        servicios.setMaximized(true);
        servicios.show();

        controladorServicios = mainLoader.getController();

        servicios.setOnCloseRequest(event -> {
            try {
                controladorServicios.cerrarConexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

}
