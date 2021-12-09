import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';

@Component({
  selector: 'app-add-todolist',
  templateUrl: './add-todolist.page.html',
  styleUrls: ['./add-todolist.page.scss'],
})
export class AddTodolistPage implements OnInit {

  validations_form: FormGroup;

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private todolistService: TodolistService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
    })
  }

  dismiss(list: any) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'list': list
    });
  }

  onSubmit(list: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": list.name
    };

    this.todolistService.addToDoList(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss(list);
        //alert("Funciona!!!!");
      }
    );
  }
}
