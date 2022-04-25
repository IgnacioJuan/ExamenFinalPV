
package Modelo.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectionPg {
    Connection con;
    String url = "jdbc:postgresql://localhost:5432/ExamenFinal";
    String user = "postgres";
    String pass = "1234";

    public ConectionPg() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet consulta(String sql) {
        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean accion(String sql) {
        Statement st;
        boolean resultado;
        try {
            st = con.createStatement();
            resultado=st.execute(sql);
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
            resultado=false;
        }
        return resultado;
    }

    public ConectionPg(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
