import { Component, OnInit } from '@angular/core';
import { NotepadService } from 'src/app/services/notepad-service/notepad.service';
import { ModalController } from '@ionic/angular';
import { NotepadPage } from '../notepad/notepad.page'
import { AddNotePage } from '../add-note/add-note.page'
import { ModifyNotePage } from '../modify-note/modify-note.page'

@Component({
  selector: 'app-notepads',
  templateUrl: './notepads.page.html',
  styleUrls: ['./notepads.page.scss'],
})
export class NotepadsPage implements OnInit {

  notepads: Notepad[];

 constructor(private notepadService: NotepadService, private modalController: ModalController) { }

  ngOnInit() {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.notepadService.getNotePads(JSON.stringify(user)).subscribe(
      (response) => {
        //console.log(response);
        if (response != '') {
          let data = JSON.parse(response).Blocs;
          this.notepads = [];
          
          if (data != '[]') {
            data.forEach((element: any) => {
              this.notepads.push(new Notepad(
                element.name,
                element.text
              ));
            });
          }
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
      }
    );
  }

  doRefresh (event : any ) {
    let user = {
      "user": sessionStorage.getItem("user"),
    };

    this.notepadService.getNotePads(JSON.stringify(user)).subscribe(
      (response) => {
        if (response != '') {
          let data = JSON.parse(response).Blocs;
          this.notepads = [];
          data.forEach((element: any) => {
            this.notepads.push(new Notepad(
              element.name,
              element.text
            ));
          });
        }
      },
      (error) => console.log("Error", error),
      () => {
        event.target.complete();
        console.log("Completed");
      }
    );
  }

  async showNotePad(note: Notepad) {
    //console.log(contact);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: NotepadPage,
      componentProps: {
        note: note,
      }
    });
    return await modal.present();
  }

  async addNotePad() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddNotePage,
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        let notepad = data.data.notepad;
        this.notepads.push(new Notepad(
          notepad.name,
          notepad.text
        ));
      }
    });
    return await modal.present();
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
    //console.log(notepad);
    let datas = {
      "user": sessionStorage.getItem("user"),
      "name" : notepad.name
    };
    this.notepadService.removeNotePad(JSON.stringify(datas)).subscribe(
      (response) => { 
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje;
          console.log("Mensaje",data);
          let index = this.notepads.indexOf(notepad);
          this.notepads.splice(index, 1);
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
      }
    );
  }

}

export class Notepad {
    name: string;
    text: string;
    
    constructor(name: string, text: string) {
      this.name = name;
      this.text = text;
    }
}