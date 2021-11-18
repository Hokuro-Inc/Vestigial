<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrarse</title>
</head>
<body>
	<% 
		boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getDni().equals("");
		String nextPage = "CONTROLADOR-LOGIN" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
		
		if (!logged) {

		
	%>

			<h1>Login Vestigial</h1>
			<h2><%= menssageNextPage %></h2>
			<form method="post" action="CONTROLADOR-LOGIN" id="LoginForm">
				<input id= "email" type="email" name="Email" placeholder="correo@gmail.com" required>
				<input id= "password" type="password" name="Password" placeholder="Contreseña" required>

				<input class="submit" type="submit" id="submitButton" value="Iniciar Sesión">
			</form>
	<% 	} 

		else {
			//Se encuentra logueado
			nextPage = "PAGINA PRINCIPAL";
			menssageNextPage = "Ya se encuentra logueado";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=menssageNextPage%>" name="message"/>
			</jsp:forward>

		<%} %>
</body>
</html>