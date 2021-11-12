package es.uco.ism.data;

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.db.impl.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class UserGroupDAO extends DBConnectImpl{

	public UserGroupDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }
	
	public ArrayList<String> QueryByUser(UserDTO user) {
			
	    	ArrayList<String> groups = new ArrayList<String>();
	
	        try {
	            Connection con = getConnection();
	            String statement = sqlProp.getProperty("Select_User_Group");
	            PreparedStatement stmt = con.prepareStatement(statement);
	            stmt.setString(1, user.getEmail());
	            ResultSet set = stmt.executeQuery();
	            
	            if (set.next()) {
	            	
	            	groups.add(set.getString(2));
	            }
	
	            if (stmt != null) {
	                stmt.close();
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return groups;
	    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param user Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(UserDTO user, String group) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_User_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, group);
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
    public int Update(UserDTO user, String group, String new_group) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_User_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, user.getEmail());
            stmt.setString(3, group);
            stmt.setString(1, new_group);

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
    public int Delete(UserDTO user, String group) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_User_Group");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, group);
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
