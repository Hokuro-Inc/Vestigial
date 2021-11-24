import { Component, OnInit, Input } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.page.html',
  styleUrls: ['./login-page.page.scss'],
})
export class LoginPagePage {

	@Input() firstName: string;
  	@Input() lastName: string;
  	@Input() middleInitial: string;

  	constructor(public modalController: ModalController) {

  	}

  	dismiss() {
    	// using the injected ModalController this page
    	// can "dismiss" itself and optionally pass back data
    	this.modalController.dismiss({
      		'dismissed': true
    	});
  	}

}
