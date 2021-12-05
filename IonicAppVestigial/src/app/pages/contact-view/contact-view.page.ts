import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { Contact } from '../contacts/contacts.page'
import { ModifyContactPage } from '../modify-contact/modify-contact.page'
@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.page.html',
  styleUrls: ['./contact-view.page.scss'],
})
export class ContactViewPage implements OnInit {

	contact: Contact;

  	constructor(private contactsService: ContactsService, public modalController: ModalController) { }

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
      let datas = {
        "user": sessionStorage.getItem("user"),
        "phone" : contact.phone,
        "prefix" : contact.prefix
      };
      this.contactsService.removeContact(JSON.stringify(datas)).subscribe(
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
        }
      ); 

      this.router.navigate(['/contacts']);
  }

}