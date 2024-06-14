package CRUD;
import BD.ControladorBD;
import BD.ModeloBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static CRUD.VistaCRUD.*;

public class ControladorCRUD extends ControladorBD {

    private ModeloCRUD operBD; // Instancia de ModeloBD (en realidad es ModeloCRUD)

    private static List<String> listaResultados = new ArrayList<>();

    @FXML
    private Label label_ID, label_Nombre;

    @FXML
    private TextField textf_Nombre, textf_ID;

    @FXML
    private Button btn_Alta, btn_Baja, btn_Cambio, btn_Consulta;

    String id, nombre, texto_Alerta;

    public void iniciar_CRUD() {
        VistaCRUD.launch();
    }

    /*Método para actualizar la lista de resultados local tras una consulta*/
    public static void actualizaResultados() {
        //Se obtiene la instancia del controlador
        ControladorBD controladorActual = ControladorBD.getInstance();
        //Se obtienen los resultados de la operación
        listaResultados = controladorActual.getContenedor();
    }

    public ControladorCRUD() throws SQLException {
        operBD = ModeloCRUD.getInstance(); // Obtener la instancia única del modelo
    }

    public void iniciarAplicacion(String[] args) {
        VistaCRUD.launchApp(args);
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexionBD() throws SQLException {
        if (operBD != null) {
            operBD.cierraConexion();
        }
    }
    public void onClickAlta(ActionEvent actionEvent) {
    }

    public void onClickBaja(ActionEvent actionEvent) {
    }

    public void onClickCambio(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickConsulta() {
        String id = textf_ID.getText(); // Obtener el ID desde el campo de texto

        try {
            int numero = Integer.parseInt(id); // Convertir el ID a entero

            // Realizar la consulta en el modelo y obtener resultados
            List<String> resultados = operBD.consultarPersonaPorID(id);

            if (!resultados.isEmpty()) {
                // Mostrar el resultado en la interfaz (suponiendo que resultados.get(1) es el nombre)
                String nombre = resultados.get(1);
                // Actualizar el campo de texto de nombre o cualquier otro componente de la interfaz
                // textf_Nombre.setText(nombre);
                mostrarAlertInfo("Consulta exitosa: Nombre encontrado -> " + nombre);
            } else {
                mostrarAlertError("No se encontró ninguna persona con el ID especificado.");
            }

        } catch (NumberFormatException e) {
            mostrarAlertError("El ID debe ser un número entero válido.");
        } catch (SQLException e) {
            mostrarAlertError("Error al consultar en la base de datos: " + e.getMessage());
        }
    }

    // Método para mostrar un cuadro de diálogo de información
    private void mostrarAlertInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar un cuadro de diálogo de error
    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

