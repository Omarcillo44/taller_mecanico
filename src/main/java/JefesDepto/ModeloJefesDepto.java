package JefesDepto;

import BD.ModeloBD;
import CRUD.ModeloCRUD;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.List;

import static CRUD.VistaCRUD.mostrarAlertError;

public class ModeloJefesDepto extends BD.ModeloBD{

    String cargo;
     List<String> listaCargo;
    private static ModeloJefesDepto instancia;

    // Constructor privado para evitar instanciación directa
    protected ModeloJefesDepto() throws SQLException {
        super();
        System.out.println("La conexión se creó desde ModeloJefesDepto");
    }

    // Método estático para obtener la instancia única
    public static ModeloJefesDepto getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ModeloJefesDepto();
        }
        return instancia;
    }

    public List<String> recuperaDatos( String rfc) throws SQLException {

        listaCargo = bucaCargo(rfc);
        cargo = listaCargo.getFirst();
        System.out.println("El cargo es: " + cargo);
        switch (cargo) {
            case "jefeM" -> setComando("select * from vistaTrabajoMecanico;", "consulta");
            case "jefeH" -> setComando("select * from vistaTrabajoHojal;", "consulta");
            case "jefeP" -> setComando("select * from vistaTrabajoPintura;", "consulta");
            default -> setComando("select * from vistaTrabajoElectrico;", "consulta");
        }
        return OperacionBD();
    }

    public List<String> bucaCargo( String rfc) throws SQLException {

        setComando("select personal.cargo_per from personal where rfc_per = '" + rfc +"' ;", "consulta");
        System.out.println("Buscando cargo con rfc = "+ rfc);
        return OperacionBD();
    }


    public List<String> nuevoInforme(String rfc_per, String placa_veh, String tipo_serv, String fecha_tramite_serv, String porc_av, String desc_av, String imprevisto_av) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "SELECT nuevoInforme('" + rfc_per + "', '" + placa_veh + "', '" + tipo_serv + "', '" + fecha_tramite_serv + "', " + porc_av + ", '" + desc_av + "', " + imprevisto_av + ");";
        setComando(comando, "consulta");
        return OperacionBD();
    }

    // Método para cargar una imagen en un ImageView
    public static void cargarImagen(String nombreImagen, ImageView imageView) {
        try {
            Image imagen = new Image(ModeloJefesDepto.class.getResourceAsStream("/ImagenesVehiculos/" + nombreImagen));
            imageView.setImage(imagen);
        } catch (Exception e) {
            mostrarAlertError("Error al cargar la imagen " + nombreImagen + ": " + e.getMessage());
        }
    }



}
