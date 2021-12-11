<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.task.TaskDTO,java.util.ArrayList" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="TaskToUpdateBean" scope="session" class="es.uco.ism.display.TaskBean"></jsp:useBean> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Tarea</title>
</head>
<body>
<% 
        boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
        String nextPage = "CreateTask" ;
        String menssageNextPage = (String) request.getAttribute("mensaje");
        if (menssageNextPage == null ) menssageNextPage = "";
        
        if (logged) {
            //Se encuentra logueado
            // Mostrarmos el formulario para crear la tarea
            if (TaskToUpdateBean.getTask() != null) {
                //Deseo modificar la tarea     
                TaskDTO TaskToUpdate = TaskToUpdateBean.getTask();
    %>
            <h1>Editar Evento Vestigial</h1>
            <h2><%= menssageNextPage %></h2>
                <form method="post" action="UpdateTask" id="UpdateTaskForm">
                    <input id= "idTask" type="hidden" name="idTask" value ="<%=TaskToUpdate.getId()%>" >
                    <input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" value ="<%=TaskToUpdate.getName()%>" required>
                    <input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" value ="<%=TaskToUpdate.getDescription()%>" required>
                    <input class="submit" type="submit" id="submitButton" value="Editar">
                </form>
    <%      }else{
                //Deseo Crear una Tarea
    %>              
                <h1>Crear Evento Vestigial</h1>
                <h2><%= menssageNextPage %></h2>
                    <form method="post" action="CreateTask" id="CreateTaskForm">
                        <input id= "nameEvent" type="text" name="nameEvent" placeholder="nameEvent" required>
                        <input id= "descriptionEvent" type="text" name="descriptionEvent" placeholder="DescriptionEvent" required>
                        <input class="submit" type="submit" id="submitButton" value="Crear Tarea">
                    </form>
    <%      } 
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