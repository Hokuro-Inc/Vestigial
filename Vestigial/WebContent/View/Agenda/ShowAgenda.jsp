<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.contact.ContactDTO,java.util.ArrayList" %>

<jsp:useBean  id="UsuarioInfoBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="Agenda" scope="session" class="es.uco.ism.display.AgendaBean"></jsp:useBean> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Agenda</title>
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
            ArrayList<ContactDTO> agenda = Agenda.getContacts();
            if (agenda.size() != 0) {
                //Deseo modificar un Contacto     
                
   
                for (int i = 0; i < agenda.size() ; i++) {
                 %>
                    <div>
                    <h1>Contacto <%= i+1%></h1>
                    <h2><%= menssageNextPage %></h2>
                        <form method="post" action="CONTROLADOR-UPDATE_CONTACT" id="UpdateContactForm">
                            <input id= "phone" type="hidden" name="phone" value ="<%=agenda[i].getPhone()%>" >
                            <input id= "prefix" type="text" name="prefix" placeholder="<%=agenda[i].getPrefix()%>" required>
                            <input id= "name" type="text" name="name" placeholder="<%=agenda[i].getName()%>" required>
                            <input id= "surname" type="text" name="surname" placeholder="<%=agenda[i].getSurname()%>" required>
                            <input id= "alias" type="text" name="alias" placeholder="<%=agenda[i].getAlias()%>" required>
                            <input id= "email" type="email" name="email" placeholder="<%=agenda[i].getEmail()%>" required>
                            <input id= "description" type="text" name="description" placeholder="<%=agenda[i].getDescription()%>" required>
                            <input id= "address" type="text" name="address" placeholder="<%=agenda[i].getAddress()%>" required>
                         <!--   <input id= "groups" type="text" name="groups" placeholder="<%=agenda[i].getName()%>" required> -->
                        </form>
                    </div>
            <%  }
            }else{
                //No se tienen contactos todavia %>
                <h1>Buenas todavia no tiene contactos si desea agregar uno acceda a crear contacto</h1>
                
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