package es.uco.ism.servlet.agenda;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import es.uco.ism.business.contact.ContactDTO;
import es.uco.ism.data.ContactDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class CreateContactController
 */
public class CreateContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateContactController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url_bd = request.getServletContext().getInitParameter("URL");
		String username_bd = request.getServletContext().getInitParameter("USER");
		String password_bd = request.getServletContext().getInitParameter("PASSWORD");
		String sql = request.getServletContext().getInitParameter("sql");
		
		ServletContext application = getServletContext();
		InputStream myIO = application.getResourceAsStream(sql);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		UserBean usuario = (UserBean) session.getAttribute("userBean");
		
		Boolean login = usuario != null && !usuario.getEmail().equals("");
		
		RequestDispatcher disparador = null;
		
		ContactDAO contactDAO = new ContactDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Agenda/CreateContact.jsp"; 
		String mensajeNextPage = "";
		
		if (login) {
			//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
			
			String phone = request.getParameter("phone");
			String dataJson = request.getReader().readLine();
			JSONObject objJson = null;
			if (dataJson != null) {
				objJson = new JSONObject(dataJson);
				if (!objJson.isEmpty()) {
					phone = (String) objJson.get("phone");
				}
			}
			if (phone != null  && !phone.equals("")) {
				// Venimos de la vista por lo cual debemos de agregar el contacto al usuario y regresarlo al controlador de calendario.
				
				String prefix ;
				String name ;
				String surname ;
				String alias ;
				String email ;
				String description ;
				String address;
				String owner;
				
				if (objJson !=null) {
					prefix = (String) objJson.get("prefix");
					name = (String) objJson.get("name");
					surname = (String) objJson.get("surname");
					alias = (String) objJson.get("alias");
					email = (String) objJson.get("email");
					description = (String) objJson.get("description");
					address = (String) objJson.get("address");
					owner = (String) objJson.get("owner");
				}
				else {
					prefix = request.getParameter("prefix");
					name = request.getParameter("name");
					surname = request.getParameter("surname");
					alias = request.getParameter("alias");
					email = request.getParameter("email");
					description = request.getParameter("description");
					address = request.getParameter("address");
					owner = request.getParameter("owner");
				}
				//Los grupos se deberían de poder coger de alguna forma pero esto dependerá de la vista
				
				ContactDTO newContact = new ContactDTO (phone,prefix,name,surname,alias,email,description,address,owner);
				if (contactDAO.Insert(newContact) <=0 )  {
					mensajeNextPage = "Ha surgido un problema a la hora de crear el contacto";
					nextPage = "View/Agenda/CreateContact.jsp";
				}
				else {
					mensajeNextPage = "Se ha creado el contacto correctamente";	
					nextPage = "Agenda";
				}
			}
			else {
				// Tenemos que dirigirnos a la vista
				// No se si necesitamos enviarle algo a la vista de crear contacto.
				nextPage = "View/Agenda/CreateContact.jsp";
			}				
		}
		else{
			// No se encuentra logueado, mandamos a la pagina de login.
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado. ACCESO NO PERMITIDO";
		}
		
		
		disparador = request.getRequestDispatcher(nextPage);
		request.setAttribute("mensaje", mensajeNextPage);
		disparador.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
