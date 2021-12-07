package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.ism.business.contact.ContactDTO;
import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.ContactDAO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.utils.PasswordHashing;

public class TestContactDAO {

	public static void main (String[] args ) throws IOException {
		Properties propertiesBD = new Properties();
		FileReader fileReaderBD = new FileReader("WebContent/server.properties");
		propertiesBD.load(fileReaderBD);

		Properties propertiesSQL = new Properties();
		FileReader fileReader = new FileReader("WebContent/sql.properties");
		propertiesSQL.load(fileReader);
		
		System.out.println("Test ContactDAO");
		
		ContactDAO contactDAO = new ContactDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		UserDAO userDAO = new UserDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		
		String password = "myPassword";
		String salt = PasswordHashing.createSalt();
		String passwordHash = PasswordHashing.createHash(password, salt);
				
		UserDTO propietarioTest = new UserDTO("chris@gmail.com",passwordHash,salt,"660651903","123");
		ContactDTO contactoTest = new ContactDTO("660651993","123","Juan","Fernandez Garcia","Rekeka", "rekeka@gmail.com","Colaborador del proyecto","Direccion",propietarioTest.getEmail());
		
		assert userDAO.Insert(propietarioTest) > 0: "No se ha introducido el usuario";
		
		assert contactDAO.Insert(contactoTest) > 0: "No se ha introducido el contacto";
		
		assert contactDAO.QueryByPhone(contactoTest.getPhone(),contactoTest.getOwner()) != null : "No se ha encontrado el contacto ";
		
		assert contactDAO.QueryByPhone(contactoTest.getPhone(),contactoTest.getOwner()).getEmail() != contactoTest.getEmail() : "Error correo del contacto ";
		
		assert contactDAO.QueryByPhone(contactoTest.getPhone(),contactoTest.getOwner()).getOwner() != contactoTest.getOwner() : "Error propietario del contacto ";

		contactoTest.setAddress("Direccion2");
		assert contactDAO.Update(contactoTest) > 0: "Error al actualizar el contacto";

		assert contactDAO.QueryByPhone(contactoTest.getPhone(),contactoTest.getOwner()).getAddress() != contactoTest.getAddress() : "Error direccion actualizada del contacto ";
		
		assert contactDAO.Delete(contactoTest.getPhone(),contactoTest.getEmail()) > 0: "Error al borrar el contacto";
		
		assert contactDAO.QueryByPhone(contactoTest.getPhone(),contactoTest.getOwner()) == null : "Error se ha encontrado un contacto borrado";
		
		System.out.println("Fin del test");
	}
	
}
