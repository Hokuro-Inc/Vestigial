import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { TaskPage } from '../task-page/task.page'
import { List } from '../lists-page/lists.page'
import { AddTaskPage } from '../add-task/add-task.page';
import { ModifyTaskPage } from '../modify-task/modify-task.page';

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
        console.log("Respuesta", response);
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

  dismiss(list: List, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'list': list,
      'deleted': deleted
    });
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
        todolist: this.todolist
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.task != undefined) {
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

  async editTask(task: Task) {
    let tmp = [];
    this.todolist.forEach(item => tmp.push(item.name));

    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyTaskPage,
      componentProps: {
        task: task,
        todolist: tmp
      }
    });
    return await modal.present();
  }

  async deleteList() {
    //console.log(this.lista);
    let data = {
      "user": sessionStorage.getItem("user"),
      "idLista": this.lista.name
    };

    this.todolistService.removeToDoList(JSON.stringify(data)).subscribe(
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
        this.dismiss(this.lista, true);
      }
    );
  }

  async deleteTask(task: Task) {
    //console.log(contact);
    let data = {
      "user": sessionStorage.getItem("user"),
      "idTask": task.id,
    };

    this.todolistService.removeTask(JSON.stringify(data)).subscribe(
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
        this.dismiss(task, true);
      }
    ); 
  }

  async changeCheckState(event: any, task: Task) {
    let checked = !event.currentTarget.checked;

    let data = {
      "user": sessionStorage.getItem("user"),
      "nameTask": task.name,
      "descriptionTask": task.description,
      "statusTask": checked ? "Archived" : "ToDo",
      "idLista": task.list,
      "idTask": task.id  
    };

    this.todolistService.updateTask(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        //alert("Funciona!!!!");
      }
    );
  }

  taskDone(task: Task) {
    return task.status == 'Archived';
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