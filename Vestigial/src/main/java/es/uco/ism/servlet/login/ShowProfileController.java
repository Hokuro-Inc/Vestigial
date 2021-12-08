package es.uco.ism.servlet.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import es.uco.ism.business.contact.ContactDTO;
import es.uco.ism.business.user.UserDTO;
import es.uco.ism.data.ContactDAO;
import es.uco.ism.display.ContactBean;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class ShowProfileController
 */
public class ShowProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowProfileController() {
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
		ContactDAO contactDAO = new ContactDAO (url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Main/loginView.jsp"; 
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		JSONObject objJson = null;
		String userEmail;
		String userPhone ;
		
		if (dataJson != null) {
			objJson = new JSONObject(dataJson);
			response.setContentType("application/json");
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				userEmail = (String) objJson.get("user");
				userPhone = (String) objJson.get("phone");
				ContactDTO contacto = contactDAO.QueryByPhone(userPhone,userEmail);
				System.out.println(contacto);
								
				if (contacto == null) {
					mensajeResultado = "[ERROR] El usuario no existe";
				}else {
					mensajeResultado = "[OK]Se envia la informacion del perfil" + userPhone;
					ArrayList<ContactDTO> perfilInfo = new ArrayList<>();
					perfilInfo.add(contacto);
					
					jsonDataEnviar.put("Profile", perfilInfo);
				}
			}
			jsonDataEnviar.put("Mensaje", mensajeResultado);
			out.print(jsonDataEnviar);
			out.close();
		}else {
			if (!login) {
				userEmail = request.getParameter("user");
				userPhone = request.getParameter("phone");
				ContactDTO contacto = contactDAO.QueryByPhone(userEmail,userPhone);
					// Se debe de ir a la vista
					
				nextPage = "View/Main/registerView.jsp"; 
				ContactBean contactoBean = new ContactBean();
				contactoBean.setContact(contacto);
				mensajeNextPage = "Rellene todos los campos obligatorios para registrarse";
				request.setAttribute("mensaje", mensajeNextPage);
				request.setAttribute("perfil", contactoBean);

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
