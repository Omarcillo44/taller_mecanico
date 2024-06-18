package Servicios;

import BD.ControladorBD;
import JefesDepto.ModeloJefesDepto;
import JefesDepto.VistaJefesDepto;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static CRUD.VistaCRUD.*;

public class ControladorServicios extends ControladorBD{

    String RFC; // variable paraguardar el rfc del cliente
    int numTipoServ;

    @FXML private TextField txtRfcCli, txtPlacaAuto;
    @FXML private Button btnBuscarCli, btnBuscarAuto, btnAgendar;
    @FXML private Label txtCorreoCli, txtNomCli, txtTelCli, txtEstadoAuto, txtModeloAuto, txtMarcaAuto, txtAñoAuto, txtColorAuto;
    @FXML private TextArea descGeneral, descMeca, descHoja, descPint, descElect;
    @FXML private RadioButton tiposServ, servRepa, servMante;
    @FXML private CheckBox checkMeca, checkHoja, checkPint, checkElect;
    @FXML private DatePicker fechaTram, fechaIngr, fechaEntr;
    @FXML private ToggleGroup tipoServ;
    @FXML private ComboBox<String> comboAuto;

    private static List<String> listaResultados = new ArrayList<>();
    private ModeloServicios operBD; // Instancia del Modelo
    //Flagas que controlan qué elementos se inicializarán
    private static boolean inializacionPrincipal = false, inicializacionReporte = false, confirmacion;

    //Constructor
    public ControladorServicios() throws SQLException {
        operBD = ModeloServicios.getInstance(); // Obtener la instancia única del modelo
    }

