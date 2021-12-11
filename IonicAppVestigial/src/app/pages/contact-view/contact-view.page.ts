import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { Contact } from '../contacts/contacts.page'
import { ModifyContactPage } from '../modify-contact/modify-contact.page'

import { NFC, Ndef, NfcTag} from '@awesome-cordova-plugins/nfc/ngx';

@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.page.html',
  styleUrls: ['./contact-view.page.scss'],
})
export class ContactViewPage implements OnInit {

	contact: Contact;

  constructor(private contactsService: ContactsService, public modalController: ModalController,private nfc: NFC, private ndef: Ndef) { }

  ngOnInit() {
    //console.log(this.contact);
  }

   dismiss(contact: Contact, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact,
      'deleted': deleted
    });
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

  async deleteContact(contact: Contact) {
    //console.log(contact);
    let data = {
      "user": sessionStorage.getItem("user"),
      "phone" : contact.phone,
      "prefix" : contact.prefix
    };
    this.contactsService.removeContact(JSON.stringify(data)).subscribe(
      (response) => { 
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje
          console.log("Mensaje",data)
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(contact, true);
      }
    ); 
  }

  async exportContact (contact: Contact) {
    console.log("Contacto a guardar en el nfc",JSON.stringify(contact));
    var mensaje = [
      this.ndef.textRecord(JSON.stringify(contact))
    ];
    console.log("Mensaje que se grabara " , mensaje);
    this.nfc.write(mensaje);

  }

}