import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { ContactViewPage } from '../contact-view/contact-view.page'
import { AddContactPage } from '../add-contact/add-contact.page';
import { ImportContactPage } from '../import-contact/import-contact.page';
import { GroupsPage } from '../groups/groups.page';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.page.html',
  styleUrls: ['./contacts.page.scss'],
})
export class ContactsPage implements OnInit {

  contacts: Contact[];
  filteredList: Contact[];
  groups: string[];

  constructor(private contactsService: ContactsService, private modalController: ModalController) { }

  ngOnInit() {
    this.getContacts();
    this.getGroups();
  }

  getContacts() {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getData(JSON.stringify(user)).subscribe(
      (response) => {
				console.log("Respuesta", response);
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
                element.surname,
                element.groups
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
        return item.fullname.toLowerCase().includes(value.toLowerCase()) ||
          item.alias.toLowerCase().includes(value.toLowerCase()) || this.isInGroup(item, value.toLowerCase());
      });
    }
    else {
      this.filteredList = this.contacts;
    }
  }

  isInGroup(contact: Contact, item: string) {
    for (let  j = 0; j < contact.groups.length; j += 1) {
      if (contact.groups[j].toLowerCase().includes(item)) {
        return true;
      }
    }

    return false;
  }

  getGroups () {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getGroups(JSON.stringify(user)).subscribe(
      (response) => {
        console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Groups;
          this.groups = [];
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
    let index = this.contacts.indexOf(contact);
    let tmp = new Contact(
      contact.address,
      contact.alias,
      contact.description,
      contact.email,
      contact.name,
      contact.owner,
      contact.phone,
      contact.prefix,
      contact.surname,
      contact.groups
    );

    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ContactViewPage,
      componentProps: {
      	contact: contact,
        groups: this.groups
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (data.data.deleted) {
          let contact = data.data.contact;
          let index = this.contacts.indexOf(contact);
          this.contacts.splice(index, 1);
        }
        else if (data.data.modified) {
          this.contacts[index] = new Contact(
            data.data.contact.address,
            data.data.contact.alias,
            data.data.contact.description,
            data.data.contact.email,
            data.data.contact.name,
            data.data.contact.owner,
            data.data.contact.phone,
            data.data.contact.prefix,
            data.data.contact.surname,
            data.data.contact.groups
          );
        }
        else {
          this.contacts[index] = tmp;
        }
      }
      else {
        this.contacts[index] = tmp;
      }

      this.filteredList = this.contacts;
      this.sort();
    });
    return await modal.present();
  }

  async showGroups() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: GroupsPage,
      componentProps: {
        groups: this.groups
      }
    });
    return await modal.present();
  }

  async addContact() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddContactPage,
      componentProps: {
        groups: this.groups,
      }
    });      
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.contact != undefined) {
        let contact = data.data.contact;
        this.contacts.push(new Contact(
          contact.address,
          contact.alias,
          contact.description,
          contact.email,
          contact.name,
          contact.owner,
          contact.phone,
          contact.prefix,
          contact.surname,
          contact.groups
        ));
        this.sort();
        this.filteredList = this.contacts;
      }
    });
    return await modal.present();
  }

  async importContact() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ImportContactPage,
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.contact != undefined) {
        let contact = data.data.contact;
        this.contacts.push(new Contact(
          contact.address,
          contact.alias,
          contact.description,
          contact.email,
          contact.name,
          contact.owner,
          contact.phone,
          contact.prefix,
          contact.surname,
          contact.groups
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
  groups: string[];
  fullname: string;

  constructor(address: string = "", alias: string = "", description: string = "", email: string = "", name: string = "",
    owner: string = "", phone: string = "", prefix: string = "", surname: string = "", groups: string[] = [])
  {
    this.address = address;
    this.alias = alias;
    this.description = description;
    this.email = email;
    this.name = name;
    this.owner = owner;
    this.phone = phone;
    this.prefix = prefix;
    this.surname = surname;
    this.groups = groups;
    this.fullname = name + " " + surname;
  }

}