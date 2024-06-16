package CRUD;

import BD.ModeloBD;
import java.sql.SQLException;
import java.util.List;

public class ModeloCRUD extends ModeloBD {
    private static ModeloCRUD instancia;

    // Constructor privado para evitar instanciación directa
    protected ModeloCRUD() throws SQLException {
        super();
        System.out.println("La conexión se creó desde ModeloCRUD");
    }

    // Método estático para obtener la instancia única
    public static ModeloCRUD getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ModeloCRUD();
        }
        return instancia;
    }

    public List<String> consultarPersonaPorID(String id) throws SQLException {
        setComando("SELECT * FROM gente WHERE id = " + id, "consulta");
        return OperacionBD();
    }


}
