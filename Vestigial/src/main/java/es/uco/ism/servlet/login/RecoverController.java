package es.uco.ism.servlet.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.MessagingException;
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
		String nextPage ="View/Main/RecoverPassword.jsp"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String UserEmail;
		String UserPassword;		
		int UserCode;
		
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				UserEmail = (String) objJson.get("email");
				UserPassword = (String) objJson.get("password");
				UserCode = (int) objJson.get("code");
				
				UserDTO userDTO = userDAO.QueryByEmail(UserEmail);
				if (userDTO == null) {
					mensajeResultado = "[ERROR] El usuario no existe";
				}else {
						
					if(UserCode == userDTO.getCode()) {
						
						String Salt = PasswordHashing.createSalt();
						String NewUserPassword = PasswordHashing.createHash(UserPassword, Salt);
						
						userDTO.setPwd(NewUserPassword);
						userDTO.setSalt(Salt);
						
						userDAO.UpdatePassword(userDTO);
					}
					
					else {
						
						mensajeResultado = "[ERROR] Codigo Incorrecto";
					}
				}
				jsonDataEnviar.put("Mensaje", mensajeResultado);
				out.print(jsonDataEnviar);
				out.close();
			}
		}else {
			
				UserEmail = request.getParameter("email");
				UserPassword = request.getParameter("password");
				UserCode = Integer.valueOf(request.getParameter("code"));
				
				if (UserEmail != null) {
					
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
				            		+ "<h2>Su contraseña ha sido cambiada con fecha: "+ date +"</h2> + "
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
		
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
