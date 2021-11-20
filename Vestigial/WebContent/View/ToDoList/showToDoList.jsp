<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.event.EventDTO,java.util.ArrayList" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="ToDoListBean" scope="session" class="es.uco.ism.display.ToDoListBean"></jsp:useBean> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar To Do list</title>
</head>
<body>  
    <% 
        boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
        String nextPage = "CONTROLADOR-LOGIN" ;
        String menssageNextPage = (String) request.getAttribute("mensaje");
        if (menssageNextPage == null ) menssageNextPage = "";
        
        if (logged) {
            
            ArrayList <ToDoListDTO > listadoListas = ToDoListBean.getToDoList();
            for (int i = 0; i <  listadoListas.size(); i++) {
            
    %>
            <div >
                <h1><%= listadoListas.get(i).getName() %></h1>
            </div>
        
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