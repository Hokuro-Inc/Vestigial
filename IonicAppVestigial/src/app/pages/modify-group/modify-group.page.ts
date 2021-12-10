import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';

@Component({
  selector: 'app-modify-group',
  templateUrl: './modify-group.page.html',
  styleUrls: ['./modify-group.page.scss'],
})
export class ModifyGroupPage implements OnInit {

  validations_form: FormGroup;
  oldGroup: string;

  constructor(private modalController: ModalController, private contactsService: ContactsService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      group: new FormControl('', Validators.required),
    })
  }

  dismiss(group: string) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'group': group
    });
  }

  onSubmit(formulario: any){
    let data = {
      'user': sessionStorage.getItem("user"),
      'oldGroup': this.oldGroup,
      'newGroup': formulario.group
    };

    this.contactsService.updateGroup(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(formulario.group);
        //alert("Funciona!!!!");
      }
    );
  }

}
