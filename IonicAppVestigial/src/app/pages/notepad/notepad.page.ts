import { Component, OnInit } from '@angular/core';
import { NotepadService } from 'src/app/services/notepad-service/notepad.service';
import { ModalController } from '@ionic/angular';
import { Notepad } from '../notepads/notepads.page'
import { ModifyNotePage } from '../modify-note/modify-note.page'

@Component({
  selector: 'app-notepad',
  templateUrl: './notepad.page.html',
  styleUrls: ['./notepad.page.scss'],
})
export class NotepadPage implements OnInit {

  notepad: Notepad;

  constructor(private notepadService: NotepadService, private modalController: ModalController) { }

  ngOnInit() {
    //console.log(this.notepad);
  }

  dismiss(notepad: Notepad, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'notepad': notepad,
      'deleted': deleted
    });
  }

  async editNotePad(notepad: Notepad) {
    //console.log(notepad);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyNotePage,
      componentProps: {
        note: notepad,
      }
    });
    return await modal.present();
  }

  async deleteNotePad(notepad: Notepad) {
    //console.log(notepad)
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": notepad.name
    };
    this.notepadService.removeNotePad(JSON.stringify(data)).subscribe(
      (response) => { 
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje
          console.log("Mensaje",data)
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(notepad, true);
      }
    );
  }
}
