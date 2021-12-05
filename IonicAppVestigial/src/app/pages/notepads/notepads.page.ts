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
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).NotePads;
          this.notepads = [];
          
          data.forEach((element: any) => {
            this.notepads.push(new Notepad(
              element.name,
              element.text
            ));
          });
          //console.log(this.notepads.forEach(e => console.log(e)));
        }
      },
      (error) => console.log("Error", error),
      () => {
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
            let data = JSON.parse(response).Mensaje
            console.log("Mensaje",data)
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