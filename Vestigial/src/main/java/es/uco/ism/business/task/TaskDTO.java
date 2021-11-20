package es.uco.ism.business.task;

import java.io.Serializable;

public class TaskDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String owner;
	private String name;
	private String description;
	private Status status;
	private String list;

	/**
	 * Constructor completo de la tarea 
	 * 
	 * @param id Id de la tarea
	 * @param owner Propietario de la tarea
	 * @param name Nombre de la tarea
	 * @param description Descripción de la tarea
	 * @param status Estado de la tarea
	 */
	public TaskDTO(String id, String owner, String name, String description, Status status, String list) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.status = status;
		this.list = list;
	}

	/**
	 * Devuelve el id de la tarea
	 * 
	 * @return Id de la tarea
	 */
	public String getId() {
		return id;
	}

	/**
	 * Asigna un id a una tarea
	 * 
	 * @param id Id a asignar en el evento
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el propietario de la tarea
	 * 
	 * @return Propietario de la tarea
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Asigna un propietario a una tarea
	 * 
	 * @param owner Propietario a asignar en el evento
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Devuelve el nombre de la tarea
	 * 
	 * @return Nombre de la tarea
	 */
	public String getName() {
		return name;
	}

	/**
	 * Asigna un nombre a una tarea
	 * 
	 * @param name Nombre a asignar a la tarea
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Devuelve la descripción de la tarea
	 * 
	 * @return Descripción de la tarea
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Asigna una descripción a una tarea
	 *
	 * @param description Descripción a asignar a la tarea
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Devuelve el estado de la tarea
	 * 
	 * @return Estado de la tarea
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Asigna un estado a una tarea
	 *
	 * @param status Estado a asignar a la tarea
	 */
	public void setStatus(String status) {
		this.status = Status.valueOf(status);
	}
	
	public String getList() {
		return list;
	}
	
	public void setList(String list) {
		this.list = list;
	}


	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "TaskDTO [id=" + id + ", owner=" + owner + ", name=" + name
				+ ", description=" + description + ", status=" + status + "]";
	}
	
}