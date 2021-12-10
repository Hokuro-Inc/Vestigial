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

	contact: Contact  = new Contact("","","","","","","","","");

  	constructor(private contactsService: ContactsService, public modalController: ModalController) { }

  	ngOnInit() {
  		//console.log(this.contact);
  		let values = {
        "user": sessionStorage.getItem("user"),
        "phone": sessionStorage.getItem("phone"),

      	};
      	console.log(values);
  		this.contactsService.getProfile(JSON.stringify(values)).subscribe(
			(response) =>{
				console.log("RespuestaShow", response)
				if (response != '') {
		          let data = JSON.parse(response).Profile;
		          console.log("RespuestaData", data)
		            this.contact = new Contact(
		              data[0].address,
		              data[0].alias,
		              data[0].description,
		              data[0].email,
		              data[0].name,
		              data[0].owner,
		              data[0].phone,
		              data[0].prefix,
		              data[0].surname
		            );
		          };
			},
			(error) => console.log("Error", error),
			() => {
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
	    return await modal.present();
	}

}
