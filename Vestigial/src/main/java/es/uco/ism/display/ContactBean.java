package es.uco.ism.display;

import java.io.Serializable;

import es.uco.ism.business.contact.ContactDTO;

public class ContactBean implements Serializable{

    private static final long serialVersionUID = 1L;

	private String email = "";
    private ContactDTO contact = null;

    public void setEmail (String email){
        this.email = email;

    }

    public String getEmail(){
        return email;
    }

    public void setContact(ContactDTO contact){
        this.contact = contact;
    }

    public ContactDTO getContact(){
        return contact;
    }

}
