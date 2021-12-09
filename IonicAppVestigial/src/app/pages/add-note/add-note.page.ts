import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NotepadService } from 'src/app/services/notepad-service/notepad.service';
import { Notepad } from '../notepads/notepads.page';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.page.html',
  styleUrls: ['./add-note.page.scss'],
})
export class AddNotePage implements OnInit {

  validations_form: FormGroup;
  note : Notepad;
  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private notepadService: NotepadService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      text: new FormControl('', Validators.required),
    })
  }

  dismiss(notepad: Notepad) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'notepad': notepad
    });
  }

  onSubmit(note: Notepad){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": note.name,
      "text" : note.text,
    };

    this.notepadService.addNotePad(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(note);
        //alert("Funciona!!!!");
      }
    );
  }

}
