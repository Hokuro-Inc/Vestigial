import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Contact } from '../contacts/contacts.page'
@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.page.html',
  styleUrls: ['./contact-view.page.scss'],
})
export class ContactViewPage implements OnInit {

	contact: Contact;

  	constructor(public modalController: ModalController) { }

  	ngOnInit() {
  		//console.log(this.contact);
  	}

   dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

}
