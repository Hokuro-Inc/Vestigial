package es.uco.ism.servlet.todolist;

import java.io.IOException;
import java.io.InputStream;
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

import es.uco.ism.business.task.Status;
import es.uco.ism.business.task.TaskDTO;
import es.uco.ism.data.TaskDAO;

/**
 * Servlet implementation class CreateTaskController
 */
@WebServlet("/CreateTaskController")
public class CreateTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTaskController() {
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
		TaskDAO taskDAO = new TaskDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_FORMULARIO_CREAR_TAREA"; 
		String mensajeNextPage = "";
		if (login) {
			String nameTask = request.getParameter("nameTask");
			if (nameTask != null  && !nameTask.equals("")) {
				// Venimos de la vista por lo cual debemos de agregar el tarea al usuario y regresarlo al controlador de la lista de tareas.
				String descriptionTask= request.getParameter("descriptionTask");
		
				String idTask; // Generar ID evento
				TaskDTO newTask = new TaskDTO (idTask, usuario.getEmail(), nameTask, descriptionTask, Status.InProcess);
				if (taskDAO.Insert(newTask) <=0 )  {
					mensajeNextPage = "Ha surgido un problema a la hora de crear la tarea";
					nextPage = "CREAR_TASk";
				}
				nextPage = "MOSTRAR_LISTA_TAREAS";
			}
			else {
				// Tenemos que dirigirnos a la vista
				// No se si necesitamos enviarle algo a la vista de crear tarea.
				nextPage = "VISTA_CREAR_TASk";
			}
		}
		else{
			// No se encuentra logueado, mandamos a la pagina de login.
			nextPage = "LOGIN";
			mensajeNextPage = "No se encuentra logueado. ACCESO NO PERMITIDO";
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
