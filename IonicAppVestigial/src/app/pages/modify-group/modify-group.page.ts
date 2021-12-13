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
  groups: string[];

  constructor(private modalController: ModalController, private contactsService: ContactsService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      group: new FormControl('', Validators.required),
    }, { validator: this.groupAlredyExists('group') });
  }

  groupAlredyExists(group: string) {
    return (formGroup: FormGroup): {[key: string]: any} => {
      let g = formGroup.controls[group];

      if (this.groups.indexOf(g.value) > -1) {
        return {
          groups: "El grupo ya existe"
        };
      }
  
      return {};
    }
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
