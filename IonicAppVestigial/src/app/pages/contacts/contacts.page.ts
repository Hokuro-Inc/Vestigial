import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { ContactViewPage } from '../contact-view/contact-view.page'
import { AddGroupPage } from '../add-group/add-group.page'

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.page.html',
  styleUrls: ['./contacts.page.scss'],
})
export class ContactsPage implements OnInit {

  contacts: Contact[];
  filteredList: Contact[];

  groups: String[];

  constructor(private contactsService: ContactsService, private modalController: ModalController, private changeDetector: ChangeDetectorRef) { }

  ngOnInit() {
    this.getContacts();
  }

  getContacts() {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getData(JSON.stringify(user)).subscribe(
      (response) => {
				//console.log("Respuesta", response);
				if (response != '') {
          let data = JSON.parse(response).Agenda;
          this.contacts = [];
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

          this.filteredList = this.contacts;
        }
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
    );
  }

  doRefresh(event: any) {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getData(JSON.stringify(user)).subscribe(
      (response) => {
				//console.log("Respuesta", response);
				if (response != '') {
          let data = JSON.parse(response).Agenda;
          this.contacts = [];
          
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

          this.filteredList = this.contacts;
        }
			},
			(error) => console.log("Error", error),
			() => {
        event.target.complete();
				console.log("Completed");
			}
    );
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

  getGroups () {
        let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getGroups(JSON.stringify(user)).subscribe(
      (response) => {
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Groups;
          this.groups = [];
          console.log(data);
          data.forEach((element: any) => {
              this.groups.push(element);
          });
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
      }
    );
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
    return await modal.present();
  }


  async addGroup() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddGroupPage,
      componentProps: {
        groups: this.groups,
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