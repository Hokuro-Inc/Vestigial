import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.page.html',
  styleUrls: ['./add-contact.page.scss'],
})
export class AddContactPage implements OnInit {

  validations_form: FormGroup;
  groups : String [];

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private contactService: ContactsService) { }

  ngOnInit() {
    console.log("En la vista de añadir contacto",this.groups);
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      prefix: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      alias: new FormControl('', Validators.required),
      owner: sessionStorage.getItem("user"),
      groups: new FormControl(''),
    })
  }

  dismiss(contact: string) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact
    });
  }

  onSubmit(contact: any){
    console.log(contact);
    this.contactService.addContact(contact).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(contact);
        //alert("Funciona!!!!");
      }
    );
  }

}
