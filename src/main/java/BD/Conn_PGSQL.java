package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn_PGSQL { //Clase pública

    /*Atributos*/
    private String usrBD;
    private String passBD;
    private String urlBD;
    private String driverClassName;
    private Connection conn = null;
    private Statement estancia;

    public Conn_PGSQL() throws SQLException {
        this.usrBD = "postgres";
        this.passBD = "zoacazoa";
        this.urlBD = "jdbc:postgresql://taller-mecanico.cr4i40esmq9v.us-east-2.rds.amazonaws.com:5432/taller_mecanico";
        this.driverClassName = "org.postgresql.Driver";
    }

    /*Encapsulación y apuntador "This"*/
    public void setUsuarioBD(String usuario) throws SQLException {
        this.usrBD = usuario;
    }

    public void setPassBD(String pass) {
        this.passBD = pass;
    }

    public void setUrlBD(String url) {
        this.urlBD = url;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    //Encapsulación
    public void conectar() throws SQLException {
        try {
            Class.forName(this.driverClassName).newInstance();
            this.conn = DriverManager.getConnection(this.urlBD, this.usrBD, this.passBD);
        }
        catch (Exception err) {
            System.out.println("Error " + err.getMessage());

        }
    }
    //Encapsulación
    public void cierraConexion() throws SQLException {
        this.conn.close();
    }

    public int insertar(String inserta) throws SQLException {
        Statement st = this.conn.createStatement();
        return st.executeUpdate(inserta);
    }

    public int borrar(String borra) throws SQLException {
        Statement st = (Statement) this.conn.createStatement();
        return st.executeUpdate(borra);
    }

    public ResultSet consulta(String consulta) throws SQLException { //Objeto
        this.estancia = conn.createStatement();
        return this.estancia.executeQuery(consulta);
    }

    public int edita(String edita) throws SQLException {
        Statement st = this.conn.createStatement();
        return st.executeUpdate(edita);
    }

    public Connection getConn() {
        return this.conn;
    }

}