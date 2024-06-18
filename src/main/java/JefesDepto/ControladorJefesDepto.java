package JefesDepto;
import javafx.event.ActionEvent;
import Acceso.Main;
import Acceso.VistaAcceso;
import BD.ControladorBD;
import BD.ModeloBD;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import static JefesDepto.ModeloJefesDepto.cargarImagen;

public class ControladorJefesDepto extends ControladorBD implements Initializable {

    @FXML private ImageView imgDer, imgFrente, imgIzq, imgPosterior;
    @FXML private TextArea txtaDescEsp, txtaDescGen, txtaDescVeh, txtaDescAv;
    @FXML private TextField txtfAnno, txtfColor, txtfFechaEnt, txtfFechaIng, txtfMarca, txtfModelo, txtfServicio1, txtfServicio2;
    @FXML private Button btnCancelar, btnConfirmar;
    @FXML private ProgressBar progBar;
    @FXML private Slider progSlider;
    @FXML private Label labelNuevoAv, labelAvance;

    //Variables para manejar los valores de la interfaz
    static String rfcPer, nuevoPorcAv = "0", imprevistoAv, placaVeh, fechaTramite,tipoServ,fechaIngreso, fechaEntrega,descGral,descEsp,marcaVeh,modeloVeh,annoVeh, colorVeh,foto1,foto2,foto3,foto4,descEdoVeh,porcAv;
   //Variables para poder operar los valores de ciertos elementos de la interfaz
    int porcAvInt, nuevoPorcAvInt;
    //Almacenará los resultados de las consultas
    private static List<String> listaResultados = new ArrayList<>();
    private ModeloJefesDepto operBD; // Instancia del Modelo
    //Flagas que controlan qué elementos se inicializarán
    private static boolean inializacionPrincipal = false, inicializacionReporte = false, confirmacion;

    //Constructor
    public ControladorJefesDepto() throws SQLException {
        operBD = ModeloJefesDepto.getInstance(); // Obtener la instancia única del modelo
    }

    //Método para inicializar la interfaz
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Se reutiliza esta clase para todos los stages del módulo
        ,por tanto se debe cuidar que se inicialice una sóla vez*/

