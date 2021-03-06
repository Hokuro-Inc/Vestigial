import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NotepadService } from 'src/app/services/notepad-service/notepad.service';
import { Notepad } from '../notepads/notepads.page';

@Component({
  selector: 'app-modify-note',
  templateUrl: './modify-note.page.html',
  styleUrls: ['./modify-note.page.scss'],
})
export class ModifyNotePage implements OnInit {

  validations_form: FormGroup;
  note: Notepad;
  
  constructor(private modalController: ModalController, private formBuilder: FormBuilder, private notepadService: NotepadService) { }
  
  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      text: new FormControl('', Validators.required)
    });
  }

  dismiss(note: any, modified: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'note': note,
      'modified': modified
    });
  }

  onSubmit(note: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": note.name,
      "text": note.text,
    };

    this.notepadService.updateNotePad(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(data, true);
        //alert("Funciona!!!!");
      }
    );
  }

}
