import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { TodolistPage } from '../todolist-page/todolist.page';
import { AddTodolistPage } from '../add-todolist/add-todolist.page';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.page.html',
  styleUrls: ['./lists.page.scss'],
})
export class ListsPage implements OnInit {

  lists: List[];
  filteredList: List[];

  constructor(private TodolistService: TodolistService, private modalController: ModalController) { }

  ngOnInit() {
    let data = {
      "user": sessionStorage.getItem("user"),
      "idLista": ""
    };

    this.TodolistService.getData(JSON.stringify(data)).subscribe(
      (response) => {
        console.log("Respuesta", response);
        if (response != '') {
          var data = JSON.parse(response).ToDoLists;
          this.lists = [];

          if (data != '[]') {
            data.forEach((element: any) => {
              this.lists.push(new List(element));
            });
          }

          this.filteredList = this.lists;
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
      }
    );
  }

  filter(event: any) {
    let value = event.target.value;

    if (value && value.trim() != '') {
      this.filteredList = this.lists.filter(item => {
        return item.name.toLowerCase().includes(value.toLowerCase());
     });
    }
    else {
      this.filteredList = this.lists;
    }
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
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (data.data.deleted) {
          let index = this.lists.indexOf(listaElegida);
          this.lists.splice(index, 1);
        }
      }

      this.filteredList = this.lists;
    });
    return await modal.present();
  }

  async addList() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddTodolistPage,
      componentProps: {
        lists: this.lists
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.list != undefined) {
        let list = data.data.list;
        this.lists.push(list);
        this.filteredList = this.lists;
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