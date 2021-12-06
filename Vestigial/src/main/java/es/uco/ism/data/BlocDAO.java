package es.uco.ism.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.bloc.BlocDTO;
import es.uco.ism.data.db.impl.DBConnectImpl;

public class BlocDAO extends DBConnectImpl {

	/**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
	public BlocDAO(String url, String user, String pwd, Properties sqlProp) {
		super(url, user, pwd, sqlProp);
	}
	
	/**
	 * Busca el bloc de notas del usuario en la base de datos
	 * 
	 * @param owner Due�o cuyo bloc de notas se va a buscar
	 * @return Bloc de notas del due�o dado
	 */

    public ArrayList<BlocDTO> QueryByAll() {
        BlocDTO note = null;
        ArrayList<BlocDTO> notes = new ArrayList<BlocDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_User");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {

                note = new BlocDTO(set.getString(1), set.getString(2), set.getString(3));
                notes.add(note);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notes;
    }

	public ArrayList<BlocDTO> QueryByOwner(String owner) {
		
		BlocDTO note = null;
		ArrayList<BlocDTO> notes = new ArrayList<BlocDTO>();

        try {
        	
        	Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Bloc_Owner");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, owner);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {            	
            	
            	note = new BlocDTO(set.getString(1), owner, set.getString(2));
                notes.add(note);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return notes;
	}
	
	public BlocDTO QueryByName(BlocDTO bloc) {
		
		BlocDTO note = null;
        try {
        	
        	Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Bloc_Name");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, bloc.getName());
            stmt.setString(2, bloc.getOwner());
            ResultSet set = stmt.executeQuery();
            if (set.next()) {            	
            	note = new BlocDTO(bloc.getName(), bloc.getOwner(), set.getString(2));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        
        return note;
	}
	
	/**
     * Inserta un bloc en la base de datos
     * 
     * @param bloc Bloc de notas a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
	public int Insert(BlocDTO bloc) {
		int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Bloc");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, bloc.getName());
            stmt.setString(2, bloc.getOwner());
            stmt.setString(3, bloc.getText());        
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
     * Actualiza un bloc de notas de la base de datos
     * 
     * @param bloc Bloc de notas a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Update(BlocDTO bloc) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Bloc");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, bloc.getText());
            stmt.setString(2, bloc.getName());
            stmt.setString(3, bloc.getOwner());
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
     * Borra un bloc de notas de la base de datos
     * 
     * @param owner Due�o cuyo bloc de notas se va a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(BlocDTO bloc) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Bloc");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, bloc.getName());
            stmt.setString(2, bloc.getOwner());
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

}
