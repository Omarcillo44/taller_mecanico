package Servicios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VistaVistaServ extends Application {

    private static ControladorVistaServ controladorVistaServ;

    @Override
    public void start(Stage vistaservicios) throws IOException {
        FXMLLoader mainLoader2  = new FXMLLoader(VistaVistaServ.class.getResource("vistaServ.fxml"));
        Scene vistServ = new Scene(mainLoader2.load());
        vistaservicios.setTitle("Taller Mecánico | Agendar servicio");
        vistaservicios.setScene(vistServ);
        vistaservicios.setResizable(false);
        vistaservicios.setMaximized(true);
        vistaservicios.show();

        controladorVistaServ = mainLoader2.getController();

        vistaservicios.setOnCloseRequest(event -> {
            try {
                controladorVistaServ.cerrarConexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void launchApp(String[] args) {
        launch(args);
    }
}
