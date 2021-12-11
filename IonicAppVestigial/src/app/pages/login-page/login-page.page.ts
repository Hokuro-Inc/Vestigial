import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MenuController, ModalController, ToastController } from '@ionic/angular';
import { LoginService } from 'src/app/services/login-service/login.service';
import { Router } from '@angular/router';
import { duration } from 'moment';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.page.html',
  styleUrls: ['./login-page.page.scss'],
})
export class LoginPagePage implements OnInit {

  	validations_form: FormGroup;

  	constructor(private modalController: ModalController, private formBuilder: FormBuilder, private loginService: LoginService, private router: Router, private menuController: MenuController, private toastController: ToastController) { }

	ngOnInit(){
		this.validations_form = this.formBuilder.group({
			email: new FormControl('', Validators.compose([
			Validators.required,
			Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$')
			])),
			password: new FormControl('', Validators.compose([
			Validators.minLength(5),
			Validators.required,
			Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')
			]))
		});
	}

  	dismiss() {
    	// using the injected ModalController this page
    	// can "dismiss" itself and optionally pass back data
    	this.modalController.dismiss({
      		'dismissed': true
    	});
  	}

	onSubmit(values: any) {
		//console.log("Page", values);
		let res = false;
		let user: any;

		this.loginService.getData(values).subscribe(
			(response) => {
				console.log("Respuesta", response);
				let data = JSON.parse(response);
				if (data.Mensaje.includes("OK")) {
					res = true;
					user = data.user[0];
				}
			},
			(error) => console.log("Error", error),
			async () => {
				if (res == true) {
					this.dismiss();
					this.menuController.enable(true);
					this.router.navigate(['/calendar']);
					sessionStorage.setItem("user", values.email);
					sessionStorage.setItem("phone", user.phone + "-" + user.prefix);
					//alert("Funciona!!!");
				}
				else {
					const toast = await this.toastController.create({
						color: "primary",
						duration: 2000,
						message: "Error en inicio de sesion",
						translucent: true
					});
					return await toast.present();
					//alert("Error en inicio de sesion");
				}
			}	
		);
	}

}
