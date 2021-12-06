package es.uco.ism.servlet.login;

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

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.display.UserBean;
import es.uco.ism.utils.PasswordHashing;


/**
 * Servlet implementation class RegisterController
 */
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RegisterController() {
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
		UserDAO userDAO = new UserDAO (url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Main/loginView.jsp"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String userEmail;
		String userPrefix ;
		//String userGroups ;
		String userPhone ;
		String userPassword ;
		
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				userEmail = (String) objJson.get("email");
				userPrefix = (String) objJson.get("prefix");
				//userGroups = objJson.get("groups");
				userPhone = (String) objJson.get("phone");
				userPassword = (String) objJson.get("password");
				UserDTO userDTO = userDAO.QueryByEmail(userEmail);
				if (userDTO != null) {
					mensajeResultado = "[ERROR] El usuario existe, ya posee una cuenta";
				}else {

					String salt = PasswordHashing.createSalt();
					String passwordHash = PasswordHashing.createHash(userPassword, salt);					
			
					userDTO = new UserDTO (userEmail, passwordHash, salt, userPhone, userPrefix);
					if (userDAO.Insert(userDTO) > 0 ) {
						mensajeResultado = "[OK]Se ha registrado correctamente el usuario " + userEmail;
					}else {
						mensajeResultado = "[ERROR]Ha ocurrido un error al registrarse " + userEmail;
					}
				}

				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}else {
			if (!login) {
				userEmail = request.getParameter("email");
				
				if (userEmail != null) {

					//Los datos vienen de la vista web
					userPrefix = request.getParameter("prefix");
					//userGroups = request.getParameter("groups");
					userPhone  = request.getParameter("phone"); 
					userPassword = request.getParameter("password");			

					
					String salt = PasswordHashing.createSalt();
					
					String passwordHash = PasswordHashing.createHash(userPassword, salt);
					
					UserDTO userDTO = new UserDTO (userEmail, passwordHash, salt, userPhone, userPrefix);
					userDAO.Insert(userDTO);
					
					nextPage = "index.jsp"; //mirar redireccion
					disparador = request.getRequestDispatcher(nextPage);
					
				}
				else {
					// Se debe de ir a la vista
				
						nextPage = "View/Main/registerView.jsp"; 
						
						mensajeNextPage = "Rellene todos los campos obligatorios para registrarse";
						request.setAttribute("mensaje", mensajeNextPage);
					
				}
				
			}
			else {
				//ya esta logeado, se va al home
				nextPage = "Home";
				disparador = request.getRequestDispatcher("Home"); // mirar
				mensajeNextPage = "Ya estas logeado";

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
