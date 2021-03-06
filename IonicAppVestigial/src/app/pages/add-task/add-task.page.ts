import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { List } from '../lists-page/lists.page'
import { Task } from '../todolist-page/todolist.page';

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.page.html',
  styleUrls: ['./add-task.page.scss'],
})
export class AddTaskPage implements OnInit {

  validations_form: FormGroup;
  lista: List;
  todolist: Task[];

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private todolistService: TodolistService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    }, { validator: this.taskAlredyExists('name') });
  }

  taskAlredyExists(task: string) {
    return (formGroup: FormGroup): {[key: string]: any} => {
      let t = formGroup.controls[task];

      for (let i = 0; i < this.todolist.length; i += 1) {
        if (String(t.value).indexOf(this.todolist[i].name) > -1 && String(t.value).trim() == this.todolist[i].name) {
          return {
            notepad: "La tarea ya existe"
          }
        }
      }
  
      return {};
    }
  }

  dismiss(task: any, id: string) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'task': task,
      'id': id
    });
  }

  onSubmit(task: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "nameTask": task.name,
      "descriptionTask" : task.description,
      "lista" : this.lista.name
    };

    let id = '0';
    this.todolistService.addTask(JSON.stringify(data)).subscribe(
      (response) => {
        console.log("Respuesta", response);
        id = JSON.parse(response).idTask;
      },
      (error) => console.log("Error", error),
      () => {
        this.dismiss(data, id);
        //alert("Funciona!!!!");
      }
    );
  }

}
