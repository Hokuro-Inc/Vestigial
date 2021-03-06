package es.uco.ism.servlet.agenda;

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

import es.uco.ism.business.contact.ContactDTO;
import es.uco.ism.data.ContactDAO;
import es.uco.ism.display.ContactBean;
import es.uco.ism.display.UserBean;

/**
 * Servlet implementation class ExportContact
 */
public class ExportContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportContactController() {
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
		
		ContactDAO contactDAO = new ContactDAO(url_bd, username_bd, password_bd, prop);
		String nextPage ="VISTA_MOSTRAR_AGENDA"; 
		String mensajeNextPage = "";
		String phone;
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
				phone = (String) objJson.get("phone");
				String usuarioActual = (String) objJson.get("user");

				ContactDTO contacto = contactDAO.QueryByPhone(phone,usuarioActual);
				if ( contacto == null )  {
					mensajeResultado = "[ERROR]Ha surgido un problema a la hora de exportar el contacto "  + phone; 
				}
				else {
					mensajeResultado = "[OK]Se ha preparado la informacion para exportar el contacto " + phone;
					jsonDataEnviar.put("contactExport", contacto);
				}
			}
			jsonDataEnviar.put("Mensaje", mensajeResultado);
			out.print(jsonDataEnviar);
			out.close();
		}
		else {
			if (login) {
				//Significa que me encuentro logueado, en dicho caso realizaremos las siguientes comprobaciones
				
				phone = request.getParameter("phone");
				

					// Tenemos que dirigirnos a la vista
					// Debemos de buscar el evento y enviar a la vista los datos de el anteriores.
					if (phone != null  && !phone.equals("")) {
						ContactDTO contactExport = contactDAO.QueryByPhone(phone,usuario.getEmail());
						ContactBean contactBean = new ContactBean();
						contactBean.setContact(contactExport);
						nextPage = "VISTA_EXPORTAR_CONTACTO";
						session.setAttribute("ContactToExport", contactBean);
					}
					else {
						mensajeNextPage = "ACCESO NO PERMITIDO, no se ha suministrado la ID del CONTACTO a modificar";
					}
							
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
