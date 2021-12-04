import { Component, OnInit } from '@angular/core';
import { ToDoListService } from 'src/app/services/todolist-service/todolist.service';
import { ModalController } from '@ionic/angular';
import { TaskViewPage } from '../task-view/task-view.page'

@Component({
  selector: 'app-todolist',
  templateUrl: './todolist.page.html',
  styleUrls: ['./todolist.page.scss'],
})
export class ToDoListPage implements OnInit {

  todolist: Task[];

  constructor(private ToDoListService: ToDoListService, private modalController: ModalController) { }

  ngOnInit() {
    let data = {
      "user": sessionStorage.getItem("user"),
    };

    this.ToDoListService.getData(JSON.stringify(data)).subscribe(
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

          //console.log(this.todolist.forEach(e => console.log(e)));
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
      component: TaskViewPage,
      componentProps: {
      	task: task,
      }
    });
    return await modal.present();
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