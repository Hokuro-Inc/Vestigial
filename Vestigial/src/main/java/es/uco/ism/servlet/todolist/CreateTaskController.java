package es.uco.ism.servlet.todolist;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import es.uco.ism.business.task.Status;
import es.uco.ism.business.task.TaskDTO;
import es.uco.ism.data.TaskDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class CreateTaskController
 */
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
		String nextPage ="View/ToDoList/createTask.jsp"; 
		String mensajeNextPage = "";
		String nameTask;
		String descriptionTask;
		String idLista;
		String idTask = null;
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				String usuarioActual = (String) objJson.get("user");
				descriptionTask = (String) objJson.get("descriptionTask");
				idLista = (String) objJson.get("idLista");
				nameTask = (String) objJson.get("nameTask");
				if (nameTask != null && !nameTask.equals("")) {
					TaskDTO newTask = new TaskDTO (idTask, usuarioActual, nameTask, descriptionTask, Status.ToDo,idLista);
					if (taskDAO.Insert(newTask) <=0 )  {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de crear la tarea"  + idTask; 
					}
					else {
						mensajeResultado = "[OK]Se ha creado correctamente la tarea" + idTask;
					}
				} else {
					//Enviamos la informacion a la vista de angular
					jsonDataEnviar.put("idLista",idLista);
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				nameTask = request.getParameter("nameTask");
				
				if (nameTask != null  && !nameTask.equals("")) {
					// Venimos de la vista por lo cual debemos de agregar el tarea al usuario y regresarlo al controlador de la lista de tareas.
					
					descriptionTask= request.getParameter("descriptionTask");
					idLista= request.getParameter("idList");
					
					idTask = ""; // Generar ID evento
					TaskDTO newTask = new TaskDTO (idTask, usuario.getEmail(), nameTask, descriptionTask, Status.ToDo,idLista);
					
					if (taskDAO.Insert(newTask) <=0 )  {
						mensajeNextPage = "Ha surgido un problema a la hora de crear la tarea";
						nextPage = "View/ToDoList/createTask.jsp";
					}
					else {
						nextPage = "View/ToDoList/showToDoList.jsp";
					}
				}
				else {
					// Tenemos que dirigirnos a la vista
					// No se si necesitamos enviarle algo a la vista de crear tarea.
					nextPage = "View/ToDoList/createTask.jsp";
					idLista= request.getParameter("idList");
					session.setAttribute("idList", idLista);
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
