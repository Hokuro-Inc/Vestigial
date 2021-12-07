package es.uco.ism.servlet.calendar;

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

import es.uco.ism.business.event.EventDTO;
import es.uco.ism.data.EventDAO;
import es.uco.ism.display.CalendarBean;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class ShowCalendar
 */
public class ShowCalendarController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowCalendarController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Obtenemos los parametros para poder realizar las conexiones con la base de datos
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
		
		EventDAO eventDAO = new EventDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Calendar/showCalendar.jsp"; 
		String mensajeNextPage = "";
		
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		if (dataJson != null) {
			//Provenimos de la vista Movil
			String usuarioActual = null;
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = new JSONObject();
			objJson = new JSONObject(dataJson);
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				usuarioActual = (String) objJson.get("user");
				PrintWriter out = response.getWriter();
				ArrayList <EventDTO> listadoEventos = eventDAO.QueryByEmail(usuarioActual);
				if (!listadoEventos.isEmpty()) {
					jsonDataEnviar.put("Calendar", listadoEventos);
					mensajeResultado = "[OK]Se han cargado todos los eventos del usuario";
				}
				else {
					jsonDataEnviar.put("Calendar", "[]");
					mensajeResultado = "[OK]El usuario no tiene ningun evento";
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}			
		}	
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				//Supongo que solicitaremos todos los eventos del usuario para mostrar no?
				ArrayList <EventDTO> listadoEventos = eventDAO.QueryByEmail(usuario.getEmail());
				
				CalendarBean calendarioUsuario = new CalendarBean ();
				
				calendarioUsuario.setEvents(listadoEventos);
				
				session.setAttribute("Calendario", calendarioUsuario);
				
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
