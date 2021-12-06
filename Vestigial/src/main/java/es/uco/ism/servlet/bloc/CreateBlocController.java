package es.uco.ism.servlet.bloc;

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

import es.uco.ism.business.bloc.BlocDTO;
import es.uco.ism.data.BlocDAO;
import es.uco.ism.display.UserBean;

public class CreateBlocController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public CreateBlocController() {
		
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
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
		
		BlocDAO blocDAO = new BlocDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Bloc/CreateBloc.jsp"; 
		String mensajeNextPage = "";
		String user ;
		String name ;
		String text ;
		
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
				user = usuario.getEmail();
				name = (String) objJson.get("name");
				text = (String) objJson.get("text");
				
				BlocDTO newBloc = new BlocDTO (name, user, text);
				if (blocDAO.Insert(newBloc) <=0 )  {
					mensajeResultado = "[ERROR]Ha surgido un problema a la hora de crear la nota "  + name; 
				}
				else {
					mensajeResultado = "[OK]Se ha creado correctamente la nota " + name;
				}
			}
			jsonDataEnviar.put("Mensaje", mensajeResultado);
			out.print(jsonDataEnviar);
			out.close();
		}
		
		else {
			if (login) {

				name = request.getParameter("name");
				
				if (name != null  && !name.equals("")) {
					
					name = request.getParameter("name");
					text = request.getParameter("text");
					user = usuario.getEmail();
					
					BlocDTO newBloc = new BlocDTO (name, user, text);
					if (blocDAO.Insert(newBloc) <=0 )  {
						mensajeNextPage = "Ha surgido un problema a la hora de crear la nota"; 
						nextPage = "View/Bloc/CreateBloc.jsp";
					}
					else {
						mensajeNextPage = "Se ha creado la nota correctamente";	
						nextPage = "ShowBloc";
					}
				}
				else {
					
					nextPage = "View/Bloc/CreateBloc.jsp";
				}				
			}
			else{

				nextPage = "Login";
				mensajeNextPage = "No se encuentra logueado. ACCESO NO PERMITIDO";
			}
			
			
			disparador = request.getRequestDispatcher(nextPage);
			request.setAttribute("mensaje", mensajeNextPage);
			disparador.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
