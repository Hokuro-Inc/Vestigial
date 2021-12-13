import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TodolistService } from 'src/app/services/todolist-service/todolist.service';
import { List } from '../lists-page/lists.page';

@Component({
  selector: 'app-add-todolist',
  templateUrl: './add-todolist.page.html',
  styleUrls: ['./add-todolist.page.scss'],
})
export class AddTodolistPage implements OnInit {

  validations_form: FormGroup;
  lists: List[];

  constructor(private modalController: ModalController, private formBuilder: FormBuilder, private todolistService: TodolistService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
    }, { validator: this.listAlredyExists('name') });
  }

  listAlredyExists(list: string) {
    return (formGroup: FormGroup): {[key: string]: any} => {
      let l = formGroup.controls[list];

      for (let i = 0; i < this.lists.length; i += 1) {
        if (String(l.value).indexOf(this.lists[i].name) > -1 && String(l.value).trim() == this.lists[i].name) {
          return {
            list: "El bloc de notas ya existe"
          }
        }
      }
  
      return {};
    }
  }

  dismiss(list: List) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'list': list
    });
  }

  onSubmit(list: List){
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
