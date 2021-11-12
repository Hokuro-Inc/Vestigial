package es.uco.ism.data;

import es.uco.ism.business.contact.ContactDTO;
import es.uco.ism.data.db.impl.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class ContactGroupDAO extends DBConnectImpl{
	
	public ContactGroupDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }
	
	public ArrayList<String> QueryByContact(ContactDTO contact) {
		
    	ArrayList<String> groups = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Contact_Group");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
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
    
    public int Insert(ContactDTO contact, String group) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Contact_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
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
    
    public int Update(ContactDTO contact, String group, String new_group) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Contact_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, contact.getPhone()+"-"+contact.getPrefix());
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
    
    public int Delete(ContactDTO contact, String group) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_User_Group");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
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
