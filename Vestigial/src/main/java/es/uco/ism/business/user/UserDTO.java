package es.uco.ism.business.user;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String pwd;
	private String salt;
	private String phone;
	private String prefix;
	private String[] groups;
	private String[] lists;
 	
	/**
	 * Constructor completo de un usuario para el login
	 * 
	 * @param email Email del usuario
	 * @param password Password del usuario
	 * @param salt Salt del login del usuario
	 * @param phonePrefix Teléfono del usuario
	 */
	public UserDTO(String email, String password, String salt, String phone, String prefix, String[] groups) {
		this.email = email;
		this.pwd = password;
		this.salt = salt;
		this.phone = phone;
		this.prefix = prefix;
		this.groups = groups;
	}
	
	public UserDTO(String email, String password, String salt, String phone, String prefix) {
		this.email = email;
		this.pwd = password;
		this.salt = salt;
		this.phone = phone;
		this.prefix = prefix;
	}

	/**
	 * Devuelve el email de un usuario
	 * 
	 * @return Email del usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna un email a un usuario
	 * 
	 * @param email Email a asignar al usuario
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Devuelve la contraseña de un contato
	 * 
	 * @return Contraseña del usuario
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * Asigna una contraseña a un usuario
	 * 
	 * @param pwd Contraseña a asignar al usuario
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * Devuelve el salt del login de un usuario
	 * 
	 * @return Salt del login de un usuario
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Asigna un salt al login de un usuario
	 * 
	 * @param salt Salt a asignar al login del usuario
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Devuelve el teléfono de un usuario
	 * 
	 * @return Teléfono del usuario
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Asigna un teléfono a un usuario
	 *
	 * @param phonePrefix Teléfono a asignar al usuario
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Devuelve el prefijo del telefono de un usuario
	 * 
	 * @return Prefijo del telefono del usuario
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Asigna un teléfono a un usuario
	 *
	 * @param phonePrefix Teléfono a asignar al usuario
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String[] getGroups() {
		
		return groups;
	}
	
	public void setGroups(String[] groups) {
		
		this.groups = groups;
	}
	
public String[] getLists() {
		
		return lists;
	}
	
	public void setLists(String[] lists) {
		
		this.lists = lists;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "UserDTO [email=" + email + ", pwd=" + pwd + ", salt=" + salt + ", phone=" + phone + ", prefix=" + prefix
				+ ", groups=" + groups + "]";
	}


	
}
