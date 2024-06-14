package CRUD;

import java.sql.SQLException;
import java.util.List;

public class ModeloCRUD extends BD.ModeloBD{


    protected ModeloCRUD() throws SQLException {


    }

    public List<String> consultarPersonaPorID(String id) throws SQLException {
        setComando("SELECT * FROM gente WHERE id = " + id, "consulta");
        return OperacionBD();
    }

}
