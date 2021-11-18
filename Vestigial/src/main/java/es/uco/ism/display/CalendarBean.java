package es.uco.ism.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.ism.business.event.EventDTO;


public class CalendarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<EventDTO> events = null;

	public ArrayList<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<EventDTO> events) {
		this.events = events;
	}


	public EventDTO get(int index) {
		return this.events.get(index);
	}
	
	public void set(int index, EventDTO event) {
		this.events.set(index, event);
	}
	
	
	
	
}
