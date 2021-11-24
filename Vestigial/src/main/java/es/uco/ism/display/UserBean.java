package es.uco.ism.display;

import java.io.Serializable;

import es.uco.ism.business.user.UserDTO;

public class UserBean implements Serializable {
	
	
    private static final long serialVersionUID = 1L;
	
    private String email = "";
	
    private UserDTO user = null;
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
}