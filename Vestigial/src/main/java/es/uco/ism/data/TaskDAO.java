package es.uco.ism.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.task.Status;
import es.uco.ism.business.task.TaskDTO;

public class TaskDAO extends DAO{
	/**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public TaskDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca al usuario de la base de datos cuyo email coincide con el dado para comprobar su contraseña
     * 
     * @param email Email del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public TaskDTO QueryById(String id) {
    	TaskDTO task = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Task");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
            	task = new TaskDTO(id, set.getString(1),set.getString(2), set.getString(3),Status.InProcess);
            	task.setStatus(set.getString(4));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return task;
    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param user Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(TaskDTO task) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Task");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, task.getId());
            stmt.setString(2, task.getOwner());
            stmt.setString(3, task.getName());
            stmt.setString(4, task.getDescription());
            stmt.setString(5, task.getDescription());  
            stmt.setString(6, task.getStatus().toString());        
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
    public int Update(TaskDTO task) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Task");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(6, task.getId());
            stmt.setString(1, task.getOwner());
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getDescription());  
            stmt.setString(5, task.getStatus().toString());    
            
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
            String statement = sqlProp.getProperty("Delete_Task");
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
