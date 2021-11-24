package es.uco.ism.servlet.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.ism.business.event.EventDTO;
import es.uco.ism.data.EventDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class CreateEventController
 */
@WebServlet("/CreateEventController")
public class CreateEventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEventController() {
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
		
		EventDAO eventDAO = new EventDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_CALENDARIO"; 
		String mensajeNextPage = "";
		
		if (login) {
			//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
			
			String nameEvent = request.getParameter("nameEvent");
			if (nameEvent != null  && !nameEvent.equals("")) {
				// Venimos de la vista por lo cual debemos de agregar el evento al usuario y regresarlo al controlador de calendario.
				String descriptionEvent = request.getParameter("descriptionEvent");
				
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				Date startEvent = null;
				try {
					startEvent = inputFormat.parse(request.getParameter("startEvent"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date endEvent = null ;
				try {
					endEvent = outputFormat.parse(request.getParameter("endEvent"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String idEvent = ""; // Generar ID evento
				EventDTO newEvent = new EventDTO (idEvent, usuario.getEmail(), startEvent, endEvent, nameEvent, descriptionEvent);
				if (eventDAO.Insert(newEvent) <=0 )  {
					mensajeNextPage = "Ha surgido un problema a la hora de crear el evento";
					nextPage = "CREAR_EVENTO";
				}

			}
			else {
				// Tenemos que dirigirnos a la vista
				// No se si necesitamos enviarle algo a la vista de crear evento.
				nextPage = "VISTA_CREAR_EVENTO";
			}
						
		}
		else{
			// No se encuentra logueado, mandamos a la pagina de login.
			nextPage = "LOGIN";
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
