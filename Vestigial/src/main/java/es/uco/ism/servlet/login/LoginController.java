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
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginController() {
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
		String nextPage ="LOGIN"; 
		
		
		if (!login) {
			
			String UserEmail = request.getParameter("Email");
			String UserPassword = request.getParameter("Password");

			if (UserEmail != null) {
				
				
				UserDTO userDTO = userDAO.QueryByEmail(UserEmail);
				String saltPassword = userDTO.getSalt();
				
				String passwordHash = PasswordHashing.createHash(UserPassword, saltPassword);
				
		
				if (passwordHash.equals(userDTO.getPwd())) {
					usuario = new UserBean ();
					usuario.setEmail(UserEmail);
					session.setAttribute("userBean", usuario);
					nextPage = "/Home"; //mira redireccion
					disparador = request.getRequestDispatcher(nextPage);
				}
				else {
					nextPage = "LOGIN";
					System.out.println("Contrase�a incorrecta");
					disparador = request.getRequestDispatcher(nextPage);
					String mensajeNextPage = "Error de Contrase�a, Intentelo de Nuevo";
					request.setAttribute("mensaje", mensajeNextPage);
				}
			}
			else {
				nextPage = "LOGIN";
				disparador = request.getRequestDispatcher(nextPage);
				String mensajeNextPage = "Rellene los campos con su email y contrase�a para acceder";
				request.setAttribute("mensaje", mensajeNextPage);
			}
		}
		else {
			//Se encuentra logueado deber� de acceder al Home.
			//nextPage = "index.jsp";
			//disparador = request.getRequestDispatcher(nextPage);
			
			request.getSession().removeAttribute("usuario");
					
			
			nextPage = "/index.jsp";//mirar redireccion
			disparador = request.getRequestDispatcher(nextPage);
			
		}
	}

	
	disparador.forward(request, response);
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
