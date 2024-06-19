package Servicios;

import BD.ControladorBD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static CRUD.VistaCRUD.*;

public class ControladorVistaServ extends ControladorBD implements Initializable{

    String servicioActual;

    @FXML
    private ListView<String> listaServicios;
    @FXML private TextField txtfFechaIng,tipoServ,modeloV,marcaV,nomCli,teleCli;
    @FXML private Button btnBuscarCli, btnBuscarAuto, btnAgendar;
    @FXML private TextArea descGen,descMec,descHoj,descPin,descEle;


    private static List<String> listaResultados = new ArrayList<>();
    private ModeloServicios operBD; // Instancia del Modelo
    //Flagas que controlan qué elementos se inicializarán
    private static boolean inializacionPrincipal = false, inicializacionReporte = false, confirmacion;

    //Constructor
    public ControladorVistaServ() throws SQLException {
        operBD = ModeloServicios.getInstance(); // Obtener la instancia única del modelo
    }

    // Método para iniciar la vista de el alta de servicios
    public static void iniciaVistaVistaServ(String[] args) {
        VistaVistaServ.launchApp(args);
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexionBD() throws SQLException {
        if (operBD != null) {
            operBD.cierraConexion();
        }
    }

    // metodo para colocar los datos del servicio seleccionado
    private void colocarResServ(String servicioAct) throws IOException {
        try {
            String placa, fecha, tipo;
            String[] partes = servicioAct.split("-");
            placa = partes[0] + "-" + partes[1];
            fecha = partes[2] + "-" + partes[3] + "-" + partes[4];
            tipo = partes[5];
            List<String> resultado = operBD.consultaDatosServ(placa,fecha,tipo);
            if (!resultado.isEmpty()) {
                tipoServ.setText(resultado.get(0));
                descGen.setText(resultado.get(1));
                descMec.setText(resultado.get(2));
                descHoj.setText(resultado.get(3));
                descPin.setText(resultado.get(4));
                descEle.setText(resultado.get(5));
                modeloV.setText(resultado.get(6));
                marcaV.setText(resultado.get(7));
                nomCli.setText(resultado.get(8) + " " + resultado.get(9));
                teleCli.setText(resultado.get(10));
            } else {
                mostrarAlertError("No se encontró ningun servicio");
            }
        } catch (SQLException e) {
            mostrarAlertError("Error al encontrar los servicios: " + e.getMessage());
        }
    }

    // metodo para poner en el listview todos los servicios disponibles
    private void consultaIdServicios() throws IOException {
        try {
            List<String> resultado = operBD.consultaServicios();
            if (!resultado.isEmpty()) {
                int count = 0;
                String txtres = "";
                for(int i = 0; count < (resultado.size() / 3) ; i += 3){
                    txtres = resultado.get(i) + "-" + resultado.get(i+1) + "-" + resultado.get(i+2);
                    listaServicios.getItems().add(txtres);
                    count++;
                }
            } else {
                mostrarAlertError("No se encontró ningun servicio");
            }
        } catch (SQLException e) {
            mostrarAlertError("Error al encontrar los servicios: " + e.getMessage());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            consultaIdServicios();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listaServicios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                servicioActual = listaServicios.getSelectionModel().getSelectedItem();
                txtfFechaIng.setText(servicioActual);
                try {
                    colocarResServ(servicioActual);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
