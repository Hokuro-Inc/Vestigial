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
import es.uco.ism.data.UserGroupDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class UpdateGroupController
 */
public class UpdateGroupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateGroupController() {
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
		UserGroupDAO userGroupDAO = new UserGroupDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="ShowToDoList"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String oldGroup, newGroup;
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			if (!objJson.isEmpty()) {
				objJson = new JSONObject(dataJson);
				response.setContentType("application/json");
				JSONObject jsonDataEnviar = null;
				PrintWriter out = response.getWriter();
				jsonDataEnviar = new JSONObject();
				String mensajeResultado = null;
				oldGroup = (String) objJson.get("oldGroup");
				newGroup = (String) objJson.get("newGroup");
				
				String user = (String) objJson.get("user");
				UserDTO usuarioInfo = new UserDTO (user, "", "", "", "");
				ArrayList<String> groupUpdate = new ArrayList<> ();
				groupUpdate.add(oldGroup);
				groupUpdate.add(newGroup);
				usuarioInfo.setGroups(groupUpdate);
				if (user != null && !user.equals("")) {			
					if (userGroupDAO.Update(usuarioInfo) <= 0) {
						mensajeResultado = "[ERROR]Ha surgido un problema a la hora de actualizar el grupo "  + oldGroup + " al nombre  " + newGroup;
					}
					else {
						mensajeResultado = "[OK]Se ha actualizado el grupo "  + oldGroup + " al nombre  " + newGroup;
					}
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				oldGroup = request.getParameter("oldgroup");
				newGroup = request.getParameter("newGroup");
				
				if (oldGroup != null && !oldGroup.equals("")) {	
					UserDTO usuarioInfo = new UserDTO (usuario.getEmail(), "", "", "", "");
					ArrayList<String> groupUpdate = new ArrayList<> ();
					groupUpdate.add(oldGroup);
					groupUpdate.add(newGroup);
					usuarioInfo.setGroups(groupUpdate);
					if (userGroupDAO.Update(usuarioInfo) <= 0) {
						mensajeNextPage = "[ERROR]Ha surgido un problema a la hora de actualizar el grupo "  + oldGroup + " al nombre  " + newGroup;
					}
					else {
						mensajeNextPage = "[OK]Se ha actualizado el grupo "  + oldGroup + " al nombre  " + newGroup;
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
