package es.uco.ism.business.event;

import java.io.Serializable;
import java.util.Date;

public class EventDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String owner;
	private Date start;
	private Date end;
	private String name;
	private String description;

	/**
	 * Constructor completo del evento 
	 * 
	 * @param id Id del evento
	 * @param owner Propietario del evento
	 * @param start Fecha de inicio del evento
	 * @param end Fecha de fin del evento
	 * @param name Nombre del evento
	 * @param description Descripción del evento
	 */
	public EventDTO(String id, String owner, Date start, Date end, String name, String description) {
		this.id = id;
		this.owner = owner;
		this.start = start;
		this.end = end;
		this.name = name;
		this.description = description;
	}

	/**
	 * Devuelve el id del evento
	 * 
	 * @return Id del evento
	 */
	public String getId() {
		return id;
	}

	/**
	 * Asigna un id a un evento
	 * 
	 * @param id Id a asignar en el evento
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el propietario del evento
	 * 
	 * @return Propietario del evento
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Asigna un propietario a un evento
	 * 
	 * @param owner Propietario a asignar en el evento
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Devuelve la fecha de inicio del evento
	 * 
	 * @return Fecha de inicio del evento
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Asigna una fecha de inicio a un evento
	 * 
	 * @param start Fecha de inicio a asignar en el evento
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Devuelve la fecha de fin del evento
	 * 
	 * @return Fecha de fin del evento
	 */
	public Date getEnd() {
		return end;
	}

	/**
	 * Asigna una fecha de fin a un evento
	 * 
	 * @param end Fecha de fin a asignar en el evento
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Devuelve el nombre del evento
	 * 
	 * @return Nombre del evento
	 */
	public String getName() {
		return name;
	}

	/**
	 * Asigna un nombre a un evento
	 * 
	 * @param name Nombre a asignar al evento
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Devuelve la descripción del evento
	 * 
	 * @return Descripción del evento
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Asigna una descripción a un evento
	 *
	 * @param description Descripción a asignar al evento
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	/**
	 * Devuelve el contenido del objeto como cadena
	 * 
	 * @return Cadena con el contenido
	 */
	public String toString() {
		return "EventDTO [id=" + id + ", owner=" + owner + ", start=" + start
				+ ", end=" + end + ", name=" + name + ", description=" + description + "]";
	}
	
}