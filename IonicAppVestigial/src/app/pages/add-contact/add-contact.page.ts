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
    //console.log("En la vista de añadir contacto",this.groups);
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

  dismiss(contact: string) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact
    });
  }

  onSubmit(contact: any){
    //console.log(contact);
    if (contact.groups == '') contact.groups = [];

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
