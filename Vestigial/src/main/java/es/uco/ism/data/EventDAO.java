package es.uco.ism.data;

import es.uco.ism.data.db.impl.*;
import es.uco.ism.utils.PasswordHashing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
    
    public ArrayList<EventDTO> QueryByAll() {
    	EventDTO evento = null;
    	ArrayList<EventDTO> events = new ArrayList<EventDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Event");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
            	
            	evento = new EventDTO(set.getString(1), set.getString(6), set.getDate(4), set.getDate(5), set.getString(2), set.getString(3));
            	events.add(evento);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return events;
    }
    
    public EventDTO QueryById(String id) {
    	EventDTO evento = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Event");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
            	//getTimestamp
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
        java.sql.Timestamp sqlDate = null;
        
        try {
            String statement = sqlProp.getProperty("Insert_Event");
            String statement2 = sqlProp.getProperty("Insert_ID");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            PreparedStatement stmt2 = con.prepareStatement(statement2, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt2.setInt(1, 0);
            stmt2.executeUpdate();
            
            ResultSet rs = stmt2.getGeneratedKeys();
            String id = new String();
            
            if(rs.next()){
                
            	int key = rs.getInt(1);
            	
            	id = PasswordHashing.createHash(String.valueOf(key), PasswordHashing.createSalt());
            }
            
            stmt.setString(1, id);
            stmt.setString(2, evento.getName());
            stmt.setString(3, evento.getDescription());
            
            sqlDate = new java.sql.Timestamp(evento.getStart().getTime());
            stmt.setTimestamp(4, sqlDate);
            
            sqlDate = new java.sql.Timestamp(evento.getEnd().getTime());
            stmt.setTimestamp(5, sqlDate);
            
            stmt.setString(6, evento.getOwner());
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
            stmt.setTimestamp(3, new java.sql.Timestamp(evento.getStart().getTime()));
            stmt.setTimestamp(4, new java.sql.Timestamp(evento.getEnd().getTime()));
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

	public ArrayList<EventDTO> QueryByEmail(String email) {

ArrayList<EventDTO> listEvent = new ArrayList<EventDTO>();
		

        try {
            Connection con = getConnection();

            String statement = sqlProp.getProperty("Select_Event_User");

            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, email);

            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            	
            	EventDTO aux = new EventDTO(set.getString(1), email, new Date (set.getTimestamp(4).getTime()),  new Date (set.getTimestamp(5).getTime()), set.getString(2), set.getString(3));
            	listEvent.add(aux);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listEvent;
	}
}
