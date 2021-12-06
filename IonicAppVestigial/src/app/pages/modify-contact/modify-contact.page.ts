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
  
  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private contactService: ContactsService, private navController: NavController) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      prefix: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      alias: new FormControl('', Validators.required),
      owner: sessionStorage.getItem("user")
    })
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(contact: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": contact.name,
      "surname": contact.surname,
      "prefix": contact.prefix,
      "phone": contact.phone,
      "address": contact.address,
      "email": contact.email,
      "description": contact.description,
      "alias": contact.alias
    };
    this.contactService.updateContact(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss();
        this.navController.navigateBack(['/contacts']);
        //alert("Funciona!!!!");
      }
    );
  }

}
