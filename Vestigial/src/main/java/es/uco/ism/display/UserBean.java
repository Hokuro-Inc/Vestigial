package es.uco.ism.display;

import java.io.Serializable;

import es.uco.iw.business.user.UserDTO;

public class UserBean implements Serializable {
	
	
    private static final long serialVersionUID = 1L;
	
    private String email = "";
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
