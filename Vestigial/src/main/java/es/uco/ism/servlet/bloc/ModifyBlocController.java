package es.uco.ism.servlet.bloc;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uco.ism.business.bloc.BlocDTO;
import es.uco.ism.data.BlocDAO;
import es.uco.ism.display.BlocBean;
import es.uco.ism.display.UserBean;


/**
 * Servlet implementation class ModifyBlocController
 */
public class ModifyBlocController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyBlocController() {
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
		String nextPage ="VISTA_MOSTRAR_BLOC";
		String mensajeNextPage = "";
		
		if (login) {
			//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
			
			BlocDTO bloc = blocDAO.QueryByOwner(usuario.getEmail());
			
			String text = request.getParameter("text");
			
			if (text != null && !text.equals("")) {
				if (bloc == null) {
					// No lo tenia creado todavia, lo insertamos
					bloc = new BlocDTO(usuario.getEmail(), text);
					blocDAO.Insert(bloc);
				}
				else {
					// Ya existe por lo que actualizamos
					bloc.setText(text);
					blocDAO.Update(bloc);
				}
			}
			
			BlocBean blocBean = new BlocBean();
			
			blocBean.setBloc(bloc);
			
			session.setAttribute("Bloc", blocBean);
			nextPage = "MOSTRAR_BLOC";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
