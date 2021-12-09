import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { ContactViewPage } from '../contact-view/contact-view.page'
import { AddContactPage } from '../add-contact/add-contact.page';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.page.html',
  styleUrls: ['./contacts.page.scss'],
})
export class ContactsPage implements OnInit {

  contacts: Contact[];
  filteredList: Contact[];

  constructor(private contactsService: ContactsService, private modalController: ModalController, private changeDetector: ChangeDetectorRef) { }

  ngOnInit() {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getData(JSON.stringify(user)).subscribe(
      (response) => {
				//console.log("Respuesta", response);
				if (response != '') {
          let data = JSON.parse(response).Agenda;
          this.contacts = [];

          if (data != '[]') {
            data.forEach((element: any) => {
              this.contacts.push(new Contact(
                element.address,
                element.alias,
                element.description,
                element.email,
                element.name,
                element.owner,
                element.phone,
                element.prefix,
                element.surname
              ));
            });
          }

          this.sort();
          this.filteredList = this.contacts;
        }
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
    );
  }

  sort() {
    if (this.contacts.length == 0) return;

    this.contacts.sort((a, b) => {
      if (a.fullname.toLowerCase() < b.fullname.toLowerCase()) {
        return -1;
      }
      if (a.fullname.toLowerCase() > b.fullname.toLowerCase()) {
        return 1;
      }
      return 0;
    });
  }

  track(index: number, item: Contact) {
    return item.phone;
  }

  filter(event: any) {
    let value = event.target.value;

    if (value && value.trim() != '') {
      this.filteredList = this.contacts.filter(item => {
        return item.fullname.toLowerCase().indexOf(value.toLowerCase()) > -1 ||
          item.alias.toLowerCase().indexOf(value.toLowerCase()) > -1;
      });
    }
    else {
      this.filteredList = this.contacts;
    }
  }

  async showContact(contact: Contact) {
    //console.log(contact);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ContactViewPage,
      componentProps: {
      	contact: contact,
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (data.data.deleted == true) {
          let contact = data.data.contact;
          let index = this.contacts.indexOf(contact);
          this.contacts.splice(index, 1);
          this.filteredList = this.contacts;
        }
      }
    });
    return await modal.present();
  }

  async addContact() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddContactPage,
    });      
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        let element = data.data.contact;
        this.contacts.push(new Contact(
          element.address,
          element.alias,
          element.description,
          element.email,
          element.name,
          element.owner,
          element.phone,
          element.prefix,
          element.surname
        ));
        this.sort();
        this.filteredList = this.contacts;
      }
    });
    return await modal.present();
  }
}

export class Contact {
  address: string;
  alias: string;
  description: string;
  email: string;
  name: string;
  owner: string;
  phone: string;
  prefix: string;
  surname: string;
  fullname: string;

  constructor(address: string, alias: string, description: string, email: string, name: string, owner: string, phone: string, prefix: string, surname: string) {
    this.address = address;
    this.alias = alias;
    this.description = description;
    this.email = email;
    this.name = name;
    this.owner = owner;
    this.phone = phone;
    this.prefix = prefix;
    this.surname = surname;
    this.fullname = name + " " + surname;
  }
}