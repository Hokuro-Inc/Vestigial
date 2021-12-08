import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.page.html',
  styleUrls: ['./add-group.page.scss'],
})
export class AddGroupPage implements OnInit {

  validations_form: FormGroup;

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private contactsService: ContactsService, private navController: NavController) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      group: new FormControl('', Validators.required),
    })
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(formulario: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": formulario.group
    };
    console.log(data)
    this.contactsService.addGroup(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss();
        //alert("Funciona!!!!");
      }
    );
  }


}
