package es.uco.ism.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Clase que gestiona los accesos a la base de datos
 * 
 * @author Angel Fuentes Almoguera
 * @version 1.0
 * @since 31-10-2021
 */
public abstract class DAO {

    protected String url;
    protected String user;
    protected String pwd;    
    protected Properties sqlProp;

    /**
     * Constructor de los DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    protected DAO(String url, String user, String pwd, Properties sqlProp) {
        try {
        	this.url = url;
        	this.user = user;
        	this.pwd = pwd;
            this.sqlProp = sqlProp;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece la conexion con una base de datos
     * 
     * @return Conexion con la base de datos
     */
    protected Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url , user, pwd);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * Comprueba los resultados del acceso a la base de datos
     * 
     * @param results Lista con los resultados
     * @return 1 en caso de exito y 0 en caso de error
     */
	protected int CheckResults(ArrayList<Integer> results) {
		for (int i = 0; i < results.size(); i++) {
        	if (results.get(i) == -1) {
        		return 0;
        	}
        }
		
		return 1;
	}
}
