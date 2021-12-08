import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import {TaskPage} from '../task-page/task.page'
import {List} from '../lists-page/lists.page'
import { AddTaskPage } from '../add-task/add-task.page';
@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.page.html',
  styleUrls: ['./todolist.page.scss'],
})
export class TodolistPage implements OnInit {

  todolist: Task[];
  lista : List;

  constructor(private TodolistService: TodolistService, private modalController: ModalController) { }

    ngOnInit() {
      let data = {
        "user": sessionStorage.getItem("user"),
        "idLista": this.lista.name
      };

      this.TodolistService.getData(JSON.stringify(data)).subscribe(
        (response) => {
          //console.log("Respuesta", response);
          if (response != '') {
            var data = JSON.parse(response).ToDoList;
            this.todolist = [];
            
            data.forEach((element: any) => {
              this.todolist.push(new Task(
                element.id,
                element.owner,
                element.name,
                element.description,
                element.status,
                element.list,
              ));
            });
          }
        },
        (error) => console.log("Error", error),
        () => {
          console.log("Completed");
        }
      );
    }
  
  doRefresh (event:any){
    let data = {
        "user": sessionStorage.getItem("user"),
        "idLista": this.lista.name
      };

      this.TodolistService.getData(JSON.stringify(data)).subscribe(
        (response) => {
          //console.log("Respuesta", response);
          if (response != '') {
            var data = JSON.parse(response).ToDoList;
            this.todolist = [];
            
            data.forEach((element: any) => {
              this.todolist.push(new Task(
                element.id,
                element.owner,
                element.name,
                element.description,
                element.status,
                element.list,
              ));
            });
          }
        },
        (error) => console.log("Error", error),
        () => {
          console.log("Completed");
        }
      );
  }
  async showTask(task: Task) {
    //console.log(task);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      //Cambiar a vista TaskPage
      component: TaskPage,
      componentProps: {
        task: task,
      }
    });
    return await modal.present();
  }

  async addTask() {
    //console.log(listaElegida);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddTaskPage,
      componentProps: {
        lista: this.lista,
      }
    });
    return await modal.present();
  }

  async deleteList() {
    //console.log(listaElegida);
    let datas = {
        "user": sessionStorage.getItem("user"),
        "idLista" :this.lista
      };
      this.TodolistService.removeToDoList(JSON.stringify(datas)).subscribe(
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
          this.dismiss();
        }

      ); 
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

}

export class Task {
  id: string;
  owner: string;
  name: string;
  description: string;
  status: string;
  list: string;

  constructor(id: string, owner: string, name: string, description: string, status: string, list: string) {
    this.id = id;
    this.owner = owner;
    this.name = name;
    this.description = description;
    this.status = status;
    this.list = list;
    
  }
}