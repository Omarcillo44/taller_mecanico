package JefesDepto;

import BD.ControladorBD;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static CRUD.VistaCRUD.mostrarAlertError;
import static CRUD.VistaCRUD.mostrarAlertInfo;

public class ControladorJefesDepto extends ControladorBD{
    private static List<String> listaResultados = new ArrayList<>();

    public void iniciar_CRUD(){
        VistaJefesDepto.launch();
    }

    /*Método para actualizar la lista de resultados local tras una consulta*/
    public static void actualizaResultados() {
        //Se obtiene la instancia del controlador
        ControladorBD controladorActual = ControladorBD.getInstance();
        //Se obtienen los resultados de la operación
        listaResultados = controladorActual.getContenedor();
    }


}
