package Servicios;

import java.sql.SQLException;
import java.util.List;

public class ModeloServicios extends BD.ModeloBD{

    private static ModeloServicios instancia;

    // Constructor privado para evitar instanciación directa
    protected ModeloServicios() throws SQLException {
        super();
        System.out.println("La conexión se creó desde ModeloServicios");
    }

    // Método estático para obtener la instancia única
    public static ModeloServicios getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ModeloServicios();
        }
        return instancia;
    }

    //insertar un nuevo servicio
    public List<String> insertarDatosAlta(String Rfc,String Placa,String Tipo,String FechT,String FechE,String FechI,String DesG,String DesM,String DesH,String DesP,String DesE) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "insert into servicio values ('"+Rfc+"','"+Placa+"',"+Tipo+",'"+FechT+"','"+FechE+"','"+FechI+"','"+DesG+"',null,"+DesM+","+DesH+","+DesP+","+DesE+",false,false,false);";
        setComando(comando, "alta");
        return OperacionBD();
    }

    // buscar un cliente por su rfc
    public List<String> buscarCli(String rfc_cli) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "SELECT mail_cli,dominio_cli,nom_cli,app_cli,apm_cli,tel1_cli,rfc_cli from clientes where rfc_cli = '" + rfc_cli + "';";
        setComando(comando, "consulta");
        return OperacionBD();
    }

    // buscar un placas del vehiculo correspondientes al cliente seleccionado
    public List<String> consultaPlacas(String rfc_cli) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "select vehiculos.placa_veh from vehiculos inner join clientes on vehiculos.rfc_cli = clientes.rfc_cli where clientes.rfc_cli = '" + rfc_cli + "';";
        setComando(comando, "consulta");
        return OperacionBD();
    }

    // buscar un vehiculo por placa
    public List<String> buscarVeh(String placa_veh) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "select vehiculos.placa_veh,placa_esdo_veh,modelo_veh,marca_veh,ano_veh,color_veh from vehiculos where vehiculos.placa_veh = '" + placa_veh + "';";
        setComando(comando, "consulta");
        return OperacionBD();
    }

    //
    public List<String> consultaServicios() throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "select placa_veh,fecha_tramite_serv,tipo_serv from servicio;";
        setComando(comando, "consulta");
        return OperacionBD();
    }

    //
    public List<String> consultaDatosServ(String placa, String fecha, String tipo) throws SQLException {
        // Construyes la consulta SQL con los parámetros proporcionados
        String comando = "select servicio.tipo_serv,desc_gral_serv,desc_meca_serv,desc_hojal_serv,desc_pint_serv,desc_elect_serv,vehiculos.modelo_veh,marca_veh,clientes.nom_cli,app_cli,tel1_cli " +
                "from vehiculos " +
                "inner join servicio on vehiculos.placa_veh = servicio.placa_veh " +
                "inner join clientes on servicio.rfc_cli = clientes.rfc_cli " +
                "where servicio.placa_veh = '"+placa+"'and servicio.fecha_tramite_serv = '"+fecha+"' and servicio.tipo_serv = "+tipo+";";
        setComando(comando, "consulta");
        return OperacionBD();
    }
}
