package es.uco.ism.servlet.bloc;

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

import es.uco.ism.business.bloc.BlocDTO;
import es.uco.ism.data.BlocDAO;
import es.uco.ism.display.BlocBean;
import es.uco.ism.display.UserBean;


/**
 * Servlet implementation class ShowBlocController
 */
public class ShowBlocController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowBlocController() {
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
		
		BlocDAO blocDAO = new BlocDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="View/Bloc/showBloc.jsp";
		String mensajeNextPage = "";
		String dataJson = request.getReader().readLine();
		
		if (dataJson != null) {
			String usuarioActual = null;
			response.setContentType("application/json");
			JSONObject objJson = new JSONObject(dataJson);
			JSONObject jsonDataEnviar = null;
			PrintWriter out = response.getWriter();
			jsonDataEnviar = new JSONObject();
			String mensajeResultado = null;
			if (!objJson.isEmpty()) {
				usuarioActual = (String) objJson.get("user");
				if (usuarioActual != null && !usuarioActual.equals("") ) {
					ArrayList<BlocDTO> blocs = blocDAO.QueryByOwner(usuarioActual);
					if (blocs.isEmpty()) {
						mensajeResultado = "[ERROR]No se posee ningún bloc de notas";
					}
					else {
						jsonDataEnviar.append("Blocs", blocs);						
						mensajeResultado = "[OK]Se estan enviando los blocs del usuario " + usuarioActual;
					}
					jsonDataEnviar.append("Mensaje", mensajeResultado);
				}
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				ArrayList<BlocDTO> bloc = blocDAO.QueryByOwner(usuario.getEmail());
				
				BlocBean blocBean = new BlocBean();
				
				blocBean.setBlocs(bloc);
				
				session.setAttribute("Bloc", blocBean);
				nextPage = "View/Bloc/showBloc.jsp";
			}
			else{
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
