<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="LINCARBEAN_UserBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Evento</title>
</head>
<body>
	<% 
		boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
		String nextPage = "CONTROLADOR-CREATEEVENT" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
		
		if (!logged) {

		// Mostrarmos el formulario para crear el evento
	%>
			<h1>Crear Evento Vestigial</h1>
			<h2><%= menssageNextPage %></h2>
				<form method="post" action="CONTROLADOR-CREATEEVENT" id="CreateEventForm">
					<input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" >
					<input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" required>

					<input id= "startEvent" type="datetime" name="startEvent" placeholder="Strat Date" required>
					<input id= "endEvent" type="datetime" name="endEvent" placeholder="endEvent" >


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