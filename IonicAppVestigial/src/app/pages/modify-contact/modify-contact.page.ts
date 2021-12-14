import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { NavController } from '@ionic/angular';
import { Contact } from '../contacts/contacts.page'

@Component({
  selector: 'app-modify-contact',
  templateUrl: './modify-contact.page.html',
  styleUrls: ['./modify-contact.page.scss'],
})
export class ModifyContactPage implements OnInit {

  validations_form: FormGroup;
  contact: Contact;
  groups: string[];
  
  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private contactService: ContactsService, private navController: NavController) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      prefix: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]{2,}')
      ])),
      phone: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[0-9]{9,}')
      ])),
      address: new FormControl('', Validators.required),
      email: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$')
      ])),
      description: new FormControl('', Validators.required),
      alias: new FormControl('', Validators.required),
      owner: sessionStorage.getItem("user"),
      groups: new FormControl(''),
    })
  }

  dismiss(contact: any, modified: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact,
      'modified': modified
    });
  }

  onSubmit(contact: any) {
    if (contact.groups == '') contact.groups = [];

    let data = {
      "user": sessionStorage.getItem("user"),
      "name": contact.name,
      "surname": contact.surname,
      "prefix": contact.prefix,
      "phone": contact.phone,
      "address": contact.address,
      "email": contact.email,
      "description": contact.description,
      "alias": contact.alias,
      "groups": contact.groups
    };

    this.contactService.updateContact(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(data, true);
        //alert("Funciona!!!!");
      }
    );
  }

}
