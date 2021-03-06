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
import es.uco.ism.display.TaskBean;

/**
 * Servlet implementation class UpdateTaskController
 */
public class UpdateTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTaskController() {
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
		String nextPage ="VISTA_MOSTRAR_FORMULARIO_ACTUALIZAR_TAREA"; 
		String mensajeNextPage = "";
		
		String nameTask;
		String descriptionTask;
		String idLista;
		String idTask = null;
		String statusTask;
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
				idTask = (String) objJson.get("idTask");
				descriptionTask = (String) objJson.get("descriptionTask");
				idLista = (String) objJson.get("idLista");
				nameTask = (String) objJson.get("nameTask");
				statusTask = (String) objJson.get("statusTask");
				Status estadoTask = Status.valueOf(statusTask) ;
				if (nameTask != null && !nameTask.equals("")) {
					TaskDTO newTask = new TaskDTO (idTask, usuarioActual, nameTask, descriptionTask, estadoTask,idLista);
					if (taskDAO.Update(newTask) <=0 )  {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de actualizar la tarea"  + idTask; 
					}
					else {
						mensajeResultado = "[OK]Se ha creado correctamente la tarea" + idTask;
					}
				} else {
					//Enviamos la informacion a la vista de angular
					TaskDTO taskUpdate = taskDAO.QueryById(idTask); 
					if ( taskUpdate == null )  {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora preparar la informacion para editar la tarea "  + idTask; 
					}
					else {
						mensajeResultado = "[OK]Se ha preparado la informacion para editar la tarea " + idTask;
						jsonDataEnviar.put("task",taskUpdate);
					}
					
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				idTask = request.getParameter("idTask");
				nameTask = request.getParameter("nameTask");

				if (nameTask != null  && !nameTask.equals("")) {
					// Venimos de la vista por lo cual debemos de actualizar el task del usuario y regresarlo al controlador de calendario.

						descriptionTask= request.getParameter("descriptionTask");
						idLista= request.getParameter("idList");
						statusTask = request.getParameter("statusTask");

					Status estadoTask = Status.valueOf(statusTask) ;
					TaskDTO updateTask = new TaskDTO (idTask, usuario.getEmail(), nameTask, descriptionTask, estadoTask, idLista);
					if (taskDAO.Update(updateTask) <=0 )  {
						mensajeNextPage = "Ha surgido un problema a la hora de actualizar la task";
						nextPage = "ACTUALIZAR_TASK";
					}
					else {
						session.removeAttribute("EventToUpdate");
						nextPage = "VISTA_MOSTRAR_CALENDARIO";
						mensajeNextPage = "Se ha actualizado correctamente";
					}
				}
				else {
					// Tenemos que dirigirnos a la vista
					// Debemos de buscar la task y enviar a la vista los datos de el anteriores.
					if (idTask != null  && !idTask.equals("")) {
						TaskDTO taskToUpdate = taskDAO.QueryById(idTask);
						TaskBean taskBean = new TaskBean();
						taskBean.setTask(taskToUpdate);
						nextPage = "VISTA_EDITAR_TASK";
						session.setAttribute("TaskToUpdate", taskBean);
					}
					else {
						mensajeNextPage = "ACCESO NO PERMITIDO, no se ha suministrado la ID de la TASK a modificar";
					}
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
