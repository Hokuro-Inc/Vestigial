import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ContactViewPage } from '../contact-view/contact-view.page';
import { Contact } from '../contacts/contacts.page';
import { ModifyContactPage } from '../modify-contact/modify-contact.page';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-show-profile',
  templateUrl: './show-profile.page.html',
  styleUrls: ['./show-profile.page.scss'],
})
export class ShowProfilePage implements OnInit {

	contacts: Contact;

  	constructor(private contactsService: ContactsService, public modalController: ModalController) { }

  	ngOnInit() {
  		//console.log(this.contact);
  	}

	async editContact(contact: Contact) {
	    //console.log(contact);
	    const modal = await this.modalController.create({
	      // Data passed in by componentProps
	      component: ModifyContactPage,
	      componentProps: {
	        contact: contact,
	      }
	    });
	    return await modal.present();
	}

}
