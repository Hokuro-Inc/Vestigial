package es.uco.ism.servlet.todolist;

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

import es.uco.ism.business.task.*;
import es.uco.ism.data.TaskDAO;
import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.UserListDAO;
import es.uco.ism.display.ToDoListBean;
import es.uco.ism.display.UserBean;


/**
 * Servlet implementation class ShowToDoListController
 */
public class ShowToDoListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowToDoListController() {
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
		String idLista;
		TaskDAO taskDAO = new TaskDAO(url_bd, username_bd, password_bd, prop);
		UserListDAO userlistDAO = new UserListDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_TO_DO_LIST"; 
		String mensajeNextPage = "";
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
				idLista = (String) objJson.get("idLista");
				TaskDTO task = new TaskDTO(" ", usuarioActual, " ", " ", Status.Done, idLista);
				UserDTO user = new UserDTO(usuarioActual, "", "", "", "");
				
				if (idLista != null && !idLista.equals("") ) {
					//Debemos de dar las tareas de la lista deseada
					ArrayList <TaskDTO> listaTareas = taskDAO.QueryByOwnerAndLabel(task);
					jsonDataEnviar.put("ToDoList",listaTareas);
					mensajeResultado = "[OK]Se devuelven todas las tareas de la lista " + idLista;
				}
				else {
					//Debemos de dar todos los nombres de las listas del usuario
					ArrayList<String> listaTareas = userlistDAO.QueryByUser(user);
					jsonDataEnviar.put("ToDoLists",listaTareas);
					mensajeResultado = "[OK]Se devuelven todas las listas de tareas " + listaTareas.size();
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
			
		}
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				idLista = request.getParameter("idLista");
				TaskDTO task = new TaskDTO(" ", usuario.getEmail(), " ", " ", Status.Done, idLista);
				UserDTO user = new UserDTO(usuario.getEmail(), "", "", "", "");
				
				if (idLista != null && !idLista.equals("") ) {
					//Supongo que solicitaremos todos los eventos del usuario para mostrar no?
					ArrayList <TaskDTO> listaTareas = taskDAO.QueryByOwnerAndLabel(task);
					
					ToDoListBean listaTareasUsuario = new ToDoListBean ();
					
					listaTareasUsuario.setListTask (listaTareas);
					
					session.setAttribute("TodoList", listaTareasUsuario);
					session.removeAttribute("ListadoListas");
					nextPage = "MOSTRAR_CONTENIDO_LISTA";
				}
				else {
					//Sigfica que queremos mostrar el conjunto de listas de tareas que tiene el usuario.
					
					ArrayList<String> listaTareas = userlistDAO.QueryByUser(user);
					
					ToDoListBean listaTareasUsuario = new ToDoListBean();
					
					listaTareasUsuario.setToDoList(listaTareas);
					
					session.setAttribute("ListadoListas", listaTareasUsuario);
					nextPage = "MOSTRAR_TODAS_LAS_LISTAS";
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
