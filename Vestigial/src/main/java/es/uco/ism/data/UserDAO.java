package es.uco.ism.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import es.uco.ism.data.db.impl.*;
import es.uco.ism.utils.Mail;
import es.uco.ism.business.user.UserDTO;


public class UserDAO extends DBConnectImpl {

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
    
    public ArrayList<UserDTO> QueryByAll() {
    	UserDTO user = null;
    	ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_User");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
            	
            	String phone = set.getString(4);
            	String[] tokens = phone.split("-");
            	
            	user = new UserDTO(set.getString(1), set.getString(2), set.getString(3), tokens[1], tokens[0]);
            	users.add(user);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    public UserDTO QueryByEmail(String email) {
    	UserDTO user = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Password");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, email);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	
            	String phone = set.getString(3);
            	String[] tokens = phone.split("-");
            	
            	user = new UserDTO(email, set.getString(1), set.getString(2), tokens[0], tokens[1]);
            	user.setCode(set.getInt(4));
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
        	
            String statement = sqlProp.getProperty("Insert_User");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPwd());
            stmt.setString(3, user.getSalt());
            stmt.setString(4, user.getPhone()+"-"+user.getPrefix());           
            
            Random r = new Random();
    		int code =  r.nextInt(1000000);
    		
            stmt.setInt(5, code);
            status = stmt.executeUpdate();
                        
            if (stmt != null) {
            	stmt.close();
            }
            
            Mail correo = new Mail(user.getEmail(), "Vestigial", "<h1>Se bienvenido a nuestra aplicaci�n, esperamos que tu estancia sea agradable.</h1>"
            		+ "<h2>Tu codigo de recuperaci�n de la contrase�a es: " + code + "</h2>");
            
            String MAIL = "hokuroincorporated@gmail.com";
    		String PWD = "adaylachupa";
    		
            correo.sendEmail(MAIL,PWD);
            
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
            String statement = sqlProp.getProperty("Update_User");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, user.getEmail());

            stmt.setString(1, user.getPhone()+"-"+user.getPrefix());

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
            String statement = sqlProp.getProperty("Update_Pwd_User");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, user.getPwd());
            stmt.setString(2, user.getSalt());
            stmt.setString(3, user.getEmail());
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
            String statement = sqlProp.getProperty("Delete_User");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
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
