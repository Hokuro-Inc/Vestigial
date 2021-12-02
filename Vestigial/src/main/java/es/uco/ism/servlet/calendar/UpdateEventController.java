package es.uco.ism.servlet.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import es.uco.ism.display.EventBean;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class UpdateEventController
 */
public class UpdateEventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateEventController() {
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
		String nextPage ="View/Calendar/createEvent.jsp"; 
		String mensajeNextPage = "";
		if (login) {
			//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
			
			String idEvent = request.getParameter("idEvent");
			String nameEvent = request.getParameter("nameEvent");
			String dataJson = request.getReader().readLine();
			JSONObject objJson = null;
			if (dataJson != null) {
				objJson = new JSONObject(dataJson);
				if (!objJson.isEmpty()) {
					nameEvent = (String) objJson.get("nameEvent");
				}
			}
			if (nameEvent != null  && !nameEvent.equals("")) {
				// Venimos de la vista por lo cual debemos de agregar el evento al usuario y regresarlo al controlador de calendario.
				String descriptionEvent;
				String startEventDate;
				String endEventDate;
				if (objJson != null ) {
					descriptionEvent = (String) objJson.get("descriptionEvent");
					startEventDate = (String) objJson.get("startEvent");
					endEventDate = (String) objJson.get("endEvent");
				}
				else {
					descriptionEvent = request.getParameter("descriptionEvent");
					startEventDate = request.getParameter("startEvent");
					endEventDate = request.getParameter("endEvent");
				}
				
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				Date startEvent = null;
				try {
					startEvent = inputFormat.parse(startEventDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date endEvent = null ;
				try {
					endEvent = outputFormat.parse(endEventDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				EventDTO updateEvent = new EventDTO (idEvent, usuario.getEmail(), startEvent, endEvent, nameEvent, descriptionEvent);
				if (eventDAO.Update(updateEvent) <=0 )  {
					mensajeNextPage = "Ha surgido un problema a la hora de actualizar el evento";
					nextPage = "View/Calendar/createEvent.jsp";
				}
				else {
					session.removeAttribute("EventToUpdate");
					nextPage = "Calendar";
					mensajeNextPage = "Se ha actualizado correctamente";
				}
			}
			else {
				// Tenemos que dirigirnos a la vista
				// Debemos de buscar el evento y enviar a la vista los datos de el anteriores.
				if (idEvent != null  && !idEvent.equals("")) {
					EventDTO eventToUpdate = eventDAO.QueryById(idEvent);
					EventBean eventBean = new EventBean();
					eventBean.setEvent(eventToUpdate);
					nextPage = "View/Calendar/createEvent.jsp";
					session.setAttribute("EventToUpdate", eventBean);
				}
				else {
					mensajeNextPage = "ACCESO NO PERMITIDO, no se ha suministrado la ID del evento a modificar";
				}
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
