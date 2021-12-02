import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { RegisterPagePage } from '../register-page/register-page.page';
import { LoginPagePage } from '../login-page/login-page.page';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-slides-page',
  templateUrl: './slides-page.page.html',
  styleUrls: ['./slides-page.page.scss'],
})
export class SlidesPagePage implements OnInit {

  slides: { img: string, title: string, desc: string }[] = [
  {
    img: '/assets/icon/logo_vestigial.png',
    title: 'agenda',
    desc: 'Look and use it'
    }

  ];

  constructor( private navCtrl: NavController, public modalController: ModalController ) { }

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

  ngOnInit() {
  }

}
