package es.uco.ism.business.contact;

import java.io.Serializable;

public class ContactDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String phone;
	private String prefix;
	private String name;
	private String alias;
	private String email;
	private String description;
	private String address;
	private String owner;

	/**
	 * Constructor completo del contacto 
	 * 
	 * @param phonePrefix Teléfono del contacto
	 * @param name Nombre del contacto
	 * @param alias Alias del contacto
	 * @param email Email del contacto
	 * @param description Descripción del contacto
	 * @param address Dirección del contacto
	 * @param owner Propietario del contacto
	 */
	public ContactDTO(String phone, String prefix, String name, String alias, String email, String description, String address, String owner) {
		this.phone = phone;
		this.prefix = prefix;
		this.name = name;
		this.alias = alias;
		this.email = email;
		this.description = description;
		this.address = address;
		this.owner = owner;
	}

	/**
	 * Devuelve el teléfono del contacto
	 * 
	 * @return Teléfono del contacto
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Asigna un teléfono a un contacto
	 * 
	 * @param phonePrefix Teléfono a asignar en el contacto
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Devuelve el prefijo del telefono del contacto
	 * 
	 * @return Prefijo del contacto
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Asigna el prefijo del telefono del contacto
	 * 
	 * @param prefix Prefijo del telefono
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * Devuelve el nombre del contacto
	 * 
	 * @return Nombre del contacto
	 */
	public String getName() {
		return name;
	}

	/**
	 * Asigna un nombre a un contacto
	 * 
	 * @param name Nombre a asignar al contacto
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Devuelve el alias del contacto
	 * 
	 * @return Alias del contacto
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Asigna un alias a un contacto
	 *
	 * @param alias Alias a asignar al contacto
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Devuelve el email del contacto
	 * 
	 * @return Email del contacto
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Asigna un email a un contacto
	 *
	 * @param email Email a asignar al contacto
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Devuelve la descripción del contacto
	 * 
	 * @return Descripción del contacto
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Asigna una descripción a un contacto
	 *
	 * @param description Descripción a asignar al contacto
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Devuelve el direccion del contacto
	 * 
	 * @return Direccion del contacto
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Asigna una direccion a un contacto
	 *
	 * @param address Direccion a asignar al contacto
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Devuelve el propietario del contacto
	 * 
	 * @return Propietario del contacto
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Asigna un propietario a un contacto
	 * 
	 * @param owner Propietario a asignar en el contacto
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "ContactDTO [phone=" + phone + ", prefix=" + prefix + ", name=" + name + ", alias=" + alias + ", email="
				+ email + ", description=" + description + ", address=" + address + ", owner=" + owner + "]";
	}
	
}