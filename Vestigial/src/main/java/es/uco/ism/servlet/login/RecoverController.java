package es.uco.ism.servlet.login;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.UserDAO;
import es.uco.ism.utils.Mail;
import es.uco.ism.utils.PasswordHashing;
/**
 * Servlet implementation class LoginController
 */
public class RecoverController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RecoverController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url_bd = request.getServletContext().getInitParameter("URL");
		String username_bd = request.getServletContext().getInitParameter("USER");
		String password_bd = request.getServletContext().getInitParameter("PASSWORD");
		String sql = request.getServletContext().getInitParameter("sql");
		
		ServletContext application = getServletContext();
		InputStream myIO = application.getResourceAsStream(sql);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);

				
		RequestDispatcher disparador = null;
		UserDAO userDAO = new UserDAO (url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Main/RecoverPassword.jsp"; 
		String mensajeNextPage = "";
		String UserEmail;
		String UserPassword;		
		int UserCode;
		
			UserEmail = request.getParameter("email");
	
				if (UserEmail != null) {
					UserPassword = request.getParameter("password");
					UserCode = Integer.valueOf(request.getParameter("code"));
					UserDTO userDTO = userDAO.QueryByEmail(UserEmail);
					
					if (userDTO == null) {
						//El usuario no existe 
						nextPage = "View/Main/RecoverPassword.jsp";
						mensajeNextPage = "Error de Usuario, Intentelo de Nuevo";
					}
					
					else {			
				
						if (UserCode == userDTO.getCode()) {
							
							String Salt = PasswordHashing.createSalt();
							String NewUserPassword = PasswordHashing.createHash(UserPassword, Salt);
							
							userDTO.setPwd(NewUserPassword);
							userDTO.setSalt(Salt);
							
							userDAO.UpdatePassword(userDTO);
							
							SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
							Date date = new Date(System.currentTimeMillis());
							System.out.println(formatter.format(date));
							
							Mail correo = new Mail(UserEmail, "Vestigial", "<h1>Alerta de seguridad.</h1>"
				            		+ "<h2>Su contrase�a ha sido cambiada con fecha: "+ date +"</h2> + "
				            				+ "<h3>Si sospecha que no ha sido usted quien la ha cambiado contacte con nosotros </h3>");
							
							try {
								correo.sendEmail();
							} catch (MessagingException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							nextPage = "View/Main/RecoverPassword.jsp";
							mensajeNextPage = "Error de Code, Intentelo de Nuevo";
						}
					}
				}
				
				
				else {
					
					request.getSession().removeAttribute("usuario");
					nextPage = "View/Main/RecoverPassword.jsp";
				}
				
				disparador = request.getRequestDispatcher(nextPage);
				request.setAttribute("mensaje", mensajeNextPage);
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
