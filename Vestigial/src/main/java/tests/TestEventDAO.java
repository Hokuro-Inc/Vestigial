package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import es.uco.ism.business.event.EventDTO;
import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.EventDAO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.utils.PasswordHashing;

public class TestEventDAO {
	public static void main (String[] args ) throws IOException {
		Properties propertiesBD = new Properties();
		FileReader fileReaderBD = new FileReader("src/main/webapp/WEB-INF/server.properties");
		propertiesBD.load(fileReaderBD);

		Properties propertiesSQL = new Properties();
		FileReader fileReader = new FileReader("src/main/webapp/WEB-INF/sql.properties");
		propertiesSQL.load(fileReader);
		
		System.out.println("Test EventDAO");
		
		EventDAO eventDAO = new EventDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);

		UserDAO userDAO = new UserDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		
		String password = "myPassword";
		String salt = PasswordHashing.createSalt();
		String passwordHash = PasswordHashing.createHash(password, salt);
				
		UserDTO propietarioTest = new UserDTO("blablacar2@gmail.com",passwordHash,salt,"660651903","123");
		String ID = "12345"; // Como generamos las ID
		EventDTO eventoTest = new EventDTO (ID,propietarioTest.getEmail(),new Date(), new Date(),"Test 1" , "Descripcion Prueba ");
		
		assert userDAO.Insert(propietarioTest) > 0 : "No se ha introducido el usuario de pruebas";
		
		assert eventDAO.Insert(eventoTest) > 0 : "No se ha introducido el evento de pruebas";
		
		EventDTO eventoBuscado = eventDAO.QueryById(eventoTest.getId());
		assert eventoBuscado != null : "No se ha encontrado del evento";
		
		eventoTest.setDescription("Descripcion Actualizada");
		
		assert eventDAO.Update(eventoTest) > 0 : "No se ha introducido el evento de pruebas";
		
		eventoBuscado = eventDAO.QueryById(eventoTest.getId());
		assert eventoBuscado != null : "No se ha encontrado del evento";
		
		assert eventoBuscado.getDescription().equals("Descripcion Actualizada") : "Error en el evento actualizado";

		assert eventDAO.Delete(eventoTest.getId()) > 0 : "No se ha borrado el evento de pruebas";
		
		eventoBuscado = eventDAO.QueryById(eventoTest.getId());
		assert eventoBuscado == null : "Se ha encontrado un evento borrado";
		
		assert userDAO.Delete(propietarioTest.getEmail()) > 0 : "No se ha borrado el usuario de pruebas";
		
		UserDTO user = userDAO.QueryByEmail(propietarioTest.getEmail());
		assert user == null : "Se ha encontrado un usuario borrado";
	}
}
