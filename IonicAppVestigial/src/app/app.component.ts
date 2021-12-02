import { Component } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { RegisterPagePage } from './pages/register-page/register-page.page';
import { LoginPagePage } from './pages/login-page/login-page.page';
import { RegisterService } from './services/register-service/register.service';
import { LoginService } from './services/login-service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [RegisterService, LoginService]
})

export class AppComponent {
  
  constructor(public modalController: ModalController){}
  
  async openRegister() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: RegisterPagePage,
    });
    return await modal.present();
  }

  async openLogin() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: LoginPagePage,
    });
    return await modal.present();
  }

  public appPages = [
    { title: 'Calendario', url: '/calendar', icon: 'calendar' },
    //{ title: 'To Do List', url: '/folder/ToDoList', icon: 'list' },
    { title: 'Agenda', url: '/agenda-page/contacts', icon: 'book' },
    //{ title: 'Notas', url: '/folder/Notas', icon: 'archive' },

  ];

}
