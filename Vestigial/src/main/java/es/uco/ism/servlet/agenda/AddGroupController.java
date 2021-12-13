package es.uco.ism.servlet.agenda;

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

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.GroupDAO;
import es.uco.ism.data.UserGroupDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class AddGroupController
 */
public class AddGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGroupController() {
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
		GroupDAO groupDAO = new GroupDAO(url_bd, username_bd, password_bd, prop);
		UserGroupDAO userGroupDAO = new UserGroupDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_FORMULARIO_CREAR_GRUPO"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String group;
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				group = (String) objJson.get("group");
				
				String usuarioActual = (String) objJson.get("user");
				
				/*if (groupDAO.QueryByGroup(group) == null) {
					if (groupDAO.Insert(group) <= 0) {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de crear el grupo"  + group;
					}
				}*/

				UserDTO user = new UserDTO (usuarioActual,"","","","");
				ArrayList<String> gruposUsuario = new ArrayList<String>() ;
				gruposUsuario.add(group);
				user.setGroups(gruposUsuario);
				if (userGroupDAO.Insert(user) <= 0) {
					mensajeResultado = "[ERROR]Ha surgido un problema a la hora vincular el grupo "  + group + " al usuario";
				}
				mensajeResultado = "[OK]Se ha creado correctamente el grupo" + group;
				
			}
			jsonDataEnviar.put("Mensaje", mensajeResultado);
			out.print(jsonDataEnviar);
			out.close();
		}
		else {
			if (login) {
				group = request.getParameter("group");
				
				if (group != null && !group.equals("")) {
					
					if (groupDAO.Insert(group) <= 0) {
						mensajeNextPage = "Ha surgido un problema a la hora de crear la lista de tareas";
						nextPage = "VISTA_CREAR_GRUPO";
					}
					else {
						nextPage = "VISTA_MOSTRAR_AGENDA";
					}
				}
				else {
					// Tenemos que dirigirnos a la vista
					// No se si necesitamos enviarle algo a la vista de crear la lista.
					nextPage = "VISTA_CREAR_GRUPO";
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
