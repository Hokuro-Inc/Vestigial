<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import ="es.uco.ism.business.user.UserDTO, es.uco.ism.business.contact.ContactDTO,java.util.ArrayList" %>

<jsp:useBean  id="userBean" scope="session" class="es.uco.ism.display.UserBean"></jsp:useBean> 
<jsp:useBean  id="Agenda" scope="session" class="es.uco.ism.display.AgendaBean"></jsp:useBean> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Agenda</title>
</head>
<body>
     <% 
        boolean logged = userBean != null && !userBean.getEmail().equals("");
        String nextPage = "CONTROLADOR-CREATE_CONTACT" ;
        String menssageNextPage = (String) request.getAttribute("mensaje");
        System.out.println("Hola ESTOY EN LA VISTA");
        if (menssageNextPage == null ) menssageNextPage = "";
        
        if (logged) {
        	System.out.println("Mostramos la vista");
            //Se encuentra logueado
            // Mostrarmos el formulario para crear la tarea
            ArrayList<ContactDTO> agenda = Agenda.getContacts();
            if (agenda.size() != 0) {
                //Deseo modificar un Contacto     
                
   				System.out.println(agenda.size());
                for (int i = 0; i < agenda.size() ; i++) {
                 %>
                    <div>
                    <h1>Contacto <%= i+1%></h1>
                    <h2><%= menssageNextPage %></h2>
                        <form method="post" action="UpdateContact" id="UpdateContactForm">
                            <input id= "phone" type="text" name="phone" value ="<%=agenda.get(i).getPhone()%>" >
                            <input id= "prefix" type="text" name="prefix" placeholder="<%=agenda.get(i).getPrefix()%>" required>
                            <input id= "name" type="text" name="name" placeholder="<%=agenda.get(i).getName()%>" required>
                            <input id= "surname" type="text" name="surname" placeholder="<%=agenda.get(i).getSurname()%>" required>
                            <input id= "alias" type="text" name="alias" placeholder="<%=agenda.get(i).getAlias()%>" required>
                            <input id= "email" type="email" name="email" placeholder="<%=agenda.get(i).getEmail()%>" required>
                            <input id= "description" type="text" name="description" placeholder="<%=agenda.get(i).getDescription()%>" required>
                            <input id= "address" type="text" name="address" placeholder="<%=agenda.get(i).getAddress()%>" required>
                         <!--   <input id= "groups" type="text" name="groups" placeholder="<%=agenda.get(i).getName()%>" required> -->
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