package CRUD;
import BD.ControladorBD;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static CRUD.VistaCRUD.*;

public class ControladorCRUD extends ControladorBD{
    private static List<String> listaResultados = new ArrayList<>();

    @FXML
    private Label label_ID, label_Nombre;

    @FXML
    private TextField textf_Nombre, textf_ID;

    @FXML
    private Button btn_Alta, btn_Baja, btn_Cambio, btn_Consulta;

    String id,nombre, texto_Alerta;

    public void iniciar_CRUD(){
        VistaCRUD.launch();
    }

    /*Método para actualizar la lista de resultados local tras una consulta*/
    public static void actualizaResultados() {
        //Se obtiene la instancia del controlador
        ControladorBD controladorActual = ControladorBD.getInstance();
        //Se obtienen los resultados de la operación
        listaResultados = controladorActual.getContenedor();
    }

    /*Métodos para controlar la interfaz*/
    @FXML
    /*Botón de alta*/
    public void onClickAlta() throws SQLException {

        nombre = textf_Nombre.getText(); //Se obtiene el nombre
        id = textf_ID.getText(); //Se obtiene el ID

        // Validar que el nombre no esté vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlertError("No puede haber campos vacíos");
            return;
        }

        // Validar el ID
        try {
            Integer.parseInt(id);
            // El valor es un número entero, realizar la operación
            realizaOperacion("INSERT INTO gente(id, nombre) VALUES (" + id + ", '" + nombre + "');", "alta");
        } catch (NumberFormatException | SQLException e) {
            // Mostrar un mensaje de error si el ID no es un número
            mostrarAlertError("Revise los datos");
        }
    }

    /*Botón de consulta*/
    public void onClickConsulta() throws SQLException {

        id = textf_ID.getText(); //Se obtiene el ID

        try {
            Integer.parseInt(id);
            // El valor es un número entero, realizar la operación
            realizaOperacion("Select * from gente where id = " + id,"consulta");
            actualizaResultados(); //La lista local de resultados se actualiza
            textf_Nombre.setText(listaResultados.get(1));
        } catch (NumberFormatException | SQLException e) {
            // Mostrar un mensaje de error si el ID no es un número
            mostrarAlertError("Revise los datos");
        }
    }

    /*Botón de baja*/
    public void onClickBaja() {

        id = textf_ID.getText(); //Se obtiene el ID

        // Validar el ID
        try {
            Integer.parseInt(id);
            // El valor es un número entero, realizar la operación
            realizaOperacion("delete from gente where id = " + id,"baja");
        } catch (NumberFormatException | SQLException e) {
            // Mostrar un mensaje de error si el ID no es un número
            mostrarAlertError("Revise los datos");
        }

        mostrarAlertInfo("Se dio de baja a la persona con ID = " + id);
    }

    /*Botón de cambio*/
    public void onClickCambio() throws SQLException {

        nombre = textf_Nombre.getText(); //Se obtiene el nombre
        id = textf_ID.getText(); //Se obtiene el ID

        // Validar que el nombre no esté vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlertError("No puede haber campos vacíos");
            return;
        }

        // Validar el ID
        try {
            Integer.parseInt(id);
            // El valor es un número entero, realizar la operación
            realizaOperacion("UPDATE gente SET nombre = '" + nombre + "' WHERE id = " + id, "cambio");
        } catch (NumberFormatException | SQLException e) {
            // Mostrar un mensaje de error si el ID no es un número
            mostrarAlertError("Revise los datos");
        }


    }

}
