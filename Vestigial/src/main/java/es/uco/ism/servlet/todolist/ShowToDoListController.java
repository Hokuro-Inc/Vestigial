package es.uco.ism.servlet.todolist;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.ism.business.task.TaskDTO;
import es.uco.ism.data.TaskDAO;
import es.uco.ism.display.UserBean;


/**
 * Servlet implementation class ShowToDoListController
 */
@WebServlet("/ShowToDoListController")
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
		
		TaskDAO taskDAO = new TaskDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_TO_DO_LIST"; 
		String mensajeNextPage = "";
		
		if (login) {
			//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
			String idLista = request.getParameter("idLista");
			if (idLista != null && !idLista.equals("") ) {
				//Supongo que solicitaremos todos los eventos del usuario para mostrar no?
				ArrayList <TaskDTO> listaTareas = taskDAO.QueryByOwnerAndLabel(usuario.getEmail(),idLista);
				
				ToDoListBean listaTareasUsuario = new ToDoListBean ();
				
				listaTareasUsuario.setToDoList (listaTareas);
				
				session.setAttribute("TodoList", listaTareasUsuario);
				session.removeAttribute("ListadoListas");
				nextPage = "MOSTRAR_CONTENIDO_LISTA";
			}
			else {
				//Sigfica que queremos mostrar el conjunto de listas de tareas que tiene el usuario.
				
				ArrayList<String> listaTareas = taskDAO.QueryListsByOwner(usuario.getEmail());
				
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
