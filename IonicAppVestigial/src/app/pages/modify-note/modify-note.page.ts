import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NotepadService } from 'src/app/services/notepad-service/notepad.service';
import { Notepad } from '../notepads/notepads.page';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-modify-note',
  templateUrl: './modify-note.page.html',
  styleUrls: ['./modify-note.page.scss'],
})
export class ModifyNotePage implements OnInit {

  validations_form: FormGroup;

  note : Notepad;
  
  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private notepadService: NotepadService, private navController: NavController) { }
  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
    })
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(note: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": note.name,
      "text" : note.text,
    };
    console.log(data)
    this.notepadService.updateNotePad(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss();
        this.navController.navigateBack(['/notepads']);
        //alert("Funciona!!!!");
      }
    );
  }

}
