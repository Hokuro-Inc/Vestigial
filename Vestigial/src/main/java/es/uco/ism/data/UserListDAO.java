package es.uco.ism.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.db.impl.DBConnectImpl;

public class UserListDAO extends DBConnectImpl{

	public UserListDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }
	
	public ArrayList<ArrayList<String>> QueryByAll() {
		
    	ArrayList<ArrayList<String>> user_lists = new ArrayList<ArrayList<String>>();
    	ArrayList<String> user_list = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_User_List");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
            	
            	user_list.add(set.getString(1));
            	user_list.add(set.getString(2));
            	
            	user_lists.add(user_list);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return user_lists;
    }
	
	public ArrayList<String> QueryByUser(UserDTO user) {
			
	    	ArrayList<String> lists = new ArrayList<String>();
	
	        try {
	            Connection con = getConnection();
	            String statement = sqlProp.getProperty("Select_User_List");
	            PreparedStatement stmt = con.prepareStatement(statement);
	            stmt.setString(1, user.getEmail());
	            ResultSet set = stmt.executeQuery();
	            
	            if (set.next()) {
	            	
	            	lists.add(set.getString(2));
	            }
	
	            if (stmt != null) {
	                stmt.close();
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return lists;
	    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param user Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(UserDTO user) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_User_List");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLists()[0]);
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
    public int Update(UserDTO user) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_User_List");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLists()[0]);
            stmt.setString(1, user.getLists()[1]);

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
    public int Delete(UserDTO user) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_User_List");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLists()[0]);
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
