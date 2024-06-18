package JefesDepto;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class VistaJefesDepto extends Application {
    private static ControladorJefesDepto controladorJefesDepto;

    @Override
    public void start(Stage jefesDepto) throws IOException {
        FXMLLoader mainLoader  = new FXMLLoader(VistaJefesDepto.class.getResource("jefesDepto.fxml"));
        Scene infoServicio = new Scene(mainLoader.load());
        jefesDepto.setTitle("Taller Mecánico | Jefe de Departamento");
        jefesDepto.setScene(infoServicio);
        jefesDepto.setResizable(false);
        jefesDepto.setMaximized(true);
        jefesDepto.show();

        controladorJefesDepto = mainLoader.getController();

        jefesDepto.setOnCloseRequest(event -> {
            try {
                controladorJefesDepto.cerrarConexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Método estático para recargar la escena principal

    public static void recargarEscenaPrincipal() {
        controladorJefesDepto.recargaEscenaPrincipal();
    }

    public static void muestraVentanaAvance() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader submoduleLoader = new FXMLLoader(VistaJefesDepto.class.getResource("avance.fxml"));
        Parent root = submoduleLoader.load();


        // Aquí obtenemos el controlador del nuevo FXML
        ControladorJefesDepto submoduleController = submoduleLoader.getController();

        Scene avanceServicio = new Scene(root);
        newStage.setTitle("Informar avance | Jefe de Departamento");
        newStage.setScene(avanceServicio);
        newStage.setResizable(false);
        newStage.show();
    }

}