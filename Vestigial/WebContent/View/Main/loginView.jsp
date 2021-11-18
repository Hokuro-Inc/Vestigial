<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="LINCARBEAN_UserBean"></jsp:useBean> 

<%%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
	</head>

	<body>
	<% 
		boolean logged = clienteBean != null && !clienteBean.getDni().equals("");
		String nextPage = "CONTROLADOR-LOGIN" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
	
		if (!logged) {
	%>

			<h1>Login Vestigial</h1>
			<form method="post" action="CONTROLADOR-LOGIN" id="LoginForm">
				<input id= "email" type="email" name="Email" placeholder="correo@gmail.com" required>
				<input id= "password" type="password" name="Password" placeholder="Contreseña" required>

				<input class="submit" type="submit" id="submitButton" value="Iniciar Sesión">


			</form>

	<% 	} 
		else {
			//Se encuentra logueado
			nextPage = "PAGINA PRINCIPAL";
			mensajeNextPage = "Ya se encuentra logueado";
			%>
			<jsp:forward page="<%=nextPage%>">
				<jsp:param value="<%=mensajeNextPage%>" name="message"/>
			</jsp:forward>

		<%} %>

	</body>


</html>