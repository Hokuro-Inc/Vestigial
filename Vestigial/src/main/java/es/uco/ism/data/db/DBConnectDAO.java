package es.uco.ism.data.db;
import java.sql.Connection;

public interface DBConnectDAO {
	
	public Connection getConnection();
}
