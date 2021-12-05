import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { TodolistPage } from '../todolist-page/todolist.page';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.page.html',
  styleUrls: ['./lists.page.scss'],
})
export class ListsPage implements OnInit {

  lists: List[];

  constructor(private TodolistService: TodolistService, private modalController: ModalController) { }

  ngOnInit() {
    let data = {
      "user": sessionStorage.getItem("user"),
      "idLista": ""
    };

    this.TodolistService.getData(JSON.stringify(data)).subscribe(
        (response) => {
          //console.log("Respuesta", response);
          if (response != '') {
            var data = JSON.parse(response).ToDoLists;
            this.lists = [];
            
            data.forEach((element: any) => {
              this.lists.push(new List(element));
            });

            //console.log(this.lists.forEach(e => console.log(e)));
          }
        },
        (error) => console.log("Error", error),
        () => {
          console.log("Completed");
        }
      );
  }

  async showList(listaElegida: List) {
    //console.log(listaElegida);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: TodolistPage,
      componentProps: {
        lista: listaElegida,
      }
    });
    return await modal.present();
  }
}

export class List {
  name: string;

  constructor(name: string) {
    this.name = name;
  }
}