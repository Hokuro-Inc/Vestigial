package es.uco.ism.servlet.agenda;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import es.uco.ism.display.AgendaBean;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class ShowAgendaController
 */
public class ShowAgendaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAgendaController() {
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
		
		String mensajeNextPage = "";
		ContactDAO contactDAO = new ContactDAO(url_bd, username_bd, password_bd, prop);
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		if (dataJson != null) {
			//Provenimos de la vista Movil
			String usuarioActual = null;
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			objJson = new JSONObject(dataJson);
			if (!objJson.isEmpty()) {
				usuarioActual = (String) objJson.get("user");
				PrintWriter out = response.getWriter();
				ArrayList <ContactDTO> listadoContactos = contactDAO.QueryByOwner(usuarioActual);
				if (!listadoContactos.isEmpty()) {
					jsonDataEnviar = new JSONObject();
					jsonDataEnviar.append("Agenda", listadoContactos);
					out.print(jsonDataEnviar);
				}
				out.close();
			}	
			
		}
		else {
			UserBean usuario = (UserBean) session.getAttribute("userBean");
			
			Boolean login = usuario != null && !usuario.getEmail().equals("");
			
			RequestDispatcher disparador = null;
			
			
			String nextPage ="View/Agenda/ShowAgenda.jsp"; 
			
			//Provenimos de la vista WEB	
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				//Supongo que solicitaremos todos los eventos del usuario para mostrar no?
				ArrayList <ContactDTO> listadoContactos = contactDAO.QueryByOwner(usuario.getEmail());
				
				AgendaBean agendaUsuario = new AgendaBean ();
				agendaUsuario.setContacts(listadoContactos);				
				session.setAttribute("Agenda", agendaUsuario);
				System.out.println("Hola Tengo que ir a la vista");
				System.out.println("num contactos" + listadoContactos.size());
				
				nextPage ="View/Agenda/ShowAgenda.jsp"; 
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
