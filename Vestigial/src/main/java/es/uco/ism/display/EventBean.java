package es.uco.ism.display;

import java.io.Serializable;

import es.uco.ism.business.event.EventDTO;

public class EventBean implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private EventDTO event = null;


	public void setEvent(EventDTO event) {
		this.event = event;
	}


	public EventDTO getEvent() {
		return event;
	}
	


}
