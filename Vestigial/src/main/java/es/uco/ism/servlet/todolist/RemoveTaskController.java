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

import es.uco.ism.data.TaskDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class RemoveTaskController
 */
public class RemoveTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveTaskController() {
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
		TaskDAO taskDAO = new TaskDAO (url_bd, username_bd, password_bd, prop);
		RequestDispatcher disparador = null;
		String nextPage ="VISTA_MOSTRAR_LISTA"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String idTask;
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			if (!objJson.isEmpty()) {
				objJson = new JSONObject(dataJson);
				response.setContentType("application/json");
				JSONObject jsonDataEnviar = null;
				PrintWriter out = response.getWriter();
				jsonDataEnviar = new JSONObject();
				String mensajeResultado = null;
				idTask = (String) objJson.get("idTask");
				if (idTask != null && !idTask.equals("")) {			
					if (taskDAO.Delete(idTask) <= 0) {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de borrar la tarea"  + idTask;
					}
					else {
						mensajeResultado = "[OK]Se ha borrado correctamente la tarea" + idTask;
					}
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				idTask = request.getParameter("idTask");
				if (idTask != null && !idTask.equals("")) {
					// Deseamos eliminar la tarea de la lista actual.
					if (taskDAO.Delete(idTask) > 0 ) {
						// Significa que se ha borrado correctamente
						mensajeNextPage = "Se ha borrado correctamente la tarea con ID -> " + idTask;
					}
					else {
						mensajeNextPage = "Ha surgido un problema al borrar la tarea " + idTask;
						
					}
					nextPage = "VISTA_MOSTRAR_LISTA";
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
