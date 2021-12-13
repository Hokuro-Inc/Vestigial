import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { Contact } from '../contacts/contacts.page';
import { ModifyContactPage } from '../modify-contact/modify-contact.page';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-show-profile',
  templateUrl: './show-profile.page.html',
  styleUrls: ['./show-profile.page.scss'],
})
export class ShowProfilePage implements OnInit {

	contact: Contact = new Contact();

  constructor(private contactsService: ContactsService, public modalController: ModalController) { }

  ngOnInit() {
    //console.log(this.contact);
    let values = {
       "user": sessionStorage.getItem("user"),
       "phone": sessionStorage.getItem("phone"),
    };
    this.contactsService.getProfile(JSON.stringify(values)).subscribe(
      (response) =>{
        //console.log("RespuestaShow", response)
        if (response != '') {
          let profile = JSON.parse(response).Profile[0];
          this.contact = new Contact(
            profile.address,
            profile.alias,
            profile.description,
            profile.email,
            profile.name,
            profile.owner,
            profile.phone,
            profile.prefix,
            profile.surname
          );
        };
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        //alert("Funciona!!!!");
      }
    );
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
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.contact != undefined) {
        let profile = data.data.contact;
        this.contact = new Contact(
          profile.address,
          profile.alias,
          profile.description,
          profile.email,
          profile.name,
          profile.owner,
          profile.phone,
          profile.prefix,
          profile.surname
        );
      }
    });
    return await modal.present();
  }

}
