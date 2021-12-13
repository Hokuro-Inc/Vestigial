import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { Task } from '../todolist-page/todolist.page';
import { ModifyTaskPage } from '../modify-task/modify-task.page'

@Component({
  selector: 'app-task',
  templateUrl: './task.page.html',
  styleUrls: ['./task.page.scss'],
})
export class TaskPage implements OnInit {

  task: Task;
  
  constructor(private todolistService: TodolistService, public modalController: ModalController) { }

  ngOnInit() {
    //console.log(this.task);
  }

  dismiss(task: any, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'task': task,
      'deleted': deleted
    });
  }

  async editTask(task: Task) {
    //console.log(contact);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyTaskPage,
      componentProps: {
        task: task,
      }
    });
    return await modal.present();
  }

  async deleteTask(task: Task) {
    let data = {
      "user": sessionStorage.getItem("user"),
      "idTask" : task.id,
    };
    this.todolistService.removeTask(JSON.stringify(data)).subscribe(
      (response) => { 
        console.log("Respuesta", response);
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
}
