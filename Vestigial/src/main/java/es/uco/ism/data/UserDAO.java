package es.uco.ism.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.user.UserDTO;

public class UserDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public UserDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca al usuario de la base de datos cuyo email coincide con el dado para comprobar su contraseña
     * 
     * @param email Email del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public UserDTO QueryByEmail(String email) {
    	UserDTO user = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Password");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, email);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	user = new UserDTO(email, set.getString(1), set.getString(2), set.getString(3),set.getString(4));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return user;
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
            String statement = sqlProp.getProperty("Insert_Usuario");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPwd());
            stmt.setString(3, user.getSalt());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPrefix());            
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
            String statement = sqlProp.getProperty("Update_Usuario");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(5, user.getEmail());
            stmt.setString(1, user.getPwd());
            stmt.setString(2, user.getSalt());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPrefix());  
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
     * Actualiza la contraseña de un usuario
     * 
     * @param user Usuario a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdatePassword(UserDTO user) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Password");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, user.getEmail());
            stmt.setString(1, user.getPwd());
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
    public int Delete(String email) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Usuario");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, email);
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Delete_Password");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, email);
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
