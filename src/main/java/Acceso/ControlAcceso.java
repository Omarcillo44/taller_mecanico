package Acceso;

import BD.ControladorBD;
import JefesDepto.ControladorJefesDepto;
import JefesDepto.VistaJefesDepto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static CRUD.VistaCRUD.*;
import static JefesDepto.ControladorJefesDepto.iniciaVistaJefesDepto;

public class ControlAcceso extends ControladorBD implements Initializable {

    private static List<String> listaResultados = new ArrayList<>();

    @FXML
    private Button btnAcceder;

    @FXML
    private PasswordField txtfPassword;

    @FXML
    private TextField txtfUsuario;

    private ModeloAcceso operBD; // Instancia del Modelo
    //Método que inicializa la interfaz gráfica
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("inicializado");
    }
    //Constructor que obtendrá la instancia de la base de datos
    public ControlAcceso() throws SQLException {
        System.out.println("Instanciado ControlAcceso");
        operBD = ModeloAcceso.getInstance(); // Obtener la instancia única del modelo
    }

    //Método que permite iniciar el módulo de acceso
    public static void iniciaVistaAcceso(String[] args) {
        VistaAcceso.launchApp(args);
    }

    public void cerrarConexionBD() throws SQLException {
        if (operBD != null) {
            operBD.cierraConexion();
        }
    }

    public void onClickAcceder(ActionEvent actionEvent) {
        String usuario = txtfUsuario.getText().trim();
        String password = txtfPassword.getText().trim();

        try {
            listaResultados = operBD.validaAcceso(usuario, password);

            if (listaResultados == null || listaResultados.isEmpty()) {
                mostrarAlertError("Datos de acceso incorrectos");
                return; // Salir del método si listaResultados es nula o vacía
            }

            // Verificar si el primer elemento de listaResultados es "null"
            String primerResultado = listaResultados.getFirst(); // Obtener el primer elemento
            if (primerResultado == null || primerResultado.equals("null") || primerResultado.isEmpty()) {
                mostrarAlertError("Datos de acceso incorrectos");
            } else {
                String rfc = listaResultados.get(0); // Asumiendo que el RFC se devuelve como primer elemento de la lista
                mostrarAlertInfo("Acceso correcto");
                //Cerrar ventana actual
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
                // Iniciar la vista de Jefes de Departamento
                ControladorJefesDepto.iniciaVistaJefesDepto(new String[0], rfc);
            }

        } catch (SQLException e) {
            mostrarAlertError("Error al intentar acceder: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            mostrarAlertError("El formato de los datos de acceso no es válido");
        }
    }

}
