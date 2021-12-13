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
  
  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private todolistService: TodolistService) { }
  
  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
    });
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(task: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "nameTask": task.name,
      "descriptionTask" : task.description,
      "statusTask" : task.status,
      "idLista" : this.task.list,
      "idTask" : this.task.id  
    };

    this.todolistService.updateTask(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss();
        //alert("Funciona!!!!");
      }
    );
  }
  
}
