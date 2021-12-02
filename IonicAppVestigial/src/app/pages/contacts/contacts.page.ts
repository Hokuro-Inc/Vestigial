import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.page.html',
  styleUrls: ['./contacts.page.scss'],
})
export class ContactsPage implements OnInit {

  contacts: Contact[];

  constructor(private contactsService: ContactsService) { }

  ngOnInit() {
    let data = {
      "user": sessionStorage.getItem("user"),
    };

    this.contactsService.getData(JSON.stringify(data)).subscribe(
      (response) => {
				//console.log("Respuesta", response);
				if (response != '') {
          var data = JSON.parse(response).Agenda;
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

          //console.log(this.contacts.forEach(e => console.log(e)));
        }
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
    );
  }

}

class Contact {
  address: string;
  alias: string;
  description: string;
  email: string;
  name: string;
  owner: string;
  phone: string;
  prefix: string;
  surname: string;

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
  }
}