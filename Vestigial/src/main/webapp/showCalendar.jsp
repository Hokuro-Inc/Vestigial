<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.event.EventDTO,java.util.ArrayList" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="CalendarBean" scope="session" class="es.uco.ism.display.CalendarBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendar</title>
</head>
<body>
	<% 
		boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getDni().equals("");
		String nextPage = "CONTROLADOR-LOGIN" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
		
		if (logged) {
			
			ArrayList <EventDTO > listadoEventos = CalendarBean.getEvents();
			for (int i = 0; i <  listadoEventos.size(); i++) {
			
	%>
		
		
	<% 		
			}
		} 

		else {
			//No se encuentra logueado
			nextPage = "Login";
			menssageNextPage = "No se encuenque expone es dedctra logueado";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=menssageNextPage%>" name="message"/>
			</jsp:forward>

		<%} %>
</body>
</html>