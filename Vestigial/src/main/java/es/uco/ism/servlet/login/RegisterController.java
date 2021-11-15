package es.uco.ism.servlet.general;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.utils.PasswordHashing;


/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
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
response.getWriter().append("Served at: ").append(request.getContextPath());
		
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

		Boolean login = usuario != null && !usuario.getDni().equals("");

		RequestDispatcher disparador = null;
		UserDAO userDAO = new UsuarioDAO (url_bd, username_bd, password_bd, prop);
		String nextPage ="REGISTER"; 

		if (!login) {
			String userEmail = request.getParameter("email");
			
			if (userEmail != null) {
				String userPrefix = request.getParameter("prefix");
				//String userGroups = request.getParameter("groups");
				String userPhone  = request.getParameter("phone"); 
				String userPassword = request.getParameter("password");			
				
				String salt = PasswordHashing.createSalt();
				
				String passwordHash = PasswordHashing.createHash(userPassword, salt);
				
				UserDTO userDTO = new UserDTO (userEmail, passwordHash, salt, userPhone, userPrefix);
				
				userDAO.Insert(userDTO);
				
				nextPage = "Home"; //mirar redireccion
				disparador = request.getRequestDispatcher(nextPage);
				
			}
			else {
				// Se debe de ir a la vista
			
					nextPage = "REGISTER"; 
					disparador = request.getRequestDispatcher(nextPage);
					String mensajeNextPage = "Rellene todos los campos obligatorios para registrarse";
					request.setAttribute("mensaje", mensajeNextPage);
				
				
			}
			
		}
		else {
			//ya esta logeado, se va al home
			nextPage = "REGISTER";
			disparador = request.getRequestDispatcher("/Home"); // mirar
			String mensajeNextPage = "Ya estas logeado";
			request.setAttribute("mensaje", mensajeNextPage);
		}
		
		disparador.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
