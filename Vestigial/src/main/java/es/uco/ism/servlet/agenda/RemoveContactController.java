package es.uco.ism.servlet.agenda;

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

import es.uco.ism.data.ContactDAO;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class RemoveContactController
 */
public class RemoveContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveContactController() {
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
		
		ContactDAO contactDAO = new ContactDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="Agenda"; 
		String mensajeNextPage = "";
		String phone;
		String prefix;
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
				String usuarioactual = (String) objJson.get("user");
				phone = (String) objJson.get("phone");
				prefix = (String) objJson.get("prefix");
				phone = phone + "-" + prefix;
				if ( contactDAO.Delete(phone,usuarioactual) <= 0 )  {
					mensajeResultado = "[ERROR]Ha surgido un problema a la hora de borrar el contacto "  + phone; 
				}
				else {
					mensajeResultado = "[OK]Se ha borrado correctamente el contacto " + phone;
				}
			}
			
			jsonDataEnviar.put("Mensaje", mensajeResultado);
			out.print(jsonDataEnviar);
			out.close();
		}
		else {
			if (login) {
				phone = request.getParameter("phone");
				prefix = request.getParameter("prefix");
				//Comprobamos si venimos de la vista movil
				
				if (phone != null  && !phone.equals("")) {
					// Venimos de la vista por lo cual debemos de eliminar el evento del usuario y regresarlo al controlador de calendario.
					
					if (contactDAO.Delete(phone,usuario.getEmail()) < 0 ) {
						mensajeNextPage = "Lo sentimos ha ocurrido un error al borrar el contacto";
					}
					else {
						mensajeNextPage = "Se ha eliminado correctamente";
					}
					
				}
				
			}
			else {
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
