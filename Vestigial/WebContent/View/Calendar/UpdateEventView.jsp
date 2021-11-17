<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="LINCARBEAN_UserBean"></jsp:useBean> 

<jsp:useBean  id="EventToUpdate" scope="session" class="LINCARBEAN_UserBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Actualizar Evento</title>
</head>
<body>
	
	<% 
		boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
		String nextPage = "CONTROLADOR-UPDATE_EVENT" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
		
		if (!logged) {

		// Mostrarmos el formulario para crear el evento
	%>
			<h1>Crear Evento Vestigial</h1>
			<h2><%= menssageNextPage %></h2>
				<form method="post" action="CONTROLADOR-UPDATE_EVENT" id="UpdateEventForm">
					<input id= "idEvent" type="hidden" name="nameEvent" value ="<%=EventToUpdate.getId()%>">
					<input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" value ="<%=EventToUpdate.getName()%>" >
					<input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" value ="<%=EventToUpdate.getDescription()%>" required>

					<input id= "startEvent" type="datetime" name="startEvent" placeholder="Strat Date" value ="<%=EventToUpdate.getStartDate()%>" required>
					<input id= "endEvent" type="datetime" name="endEvent" placeholder="endEvent" value ="<%=EventToUpdate.getEndDate()%">

					<input class="submit" type="submit" id="submitButton" value="Iniciar Sesión">
				</form>
	<% 	} 

		else {
			//Se encuentra logueado
			nextPage = "Login";
			mensajeNextPage = "No se encuentra logueado";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=mensajeNextPage%>" name="message"/>
			</jsp:forward>

		<%} %>
</body>
</html>