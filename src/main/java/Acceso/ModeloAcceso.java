package Acceso;

import BD.ModeloBD;

import java.sql.SQLException;
import java.util.List;

import static CRUD.VistaCRUD.mostrarAlertError;

public class ModeloAcceso extends ModeloBD {
    private static ModeloAcceso instancia;
    protected ModeloAcceso() throws SQLException {
        super();
        System.out.println("La conexión se creó desde ModeloJefesDepto");
    }

    // Método estático para obtener la instancia única
    public static ModeloAcceso getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ModeloAcceso();
        }
        return instancia;
    }

    public List<String> validaAcceso(String usuarioDominio, String password) throws SQLException {

        // Separar usuario y dominio
        String[] partes = usuarioDominio.split("@");

            String usuario = partes[0];
            String dominio = partes[1];

            setComando(
                    "SELECT validaAcceso('"+ "@" + dominio +"', '" + usuario +"','" + password +"');",
                    "consulta");

            System.out.println("Usuario: " + usuario);
            System.out.println("Dominio: " + "@"+dominio);
            System.out.println("Contraseña: " + password);

        return OperacionBD();
    }

}
