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
		String nextPage ="View/Bloc/ModifyBloc.jsp";
		String mensajeNextPage = "";

		String dataJson = request.getReader().readLine();
		
		String name = null;
		BlocDTO bloc = null;
		String text = null;
		if (dataJson != null) {
			//Provenimos de la vista Movil
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
					name = (String) objJson.get("name");
					text = (String) objJson.get("text");
					bloc = new BlocDTO(name,usuarioActual,text);
					BlocDTO blocAntiguo = blocDAO.QueryByName(bloc);
					System.out.println("HOLA");
					if (blocAntiguo == null) {
						System.out.println("ESTOY A NULL");
					}
					if (text != null && !text.equals("") ) {
						if (blocAntiguo != null) {
							// Ya existe por lo que actualizamos
							if (blocDAO.Update(bloc) > 0 ) {
								mensajeResultado = "[OK]Se ha actualizado correctamente el bloc" + name;
							}else {
								mensajeResultado = "[ERROR]Ha ocurrido un error al actualizar el bloc " + name;
							}
						}else {
							
							if (blocDAO.Insert(bloc) > 0 ) {
								mensajeResultado = "[OK]Se ha creado correctamente el bloc" + name;
							}else {
								mensajeResultado = "[ERROR]Ha ocurrido un error al crear el bloc " + name;
							}
						}
						jsonDataEnviar.put("Mensaje", mensajeResultado);
					}
					else {
						//Enviamos la informacion necesaria a la vista de modificar
						jsonDataEnviar.put("updateContact", blocAntiguo);
						mensajeResultado = "[OK]Se envia la informacion del bloc para editar " + name;
					}	
				}
				out.print(jsonDataEnviar);
				out.close();
			}
		}
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				
				name = request.getParameter("name");
				text = request.getParameter("text");
				bloc = new BlocDTO(usuario.getEmail(),name,text);
				
				if (text != null && !text.equals("")) {
					BlocDTO blocAntiguo = blocDAO.QueryByName(bloc);
					if (blocAntiguo == null) {
						// No lo tenia creado todavia, lo insertamos
						bloc = new BlocDTO(usuario.getEmail(),name, text);
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
				nextPage = "View/Bloc/ModifyBloc.jsp";
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
