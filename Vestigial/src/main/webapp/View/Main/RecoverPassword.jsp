<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean>     
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recover Password</title>
</head>
<body>
<% 
		boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
		String nextPage = "RecoverPassword" ;
		String menssageNextPage = (String) request.getAttribute("mensaje");
		if (menssageNextPage == null ) menssageNextPage = "";
	
		if (!logged) {
	%>

			<h1>Recover Password Vestigial</h1>
			<form method="post" action="RecoverPassword" id="Login">
				<input id= "email" type="email" name="email" placeholder="correo@gmail.com" required>
				<input id= "password" type="password" name="new-password" placeholder="Nueva Contraseña" required>
				<input id= "code" type="password" name="confirmation code" placeholder="Code" required>

				<input class="submit" type="submit" id="submitButton" value="Recuperar contraseña">


			</form>
	<% 	}  %>
</body>
</html>