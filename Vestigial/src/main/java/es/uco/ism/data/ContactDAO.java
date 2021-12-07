package es.uco.ism.data;

import es.uco.ism.data.db.impl.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.contact.ContactDTO;

public class ContactDAO extends DBConnectImpl{
	/**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public ContactDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca al usuario de la base de datos cuyo email coincide con el dado para comprobar su contraseña
     * 
     * @param email Email del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    
    public ArrayList<ContactDTO> QueryByAll() {
    	ContactDTO contacto = null;
    	ArrayList<ContactDTO> contacts = new ArrayList<ContactDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Contact");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
            	
            	String[] tokens = set.getString(1).split("-");
            	
            	contacto = new ContactDTO(tokens[0], tokens[1], set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getString(7), set.getString(8));
            	contacts.add(contacto);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return contacts;
    }
    
    public ContactDTO QueryByPhone(String phone,String owner) {
    	ContactDTO contacto = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Contact");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, phone);
            stmt.setString(2, owner);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	
            	String[] tokens = phone.split("-");
            	
            	contacto = new ContactDTO(tokens[0], tokens[1], set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6), owner);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return contacto;
    }

	public ArrayList<ContactDTO> QueryByOwner(String email) {
		ContactDTO contacto = null;
    	ArrayList<ContactDTO> contacts = new ArrayList<ContactDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Contact_Owner");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, email);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
            	
            	String[] tokens = set.getString(1).split("-");
            	
            	contacto = new ContactDTO(tokens[0], tokens[1], set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6), set.getString(7), email);
            	contacts.add(contacto);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return contacts;
	}
    /**
     * Inserta un usuario en la base de datos
     * 
     * @param user Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(ContactDTO contacto) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Contact");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contacto.getPhone()+"-"+contacto.getPrefix());
            stmt.setString(2, contacto.getName());
            stmt.setString(3, contacto.getSurname());
            stmt.setString(4, contacto.getAlias());
            stmt.setString(5, contacto.getEmail());  
            stmt.setString(7, contacto.getDescription());  
            stmt.setString(8, contacto.getAddress());  
            stmt.setString(6, contacto.getOwner());           
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
    public int Update(ContactDTO contacto) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Contact");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contacto.getName());
            stmt.setString(2, contacto.getSurname());
            stmt.setString(3, contacto.getAlias());
            stmt.setString(4, contacto.getEmail());  
            stmt.setString(5, contacto.getDescription());  
            stmt.setString(6, contacto.getAddress());  
            stmt.setString(8, contacto.getOwner());  
            stmt.setString(7, contacto.getPhone()+"-"+contacto.getPrefix());  
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
    public int Delete(String telefono, String owner) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Contact");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, telefono);
            stmt.setString(2, owner);
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
