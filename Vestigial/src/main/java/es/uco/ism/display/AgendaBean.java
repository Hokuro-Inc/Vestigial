package es.uco.ism.display;

import java.io.Serializable;
import java.util.ArrayList;

import es.uco.ism.business.contact.ContactDTO;

public class AgendaBean implements Serializable{

    private static final long serialVersionUID = 1L;

    private ArrayList <ContactDTO> contacts = null;

    public ArrayList <ContactDTO> getContacts(){
        return contacts;
    }
    
    public void setEvents (ArrayList <ContactDTO> contacts){
        this.contacts = contacts;
    }

    public ContactDTO get(int index){
        return this.contacts.get(index);
    }

    public void set (int index, ContactDTO contact){
        this.contacts.set(index, contact);
    }
    
}
