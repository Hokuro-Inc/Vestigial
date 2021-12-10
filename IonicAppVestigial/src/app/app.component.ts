import { Component } from '@angular/core';
import { RegisterService } from './services/register-service/register.service';
import { LoginService } from './services/login-service/login.service';
import { MenuController, NavController } from '@ionic/angular';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ContactViewPage } from './pages/contact-view/contact-view.page';
import { Contact } from './pages/contacts/contacts.page';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [RegisterService, LoginService]
})

export class AppComponent {
  
  contact: Contact;

  constructor(private contactsService: ContactsService, private navController: NavController, private menuController: MenuController){}
  ngOnInit() {
    //console.log(this.contact);
    let values = {
      "user": sessionStorage.getItem("user"),
      "phone": sessionStorage.getItem("phone"),

      };
      console.log(values);
    this.contactsService.getProfile(JSON.stringify(values)).subscribe(
    (response) =>{
      console.log("RespuestaShow", response)
      if (response != '') {
            let data = JSON.parse(response).Profile;
            console.log("RespuestaData", data)
              this.contact = new Contact(
                data[0].address,
                data[0].alias,
                data[0].description,
                data[0].email,
                data[0].name,
                data[0].owner,
                data[0].phone,
                data[0].prefix,
                data[0].surname
              );
            };
    },
    (error) => console.log("Error", error),
    () => {
      //alert("Funciona!!!!");

    }
  );
  }
  
  public appPages = [
    { title: 'Mi Contacto', url: '/show-profile', icon: 'person' },
    { title: 'Calendario', url: '/calendar', icon: 'calendar' },
    //{ title: 'To Do List', url: '/folder/ToDoList', icon: 'list' },
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
  }
}