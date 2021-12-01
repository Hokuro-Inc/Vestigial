<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.bloc.BlocDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="BlocBean" scope="session" class="es.uco.ism.display.BlocBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Bloc</title>
</head>
<body>
	<% 
        boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
        String nextPage = "Login" ;
        String menssageNextPage = (String) request.getAttribute("mensaje");
        if (menssageNextPage == null ) menssageNextPage = "";
        
        if (logged) {
        	BlocDTO bloc = BlocBean.getBloc();
        	%>
        	<div>
        		<h1>Bloc de notas</h1>
        		<form method="post" action="ModifyBloc" id="ModifyBlocForm">
        			<input id= "text" type="text" name="text" value ="<%=bloc.getText()%>" >
					<input class="submit" type="submit" id="submitButton" value="Guardar bloc">
				</form>
        	</div>
        <%}
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