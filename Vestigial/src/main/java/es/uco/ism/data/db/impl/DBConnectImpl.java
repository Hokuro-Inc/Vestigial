package es.uco.ism.data.db.impl;

import es.uco.ism.data.db.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectImpl implements DBConnectDAO{
	
	private Connection con;
	
	public DBConnectImpl()
	{
		connect();
	}
	
	private void connect()
	{
		try 
    	{
			
            Class.forName("com.mysql.jdbc.Driver");
            
            con = (Connection) DriverManager.getConnection(
							"jdbc:mysql://hokuro.xyz:3306/ism?characterEncoding=utf8&useSSL=false",
							"hokuro",
							"hokuro12");  
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
		
	 /**
	 * Metodo getConnection que devuelve una conexion a la base de datos
	 * @return con -> Devuelve una conexion a la BBDD
	 * @author Pedro Pablo García Pozo
	 * @param servletContext 
	 * @return 
	 */
	
	public Connection getConnection ()
	{
		
		if (con == null)
		{
			connect();
		}
		return con;
		
	}	
}
