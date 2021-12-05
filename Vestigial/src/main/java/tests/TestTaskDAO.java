package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.business.task.Status;
import es.uco.ism.business.task.TaskDTO;
import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.TaskDAO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.data.UserListDAO;
import es.uco.ism.utils.PasswordHashing;

public class TestTaskDAO {
	public static void main (String[] args ) throws IOException {
		Properties propertiesBD = new Properties();
		FileReader fileReaderBD = new FileReader("src/main/webapp/WEB-INF/server.properties");
		propertiesBD.load(fileReaderBD);

		Properties propertiesSQL = new Properties();
		FileReader fileReader = new FileReader("src/main/webapp/WEB-INF/sql.properties");
		propertiesSQL.load(fileReader);
		
		System.out.println("Test TaskDAO");
		
		TaskDAO taskDAO = new TaskDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		UserDAO userDAO = new UserDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		UserListDAO userlistDAO = new UserListDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		
		String password = "myPassword";
		String salt = PasswordHashing.createSalt();
		String passwordHash = PasswordHashing.createHash(password, salt);
				
		UserDTO propietarioTest = new UserDTO("pedropgarcialol@gmail.com",passwordHash,salt,"660651903","123");
		ArrayList <String> lists = new ArrayList<String>();
		lists.add("Lista 2");
		propietarioTest.setLists(lists);
		
		String ID = "12345"; // Como generamos las ID
		TaskDTO taskTest = new TaskDTO (ID,propietarioTest.getEmail(),"Test 1" , "Descripcion Prueba ",Status.ToDo, "Lista 2");
		
		assert userDAO.Insert(propietarioTest) > 0 : "No se ha introducido el usuario de pruebas";
		
		assert userlistDAO.Insert(propietarioTest) > 0 : "No se ha introducido en la interrelacion";
		
		assert taskDAO.Insert(taskTest) > 0 : "No se ha introducido la tarea de pruebas";
		
		TaskDTO taskBuscada = taskDAO.QueryById(taskTest.getId());
		assert taskBuscada != null : "No se ha encontrado la tarea";
		
		taskTest.setDescription("Descripcion Actualizada");
		
		assert taskDAO.Update(taskTest) > 0 : "No se ha introducido la tarea de pruebas";
		
		taskBuscada = taskDAO.QueryById(taskTest.getId());
		assert taskBuscada != null : "No se ha encontrado la tarea";
		
		assert taskBuscada.getDescription().equals("Descripcion Actualizada") : "Error en la tarea actualizado";

		assert taskDAO.Delete(taskTest.getId()) > 0 : "No se ha borrado la tarea de pruebas";
		
		taskBuscada = taskDAO.QueryById(taskTest.getId());
		assert taskBuscada == null : "Se ha encontrado una tarea borrada";
		
		assert userDAO.Delete(propietarioTest.getEmail()) > 0 : "No se ha borrado el usuario de pruebas";
		
		UserDTO user = userDAO.QueryByEmail(propietarioTest.getEmail());
		assert user == null : "Se ha encontrado un usuario borrado";
	}
}
