import { Component } from '@angular/core';
import { RegisterService } from './services/register-service/register.service';
import { LoginService } from './services/login-service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [RegisterService, LoginService]
})

export class AppComponent {
  
  constructor(){}
  

  public appPages = [
    { title: 'Calendario', url: '/calendar', icon: 'calendar' },
    //{ title: 'To Do List', url: '/folder/ToDoList', icon: 'list' },
    { title: 'Agenda', url: '/agenda-page/contacts', icon: 'book' },
    { title: 'Notas', url: '/notepads', icon: 'archive' },
    { title: 'Lista de tareas', url: '/lists', icon: 'list' },
    { title: 'Sobre nosotros', url: '/aboutus', icon: 'information-circle'}

  ];

}