    // Método para iniciar la vista de el alta de servicios
    public static void iniciaVistaServicios(String[] args) {
        VistaServicios.launchApp(args);
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexionBD() throws SQLException {
        if (operBD != null) {
            operBD.cierraConexion();
        }
    }

    // para inicializar el combobox y agregar su listener
    @FXML
    public void initialize(){
        comboAuto.valueProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("seleccionado: " + newValue);
            try {
                if(comboAuto.getValue() != null)
                    buscarVeh_click(newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // metodo para buscar la informacion del cliente seleccionado con la RFC
    @FXML
    private void buscarCli_click() throws IOException {
        RFC = "";
        try {
            RFC = txtRfcCli.getText();
            limpiarCamposAgendar();
            List<String> resultados = operBD.buscarCli(RFC); // realiza la consulta por el rfc

            if (!resultados.isEmpty()) {
                // reasigna el nombre a los labels con la informacion del cliente
                String nombrecomp = resultados.get(2)+" "+resultados.get(3)+" "+resultados.get(4);
                String correocomp = resultados.get(0)+"@"+resultados.get(1);
                txtCorreoCli.setText(correocomp);
                txtNomCli.setText(nombrecomp);
                txtTelCli.setText(resultados.get(5));
                txtRfcCli.setText(resultados.get(6));

                consultaVehPlacas();
            } else {
                mostrarAlertError("No se encontró ninguna persona con el RFC especificado.");
            }
        } catch (SQLException e) {
            mostrarAlertError("Error al encontrar al usuario: " + e.getMessage());
        }
    }

    // metodo para buscar y crear el combobox con los vehiculos del cliente
    private void consultaVehPlacas() throws IOException {
        try {
            List<String> resultados = operBD.consultaPlacas(RFC);

            if (!resultados.isEmpty()) {
                comboAuto.getItems().clear();
                for(int i = 0; i < resultados.size(); i++){
                    comboAuto.getItems().add(resultados.get(i));
                }
            } else {
                mostrarAlertError("No se encontró ningun vehiculo");
            }
        } catch (SQLException e) {
            mostrarAlertError("Error al encontrar el vehiculo: " + e.getMessage());
        }
    }

    // metodo para buscar la informacion del vehiculo seleccionado en el combobox
    @FXML
    private void buscarVeh_click(String newValue) throws IOException {
        try {
            List<String> resultados = operBD.buscarVeh(newValue); // realiza la consulta por el rfc

            if (!resultados.isEmpty()) {
                // reasigna el nombre a los labels con la informacion del cliente
                //txtPlacaAuto.setText(resultados.get(0));
                txtEstadoAuto.setText(resultados.get(1));
                txtModeloAuto.setText(resultados.get(2));
                txtMarcaAuto.setText(resultados.get(3));
                txtAñoAuto.setText(resultados.get(4));
                txtColorAuto.setText(resultados.get(5));
            } else {
                mostrarAlertError("No se encontró ningun vehiculo con la placa especificada.");
            }
        } catch (SQLException e) {
            mostrarAlertError("Error al encontrar el vehiculo: " + e.getMessage());
        }
    }

    // metodo para verificar si esta activado un checkbo (se va a realizar un servicio o no)
    @FXML
    private void revisarServ_click() throws IOException{
        if(checkMeca.isSelected()) //chechbox trabajos mecanicos
            descMeca.setDisable(false);
        else{
            descMeca.setDisable(true);
            descMeca.setText("");
        }
        if(checkPint.isSelected()) //chechbox trabajos pintura
            descPint.setDisable(false);
        else{
            descPint.setDisable(true);
            descPint.setText("");
        }
        if(checkHoja.isSelected()) //chechbox trabajos hojalateria
            descHoja.setDisable(false);
        else{
            descHoja.setDisable(true);
            descHoja.setText("");
        }
        if(checkElect.isSelected()) //chechbox trabajos electricos
            descElect.setDisable(false);
        else{
            descElect.setDisable(true);
            descElect.setText("");
        }
    }

    // metodo para saber que tipo de servicio se esta selecionando reparacion o mantenimiento
    @FXML
    private void obtenerServ() throws IOException{
        if(servRepa.isSelected()) // servicio de tipo reparacion
            numTipoServ = 1;
        else if (servMante.isSelected()) // servicio de tipo mantenimiento
            numTipoServ = 2;
    }

    // metodo para dar de alta el servicio :V
    @FXML
    private void darAltaServ_click() throws IOException{
        // validar que los datos obligatorios esten llenos
        if(RFC==null || comboAuto.getValue()==null || numTipoServ==0 || fechaTram.getValue()==null || fechaEntr.getValue()==null || fechaIngr.getValue()==null || descGeneral.getText().isEmpty()){
            mostrarAlertError("Introduce todos los datos necesarios");
        }
        else{
            String insertRfc = RFC;
            String insertPlaca = comboAuto.getValue();
            String insertTipo = String.valueOf(numTipoServ);
            String insertFechT = String.valueOf(fechaTram.getValue());
            String insertFechE = String.valueOf(fechaEntr.getValue());
            String insertFechI = String.valueOf(fechaIngr.getValue());
            String insertDesG = descGeneral.getText();
            String insertDesM = "";
            String insertDesH = "";
            String insertDesP = "";
            String insertDesE = "";
            // Ajustar los datos para ingresar null si no hay texto en las descripciones
            if(descMeca.getText().isEmpty()) // verificar si la descipcion mecanico esta vacio
                insertDesM = "null";
            else
                insertDesM = "'" + descMeca.getText() + "'";
            if(descHoja.getText().isEmpty()) // verificar si la descipcion  hojalateria esta vacio
                insertDesH = "null";
            else
                insertDesH = "'" + descHoja.getText() + "'";
            if(descPint.getText().isEmpty()) // verificar si la descipcion pintura esta vacio
                insertDesP = "null";
            else
                insertDesP = "'" + descPint.getText() + "'";
            if(descElect.getText().isEmpty()) // verificar si la descipcion electrico esta vacio
                insertDesE = "null";
            else
                insertDesE = "'" + descElect.getText() + "'";

            introducirDatosAlta(insertRfc,insertPlaca,insertTipo,insertFechT,insertFechE,insertFechI,insertDesG,insertDesM,insertDesH,insertDesP,insertDesE);
        }
    }

    // metodo para insertar los datos en la base de datos
    @FXML
    private void introducirDatosAlta(String Rfc,String Placa,String Tipo,String FechT,String FechE,String FechI,String DesG,String DesM,String DesH,String DesP,String DesE) throws IOException {
        try {
            operBD.insertarDatosAlta(Rfc,Placa,Tipo,FechT,FechE,FechI,DesG,DesM,DesH,DesP,DesE); // realiza la consulta por el rfc
            mostrarAlertInfo("Se ha dado de alta el servicio");
            limpiarCamposAgendar();
        } catch (SQLException e) {
            mostrarAlertError("Error al agendar el servicio: " + e.getMessage());
        }
    }

    // metodo para limpiar todos los campos
    private void limpiarCamposAgendar(){
        txtRfcCli.setText("");
        txtCorreoCli.setText("");
        txtNomCli.setText("");
        txtTelCli.setText("");

        txtEstadoAuto.setText("");
        txtModeloAuto.setText("");
        txtMarcaAuto.setText("");
        txtAñoAuto.setText("");
        txtColorAuto.setText("");
        comboAuto.getItems().clear();

        servRepa.setSelected(false);
        servMante.setSelected(false);
        fechaTram.setValue(null);
        fechaIngr.setValue(null);

        descGeneral.setText("");
        checkMeca.setSelected(false);
        descMeca.setText("");
        checkHoja.setSelected(false);
        descHoja.setText("");
        checkPint.setSelected(false);
        descPint.setText("");
        checkElect.setSelected(false);
        descElect.setText("");
    }
}
