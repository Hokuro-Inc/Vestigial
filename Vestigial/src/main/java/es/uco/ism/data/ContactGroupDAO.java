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
	
public ArrayList<ArrayList<String>> QueryByAll() {
		
    	ArrayList<ArrayList<String>> contact_groups = new ArrayList<ArrayList<String>>();
    	ArrayList<String> contact_group = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_All_Contact_Group");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            	
            	contact_group.add(set.getString(1));
            	contact_group.add(set.getString(2));
            	
            	contact_groups.add(contact_group);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return contact_groups;
    }
	
	public ArrayList<String> QueryByContact(ContactDTO contact) {
		
    	ArrayList<String> groups = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Contact_Group");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            	
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
    
    public int Insert(ContactDTO contact) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Contact_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
            stmt.setString(2, contact.getGroups()[0]);
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
    
    public int Update(ContactDTO contact) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Contact_Group");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, contact.getPhone()+"-"+contact.getPrefix());
            stmt.setString(3, contact.getGroups()[0]);
            stmt.setString(1, contact.getGroups()[1]);

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
    
    public int Delete(ContactDTO contact) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_User_Group");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, contact.getPhone()+"-"+contact.getPrefix());
            stmt.setString(2, contact.getGroups()[0]);
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
