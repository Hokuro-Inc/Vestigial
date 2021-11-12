package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.utils.PasswordHashing;

public class TestUserDAO {

	public static void main(String[] args) throws IOException {
		
		Properties propertiesDB = new Properties();
		FileReader fileReaderDB = new FileReader("WebContent/server.properties");
		propertiesDB.load(fileReaderDB);
		
		Properties propertiesSQL = new Properties();
		FileReader fileReaderSQL = new FileReader("WebContent/sql.properties");
		propertiesSQL.load(fileReaderSQL);
		
		System.out.println("Test UserDAO");
		
		UserDAO userDAO = new UserDAO(propertiesDB.getProperty("URL"), propertiesDB.getProperty("USER"), propertiesDB.getProperty("PASSWORD"), propertiesSQL);
		
		String password = "myPassword";
		String salt = PasswordHashing.createSalt();
		String passwordHash = PasswordHashing.createHash(password, salt);
		
		UserDTO userDTO = new UserDTO("testUser@gmail.com", passwordHash, salt, "660651903", "123");
		
		assert userDAO.Insert(userDTO) > 0 : "No se ha introducido el usuario";
		
		UserDTO queryRes = userDAO.QueryByEmail("testUser@gmail.com");
		
		assert queryRes == null : "No se encontr� al usuario";
		
		assert PasswordHashing.createHash(password, queryRes.getSalt()).equals(queryRes.getPwd()) : "Error en la contrase�a";
		
		password = "password";
		
		passwordHash = PasswordHashing.createHash(password, queryRes.getSalt());
		
		queryRes.setPwd(passwordHash);
		
		assert userDAO.UpdatePassword(queryRes) > 0 : "Error al actualizar la contrase�a";
		
		queryRes = userDAO.QueryByEmail("testUser@gmail.com");
		
		assert queryRes.getPwd().equals(passwordHash) : "Error en la contrase�a actualizada";
		
		assert userDAO.Delete("testUser@gmail.com") > 0 : "Error en el borrado del usuario";
		
		assert userDAO.QueryByEmail("testUser@gmail.com") == null : "Se ha encontrado un usuario borrado";
		
		System.out.println("Fin del test");
		
	}

}
