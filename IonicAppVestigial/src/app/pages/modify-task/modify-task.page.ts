import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { Task } from '../todolist-page/todolist.page';

@Component({
  selector: 'app-modify-task',
  templateUrl: './modify-task.page.html',
  styleUrls: ['./modify-task.page.scss'],
})
export class ModifyTaskPage implements OnInit {

  validations_form: FormGroup;
  task: Task;
  todolist: string[];
  
  constructor(private modalController: ModalController, private formBuilder: FormBuilder, private todolistService: TodolistService) { }
  
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
        if (String(t.value).indexOf(this.todolist[i]) > -1 && String(t.value).trim() == this.todolist[i]) {
          return {
            notepad: "La tarea ya existe"
          }
        }
      }
  
      return {};
    }
  }

  dismiss(task: any, modified: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'task': task,
      'modified': modified
    });
  }

  onSubmit(task: Task){
    let data = {
      "user": sessionStorage.getItem("user"),
      "nameTask": task.name,
      "descriptionTask": task.description,
      "statusTask" : this.task.status,
      "idLista" : this.task.list,
      "idTask" : this.task.id  
    };

    this.todolistService.updateTask(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(data, true);
        //alert("Funciona!!!!");
      }
    );
  }
  
}
