package es.uco.ism.business.bloc;

import java.io.Serializable;

public class BlocDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String owner;
	private String text;
	private String name;
	
	/**
	 * Constructor completo para un bloc de notas
	 * 
	 * @param owner Dueño del bloc de notas
	 * @param text Texto del bloc de notas
	 */
	public BlocDTO(String name, String owner, String text) {
		
		this.name = name;
		this.owner = owner;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Devuelve el dueño de un bloc de notas
	 * 
	 * @return Dueño del bloc de notas
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Asigna un dueño al bloc de notas
	 * 
	 * @param owner Dueño a asignar al bloc de notas
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Devuelve el texto de un bloc de notas
	 * 
	 * @return Texto del bloc de notas
	 */
	public String getText() {
		return text;
	}

	/**
	 * Asigna un texto a un bloc de notas
	 * 
	 * @param text Texto a asigna al bloc de notas
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "BlocDTO [name=" + name + ", owner=" + owner + ", text=" + text + "]";
	}
	
}
