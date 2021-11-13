package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.ism.data.GroupDAO;

public class TestGroupDAO {
	public static void main (String[] args ) throws IOException {
		Properties propertiesBD = new Properties();
		FileReader fileReaderBD = new FileReader("WebContent/server.properties");
		propertiesBD.load(fileReaderBD);

		Properties propertiesSQL = new Properties();
		FileReader fileReader = new FileReader("WebContent/sql.properties");
		propertiesSQL.load(fileReader);
		
		System.out.println("Test GroupDAO");
		
		GroupDAO groupDAO = new GroupDAO(propertiesBD.getProperty("URL"), propertiesBD.getProperty("USER"), propertiesBD.getProperty("PASSWORD"), propertiesSQL);
		
		ArrayList <String> listadoGruposAntesDelTest = groupDAO.QueryAll();
		
		assert groupDAO.Insert("Familia") > 0 : "No se ha introcido el grupo Familia" ;
		
		assert groupDAO.Insert("Amigos") > 0 : "No se ha introcido el grupo Familia" ;
		
		assert groupDAO.Insert("Empresa") > 0 : "No se ha introcido el grupo Familia" ;
		
		assert groupDAO.Insert("Departamento") > 0 : "No se ha introcido el grupo Familia" ;
		
		ArrayList <String> listadoGrupos = groupDAO.QueryAll();
		
		assert listadoGrupos.isEmpty() : "No se han leido correctamente los grupos";
		
		assert listadoGrupos.size() == listadoGruposAntesDelTest.size() + 4: "No se han leido todos los grupos";
		
		String oldGroup = "Amigos";
		String newGroup = "Universidad";
		
		assert groupDAO.Update(oldGroup, newGroup) > 0 : "No se ha actualizado el grupo";
		
		assert groupDAO.Delete(newGroup) > 0 : "No se ha eliminado el grupo";
		
		assert groupDAO.Delete("Familia") > 0 : "No se ha eliminado el grupo";

		assert groupDAO.Delete("Empresa") > 0 : "No se ha eliminado el grupo";
		
		assert groupDAO.Delete("Departamento") > 0 : "No se ha eliminado el grupo";
		
		listadoGrupos = groupDAO.QueryAll();

		assert listadoGrupos.size() == listadoGruposAntesDelTest.size(): "No se han eliminado correctamente los grupos de las pruebas";
		
		System.out.println("Fin del test");
	}
}
