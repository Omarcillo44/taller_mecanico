package BD;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloBD {

    protected Conn_PGSQL bd;
    protected String comando;
    protected String tipoOper;

    protected ModeloBD() throws SQLException {
        bd = new Conn_PGSQL(); //Se crea una nueva instancia para la conexión
        bd.conectar(); //Se crea la conexión
        System.out.println("Conexión creada");
    }

    public void cierraConexion() throws SQLException {
        bd.cierraConexion();//Se crea una nueva instancia para la conexión
        System.out.println("Conexión cerrada");
    }

    //Método para establecer el comando SQL y el tipo de operación (CRUD)
    public void setComando(String comando, String tipoOper) {
        this.comando = comando;
        this.tipoOper = tipoOper;

    }

    //Método para ejecutar una operación en la BD, retorna una lista con los resultados
    public List<String> OperacionBD() throws SQLException{

        // Lista para los resultados de la consulta
        List<String> resultados = new ArrayList<>();
        ResultSet resultSet; //Almacena los resultados

        /*El modelo se puede aplicar a cualquier tipo de tabla, por ende
        se requiere saber qué estructura tiene cada tabla.
        Para ello se usan los metadatos sobre los resultados.
        */
        ResultSetMetaData metaData;

        switch (tipoOper) { //Según la operación del CRUD, se cambia de opc

            case "consulta" -> {
                System.out.println("Consulta en curso");
                resultSet = bd.consulta(comando);
                //Se obtienen los metadatos de los resultados
                metaData = resultSet.getMetaData();

                while (resultSet.next()) {
                    /*Se usan los metadatos para conocer cuántos resultados
                    se obtuvieron de la consulta y apartir de dicho número
                    establecer un for que agregue los resultados a una lista*/
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        // Agregar el valor de cada columna a la lista
                        resultados.add(resultSet.getString(i));
                        System.out.println("Resultado " + i + ": " + resultSet.getString(i));
                    }
                }
            }

            case "alta" ->{

                System.out.println("Inserción en curso");
                bd.insertar(comando);

                // Realizar cualquier acción necesaria con el resultado de la inserción
            }
            case "cambio" ->{
                System.out.println("Edición en curso");
                bd.edita(comando);

            }
            // Realizar cualquier acción necesaria con el resultado de la inserción

            case "baja" -> {
                System.out.println("Borrado en curso");
                bd.borrar(comando);

                // Realizar cualquier acción necesaria con el resultado de la inserción
            }
        }

        return resultados;

    }

}