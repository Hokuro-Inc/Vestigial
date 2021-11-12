package es.uco.ism.data;

import es.uco.ism.data.db.impl.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class GroupDAO extends DBConnectImpl {

	public GroupDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }
	
	public ArrayList<String> QueryAll(String group) {
		
    	ArrayList<String> groups = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Group");
            PreparedStatement stmt = con.prepareStatement(statement);
 
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
            	
            	groups.add(set.getString(1));
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
	
	public int Insert(String name) {
		
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, name);
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
	
    public int Update(String name) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, name);
            stmt.setString(2, name);
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
    
    public int Delete(String name) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Group");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, name);
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
