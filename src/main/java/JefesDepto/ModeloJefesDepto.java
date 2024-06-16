package JefesDepto;

import java.sql.SQLException;
import java.util.List;

public class ModeloJefesDepto extends BD.ModeloBD{

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

    public List<String> recuperaDatos() throws SQLException {
        setComando("select * from prueba_vista_servicio;", "consulta");
        return OperacionBD();
    }

    public List<String> nuevoInforme(String rfc_per, String placa_veh, String tipo_serv, String fecha_tramite_serv, String porc_av, String desc_av, String imprevisto_av) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "SELECT nuevoInforme('" + rfc_per + "', '" + placa_veh + "', '" + tipo_serv + "', '" + fecha_tramite_serv + "', " + porc_av + ", '" + desc_av + "', " + imprevisto_av + ");";
        setComando(comando, "consulta");
        return OperacionBD();
    }





}
