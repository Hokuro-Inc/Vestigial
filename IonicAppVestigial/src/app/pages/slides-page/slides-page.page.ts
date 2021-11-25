import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';

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

  constructor( private navCtrl: NavController ) { }


  ngOnInit() {
  }

  onClick(){

    this.navCtrl.navigateRoot('/calendar');




  }

}
