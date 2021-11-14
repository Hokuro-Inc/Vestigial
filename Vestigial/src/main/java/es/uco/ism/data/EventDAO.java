package es.uco.ism.data;

import es.uco.ism.data.db.impl.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.event.EventDTO;

public class EventDAO extends DBConnectImpl{
	/**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public EventDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca al usuario de la base de datos cuyo email coincide con el dado para comprobar su contraseña
     * 
     * @param email Email del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public EventDTO QueryById(String id) {
    	EventDTO evento = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Event");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
            	
            	evento = new EventDTO(id, set.getString(5), set.getDate(3), set.getDate(4), set.getString(1), set.getString(2));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return evento;
    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param user Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(EventDTO evento) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Event");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, evento.getId());
            stmt.setString(2, evento.getName());
            stmt.setString(3, evento.getDescription());
            stmt.setDate(4, (java.sql.Date) evento.getStart());
            stmt.setDate(5, (java.sql.Date) evento.getEnd());
            stmt.setString(6, evento.getOwner());        
            stmt.executeUpdate();
                        
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    /**
     * Actualiza un usuario de la base de datos
     * 
     * @param user Usuario a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Update(EventDTO evento) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Event");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(6, evento.getId());
            stmt.setString(1, evento.getName());
            stmt.setString(2, evento.getDescription());
            stmt.setDate(3, (java.sql.Date) evento.getStart());
            stmt.setDate(4, (java.sql.Date) evento.getEnd());
            stmt.setString(5, evento.getOwner());        
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

        /**
     * Borra un usuario de la base de datos
     * 
     * @param email Email del usuario a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String id) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Event");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            results.add(stmt.executeUpdate());

            status = CheckResults(results);
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
