package BD;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author omarc
 */
public class ControladorBD {

    private static ControladorBD instancia; //todas las clases usan la misma instancia

    private static List<String> contenedor = new ArrayList<>();

    public static synchronized ControladorBD getInstance() {

        /*Si no existe una instancia, crea una; de lo contrario,
        devuelve la instancia existente.*/
        if (instancia == null) {
            instancia = new ControladorBD();
        }
        return instancia;

        /*Se va a acceder a los resultados desde otras Clases,
         * por ello se requiere una misma instnacia para todo*/
    }

    public List<String> getContenedor() {
        return contenedor;
    }

}