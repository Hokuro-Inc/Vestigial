<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO,es.uco.ism.business.event.EventDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="EventToUpdateBean" scope="session" class="es.uco.ism.display.EventBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Evento</title>
</head>
<body>
	<% 
		boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
		String nextPage = "CONTROLADOR-CREATEEVENT" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
		
		if (logged) {
			//Se encuentra logueado
			// Mostrarmos el formulario para crear el evento
			if (EventToUpdateBean != null) {
				//Deseo modificar el evento		
				EventDTO EventToUpdate = EventToUpdateBean.getEvent();
	%>
			<h1>Editar Evento Vestigial</h1>
			<h2><%= menssageNextPage %></h2>
				<form method="post" action="CONTROLADOR-UPDATE_EVENT" id="UpdateEventForm">
					<input id= "idEvent" type="hidden" name="idEvent" value ="<%=EventToUpdate.getId()%>" >
					<input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" value ="<%=EventToUpdate.getName()%>" >
					<input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" value ="<%=EventToUpdate.getDescription()%>" required>

					<input id= "startEvent" type="datetime" name="startEvent" placeholder="Strat Date" value ="<%=EventToUpdate.getStartDate()%>" required>
					<input id= "endEvent" type="datetime" name="endEvent" placeholder="endEvent" value ="<%=EventToUpdate.getEndDate()%>" >

					<input class="submit" type="submit" id="submitButton" value="Editar">
				</form>
	<% 		}else{
				//Deseo Crear un Evento
	%>				
				<h1>Crear Evento Vestigial</h1>
				<h2><%= menssageNextPage %></h2>
					<form method="post" action="CONTROLADOR-CREATEEVENT" id="CreateEventForm">
						<input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" >
						<input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" required>
	
						<input id= "startEvent" type="datetime" name="startEvent" placeholder="Strat Date" required>
						<input id= "endEvent" type="datetime" name="endEvent" placeholder="endEvent" >
						<input class="submit" type="submit" id="submitButton" value="Crear Evento">
					</form>
	<% 		} 
		}
		else {
			//No se encuentra logueado
			nextPage = "Login";
			menssageNextPage = "No se encuentra logueado";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=menssageNextPage%>" name="message"/>
			</jsp:forward>

		<%} %>
</body>
</html>