        if (!inializacionPrincipal) {
            System.out.println("Inicializando");
            try {
                colocaDatosServicio();
            } catch (SQLException e) {
                mostrarAlertError("Error al recuperar datos: " + e.getMessage());
            }
            inializacionPrincipal = true; // Marcar como inicializado
        } else {
            try {
                recuperaDatos();
                colocaDatosInforme();
                if (inicializacionReporte){
                    //Si se va a levatar un reporte, no se puede establecer un avance en el slider
                    progSlider.setDisable(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Método para iniciar la vista de Jefes de Departamento
    public static void iniciaVistaJefesDepto(String[] args, String rfc) {
        rfcPer = rfc;
        // Ejecutar la inicialización en el hilo JavaFX
        Platform.runLater(() -> {
            try {
                new VistaJefesDepto().start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Método para cerrar la conexión a la base de datos
    public void cerrarConexionBD() throws SQLException {
        if (operBD != null) {
            operBD.cierraConexion();
        }
    }

    private void recuperaDatos() throws SQLException {
        try {
            listaResultados = operBD.recuperaDatos(rfcPer); // Método del Modelo

            // Verificar que la lista no esté vacía y tenga al menos 17 elementos
            if (listaResultados.size() >= 17) {
                placaVeh = listaResultados.get(0);
                fechaTramite = listaResultados.get(1);
                tipoServ = listaResultados.get(2);
                fechaIngreso = listaResultados.get(3);
                fechaEntrega = listaResultados.get(4);
                descGral = listaResultados.get(5);
                descEsp = listaResultados.get(6);
                marcaVeh = listaResultados.get(7);
                modeloVeh = listaResultados.get(8);
                annoVeh = listaResultados.get(9);
                colorVeh = listaResultados.get(10);
                foto1 = listaResultados.get(11);
                foto2 = listaResultados.get(12);
                foto3 = listaResultados.get(13);
                foto4 = listaResultados.get(14);
                descEdoVeh = listaResultados.get(15);

                if (listaResultados.get(16) == null || listaResultados.get(16).equals("NULL")) {
                    porcAv = "0";
                } else {
                    porcAv = listaResultados.get(16);
                }
            } else {

                mostrarAlertError("No hay trabajos asignados");
                Platform.runLater(() -> {
                    Platform.exit();

                    //Cerrar stage actual
                });
            }
        } catch (IndexOutOfBoundsException e) {
            mostrarAlertError("Error al acceder a los datos: " + e.getMessage());
        }
    }

    // Método para recargar la escena principal
    @FXML
    public void recargaEscenaPrincipal() {
        try {
            colocaDatosServicio();  // Colocar nuevamente los datos en los campos
        } catch (SQLException e) {
            mostrarAlertError("Error al recargar datos: " + e.getMessage());
        }
    }

    //Método para colocar los datos en los campos de la interfaz
    private void colocaDatosServicio() throws SQLException {
        recuperaDatos(); //Método del Controlador
        txtfServicio1.setText(placaVeh + " - " + fechaTramite + "-" + tipoServ);//Identificador del servicio
        txtfFechaIng.setText(fechaIngreso); //Fecha de ingreso
        txtfFechaEnt.setText(fechaEntrega); //Fecha de entrega estimada
        txtaDescGen.setText(descGral); //Descripción General
        txtaDescEsp.setText(descEsp); //Descripción del trabajo específico
        txtfMarca.setText(marcaVeh);  //Marca
        txtfModelo.setText(modeloVeh); //Modelo
        txtfAnno.setText(annoVeh); //Año
        txtfColor.setText(colorVeh); //Color
        txtaDescVeh.setText(descEdoVeh); //Descripción del estado del vehículo

        // Cargar y establecer imagen en los ImageView
        cargarImagen(foto1 +".jpg", imgFrente);
        cargarImagen(foto2 +".jpg", imgPosterior);
        cargarImagen(foto3+".jpg", imgIzq);
        cargarImagen(foto4 +".jpg", imgDer);
    }

    // Método para mostrar la ventana de avance
    @FXML
    public void informaAvance() {
        try {
            VistaJefesDepto.muestraVentanaAvance();
        } catch (Exception e) {
            mostrarAlertError("Error al mostrar ventana de avance: " + e.getMessage());
        }
    }

    private void colocaDatosInforme(){
        //Identificador del servicio
        txtfServicio2.setText(placaVeh + " - " + fechaTramite + " - " + tipoServ);
        progBar.setProgress((Double.parseDouble(porcAv))/100);
        labelAvance.setText(porcAv+ "%");
        /*Fragmento que hace mostrar el valor del slider*/
        progSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            nuevoPorcAv = String.valueOf((newValue.intValue()));
            labelNuevoAv.setText((newValue.intValue()) + "%");
        });
    }

    public void onClickReportaAvance() {

        porcAvInt = Integer.parseInt(porcAv); //Pasamos a int el porcentaje de avance actual
        nuevoPorcAvInt = Integer.parseInt(nuevoPorcAv);//Pasamos a int el nuevo  porcentaje de avance actual

        if (progSlider.isDisabled()) {
            mostrarAlertWarning("Se emitirá un reporte al Jefe del Taller.");
            imprevistoAv = "TRUE"; //El atributo indica que es un reporte al Jefe del Taller

        } else if (nuevoPorcAvInt <= porcAvInt){
            //Se emite una alerta si el porcentaje a insertar es menor al progreso actual
            mostrarAlertWarning("El porcentaje de avance es menor al progreso actual. " +
                    "Se emitirá un reporte al Jefe del Taller.");
            imprevistoAv = "TRUE"; //El atributo indica que es un reporte al Jefe del Taller

        } else if (progSlider.isDisabled()){ //La barra deshabilitada indica que será un reporte y no se actualizará el avance
            mostrarAlertWarning("Se emitirá un reporte al Jefe del Taller.");
            imprevistoAv = "TRUE"; //El atributo indica que es un reporte al Jefe del Taller
        }

        else if (nuevoPorcAvInt == 100){ //Avisar cuando se haya puesto 100% de avance
            mostrarAlertWarning("El porcentaje de avance seleccionado hará que se complete el " +
                    "trabajo");
            imprevistoAv = "FALSE";
        }
        else { //Cualquier otro porcentaje de avance
            imprevistoAv = "FALSE";
        }

        confirmacion = mostrarAlertConfirmacion("¿Está seguro que quiere informar un " +
                "avance de " + nuevoPorcAv + "%?");
        if (confirmacion && !(txtaDescAv.getText().trim() == null || txtaDescAv.getText().trim().isEmpty())) {
            try {

                operBD.nuevoInforme("RFC12345A1", placaVeh, tipoServ, fechaTramite, nuevoPorcAv, txtaDescAv.getText().trim(), imprevistoAv);
                VistaJefesDepto.recargarEscenaPrincipal();
                Stage stageActual = (Stage) btnConfirmar.getScene().getWindow();
                stageActual.close(); // Cierra la ventana actual de reporte de avance
                nuevoPorcAvInt = 0;
            } catch (SQLException e) {
                mostrarAlertError("Error al recuperar datos: " + e.getMessage());
            }
        } else {
            mostrarAlertError("La descripción no puede estar vacía");
        }
    }

    // Método para reportar un imprevisto
    @FXML
    public void reportaImprevisto() {
        inicializacionReporte = true; //levantamos flag para indicar que será un reporte
        informaAvance();
        inicializacionReporte = false; //restauramos la flag
    }
    @FXML
    public void onClickCancelar(){
        // Obtener el Stage asociado al botón
        Stage stageActual = (Stage) btnCancelar.getScene().getWindow();
        // Cerrar la ventana
        stageActual.close();
    }
}
