package es.uco.ism.data;

import es.uco.ism.data.db.impl.*;
import es.uco.ism.utils.PasswordHashing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.task.Status;
import es.uco.ism.business.task.TaskDTO;

public class TaskDAO extends DBConnectImpl{
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
    public ArrayList<TaskDTO> QueryByAll() {
    	TaskDTO task = null;
    	ArrayList<TaskDTO> tasks = new ArrayList<TaskDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Task");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            while (set.next()) {
            	
            	task = new TaskDTO(set.getString(1), set.getString(5),set.getString(2), set.getString(3), Status.valueOf(set.getString(4)), set.getString(6));
            	tasks.add(task);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return tasks;
    }
    
    public TaskDTO QueryById(String id) {
    	TaskDTO task = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Task");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
            	
            	task = new TaskDTO(id, set.getString(4),set.getString(1), set.getString(2), Status.valueOf(set.getString(3)), set.getString(5));
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

	public ArrayList<TaskDTO> QueryByOwnerAndLabel(TaskDTO task) {
		
		ArrayList<TaskDTO> listTask = new ArrayList<TaskDTO>();
		

        try {
            Connection con = getConnection();

            String statement = sqlProp.getProperty("Select_Task_User_List");

            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, task.getOwner());
            stmt.setString(2, task.getList());
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            		
            	TaskDTO aux = new TaskDTO(set.getString(1), task.getOwner(), set.getString(2), set.getString(3), Status.valueOf(set.getString(4)), task.getList());
            	listTask.add(aux);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return listTask;
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
            stmt.setString(2, task.getName());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getStatus().toString());
            stmt.setString(5, task.getOwner());  
            stmt.setString(6, task.getList());
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
            task.setId(id);
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
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().toString());
            stmt.setString(4, task.getOwner());
            stmt.setString(5, task.getList());
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
