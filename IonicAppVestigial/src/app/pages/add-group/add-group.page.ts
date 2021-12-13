import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';

@Component({
  selector: 'app-add-group',
  templateUrl: './add-group.page.html',
  styleUrls: ['./add-group.page.scss'],
})
export class AddGroupPage implements OnInit {

  validations_form: FormGroup;
  groups: string[];

  constructor(private modalController: ModalController, private formBuilder: FormBuilder, private contactsService: ContactsService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      group: new FormControl('', Validators.required),
    }, { validator: this.groupAlredyExists('group') });
  }

  groupAlredyExists(group: string) {
    return (formGroup: FormGroup): {[key: string]: any} => {
      let g = formGroup.controls[group];

      for (let i = 0; i < this.groups.length; i += 1) {
        if (String(g.value).indexOf(this.groups[i]) > -1 && String(g.value).trim() == this.groups[i]) {
          return {
            groups: "El grupo ya existe"
          };
        }
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
      "user": sessionStorage.getItem("user"),
      "group": formulario.group
    };

    this.contactsService.addGroup(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(formulario.group);
        //alert("Funciona!!!!");
      }
    );
  }

}
