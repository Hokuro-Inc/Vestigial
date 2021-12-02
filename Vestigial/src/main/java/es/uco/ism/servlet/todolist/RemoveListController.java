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

import es.uco.ism.data.ListDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class RemoveListController
 */
public class RemoveListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveListController() {
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
		ListDAO toDoListDAO = new ListDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="ShowToDoList"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String idLista;
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			if (!objJson.isEmpty()) {
				objJson = new JSONObject(dataJson);
				response.setContentType("application/json");
				JSONObject jsonDataEnviar = null;
				PrintWriter out = response.getWriter();
				jsonDataEnviar = new JSONObject();
				String mensajeResultado = null;
				idLista = (String) objJson.get("idLista");
				if (idLista != null && !idLista.equals("")) {			
					if (toDoListDAO.Delete(idLista) <= 0) {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de borrar la tarea"  + idLista;
					}
					else {
						mensajeResultado = "[OK]Se ha borrado correctamente la tarea" + idLista;
					}
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				idLista = request.getParameter("idLista");
				if (idLista != null && !idLista.equals("")) {			
					if (toDoListDAO.Delete(idLista) <= 0) {
						mensajeNextPage = "Se ha borrado correctamente la tarea con ID -> " + idLista;
					}
					else {
						mensajeNextPage = "Ha surgido un problema al borrar la tarea " + idLista;
					}
					
					nextPage = "ShowToDoList";
				}
				else {
					// Tenemos que dirigirnos a la vista
					// No se si necesitamos enviarle algo a la vista de crear la lista.
					nextPage = "View/ToDoList/createTask.jsp";
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
