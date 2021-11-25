<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.contact.ContactDTO" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="ContactToUpdateBean" scope="session" class="es.uco.ism.display.ContactBean"></jsp:useBean> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Contacto</title>
</head>
<body>
    <% 
        boolean logged = UsuarioInfoBean != null && !UsuarioInfoBean.getEmail().equals("");
        String nextPage = "CONTROLADOR-CREATE_CONTACT" ;
        String menssageNextPage = (String) request.getAttribute("mensaje");
        if (menssageNextPage == null ) menssageNextPage = "";
        
        if (logged) {
            //Se encuentra logueado
            // Mostrarmos el formulario para crear la tarea
            ContactDTO ContactToUpdate = ContactToUpdateBean.getContact();
            if (ContactToUpdate != null) {
                //Deseo modificar un Contacto     
                
    %>
            <h1>Información del contacto <%=ContactToUpdate.getName()%></h1>
            <h2><%= menssageNextPage %></h2>
                <form method="post" action="CONTROLADOR-UPDATE_CONTACT" id="UpdateContactForm">
                    <input id= "phone" type="hidden" name="phone" value ="<%=ContactToUpdate.getPhone()%>" >
                    <input id= "prefix" type="text" name="prefix" placeholder="<%=ContactToUpdate.getPrefix()%>" required>
                    <input id= "surname" type="text" name="surname" placeholder="<%=ContactToUpdate.getSurname()%>" required>
                    <input id= "alias" type="text" name="alias" placeholder="<%=ContactToUpdate.getAlias()%>" required>
                    <input id= "email" type="email" name="email" placeholder="<%=ContactToUpdate.getEmail()%>" required>
                    <input id= "description" type="text" name="description" placeholder="<%=ContactToUpdate.getDescription()%>" required>
                    <input id= "address" type="text" name="address" placeholder="<%=ContactToUpdate.getAddress()%>" required>
                 <!--   <input id= "groups" type="text" name="groups" placeholder="<%=ContactToUpdate.getName()%>" required> -->
                </form>
    <%      }else{
                //Acceso no permitdo
                nextPage = "INDEX";
                menssageNextPage = "[ERROR] Acceso no autorizado";
            %>
            <jsp:forward page="<%=nextPage%>">
                <jsp:param value="<%=menssageNextPage%>" name="message"/>
            </jsp:forward>
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