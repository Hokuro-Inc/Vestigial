package es.uco.ism.data.db.impl;

import es.uco.ism.data.db.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public abstract class DBConnectImpl implements DBConnectDAO{
	
	private Connection con;
	
	protected String url;
    protected String user;
    protected String pwd;    
    protected Properties sqlProp;

	
	public DBConnectImpl(String url, String user, String pwd, Properties sqlProp)
	{	
		try {
        	this.url = url;
        	this.user = user;
        	this.pwd = pwd;
            this.sqlProp = sqlProp;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
		connect();
	}
	
	private void connect()
	{
		try 
    	{
			
            Class.forName("com.mysql.jdbc.Driver");
            
            con = (Connection) DriverManager.getConnection(url, user, pwd);  
    	} 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (SQLException e)
        {        	
            e.printStackTrace();
        }
		
	}
		
	public Connection getConnection ()
	{
		
		if (con == null)
		{
			connect();
		}
		return con;
		
	}	
	
	protected int CheckResults(ArrayList<Integer> results) {
		
		for (int i = 0; i < results.size(); i++) {
			
        	if (results.get(i) == -1) {
        		return 0;
        	}
        }
		
		return 1;
	}
}
