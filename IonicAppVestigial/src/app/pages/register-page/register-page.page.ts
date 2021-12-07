import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { RegisterService } from 'src/app/services/register-service/register.service';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { Contact } from '../contacts/contacts.page'
import { Router } from '@angular/router';

@Component({
  selector: 'register-page',
  templateUrl: './register-page.page.html',
  styleUrls: ['./register-page.page.scss'],
})
export class RegisterPagePage implements OnInit {

  validations_form: FormGroup;
  contact: Contact;
  constructor(public modalController: ModalController, private contactService: ContactsService, public formBuilder: FormBuilder, private registerService: RegisterService, private router: Router) { }

  ngOnInit(){
    this.validations_form = this.formBuilder.group({
      email: new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$')
      ])),
      password: new FormControl('', Validators.compose([
        Validators.minLength(5),
        Validators.required,
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
      ])),
      prefix: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
    })

  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

	onSubmit(values: any){
		//console.log("Page", values);

		this.registerService.getData(values).subscribe(
			(response) => console.log("Respuesta", response),
			(error) => console.log("Error", error),
			() => {
				this.dismiss();
				sessionStorage.setItem("user", values.email);
        sessionStorage.setItem("phone", values.phone);
				this.router.navigate(['/calendar']);
				//alert("Funciona!!!!");
			}
		);
    let contact = {
        "email": values.getItem("user"),
        "phone" : values.phone +'-'+ values.prefix,
      };

      console.log(contact);
      /*this.contactService.addContact(contact).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss();
        //this.navController.navigateBack(['/agenda-page/contacts']);
        //alert("Funciona!!!!");
      }
    );*/
	}

}
