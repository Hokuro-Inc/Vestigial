import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { TaskPage } from '../task-page/task.page'
import { List } from '../lists-page/lists.page'
import { AddTaskPage } from '../add-task/add-task.page';

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.page.html',
  styleUrls: ['./todolist.page.scss'],
})
export class TodolistPage implements OnInit {

  todolist: Task[];
  lista: List;

  constructor(private todolistService: TodolistService, private modalController: ModalController) { }

  ngOnInit() {
    //console.log(this.lista);
    let data = {
      "user": sessionStorage.getItem("user"),
      "idLista": this.lista.name
    };

    this.todolistService.getData(JSON.stringify(data)).subscribe(
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
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (data.data.deleted) {
          let task = data.data.task;
          let index = this.todolist.indexOf(task);
          this.todolist.splice(index, 1);
        }
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
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        let task = data.data.task;
        this.todolist.push(new Task(
          data.data.id,
          task.user,
          task.nameTask,
          task.descriptionTask,
          "ToDo",
          task.lista
        ));
      }
    });
    return await modal.present();
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