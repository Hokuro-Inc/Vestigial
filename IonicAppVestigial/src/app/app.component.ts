import { Component } from '@angular/core';
import { RegisterService } from './services/register-service/register.service';
import { LoginService } from './services/login-service/login.service';
import { MenuController, NavController } from '@ionic/angular';
import { Contact } from './pages/contacts/contacts.page';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [RegisterService, LoginService]
})

export class AppComponent {
  
  contact: Contact;
  user: String;
  
  constructor(private navController: NavController, private menuController: MenuController){}
  
  ngOnInit() {
    //console.log(this.contact);
    this.user = sessionStorage.getItem("user");
  };
  
  public appPages = [
    { title: 'Mi Contacto', url: '/show-profile', icon: 'person' },
    { title: 'Calendario', url: '/calendar', icon: 'calendar' },
    { title: 'Agenda', url: '/contacts', icon: 'book' },
    { title: 'Notas', url: '/notepads', icon: 'archive' },
    { title: 'Lista de tareas', url: '/lists', icon: 'list' },
    { title: 'Sobre nosotros', url: '/aboutus', icon: 'information-circle'}
  ];

  logout() {
    sessionStorage.removeItem("user");
    sessionStorage.removeItem("phone");
    this.navController.navigateBack(['/']);
    this.menuController.close();
    this.menuController.enable(false);
  }
}