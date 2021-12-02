import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { LoginService } from 'src/app/services/login-service/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.page.html',
  styleUrls: ['./login-page.page.scss'],
})
export class LoginPagePage implements OnInit {

  	validations_form: FormGroup;

  	constructor(public modalController: ModalController, private formBuilder: FormBuilder, private loginService: LoginService, private router: Router) { }

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
		var res = false;

		this.loginService.getData(values).subscribe(
			(response) => {
				//console.log("Respuesta", response);
				var data = JSON.parse(response);

				if (data.Mensaje.includes("OK")) res = true;
			},
			(error) => console.log("Error", error),
			() => {
				if (res == true) {
					this.dismiss();
					sessionStorage.setItem("user", values.email);
					this.router.navigate(['/calendar']);
					//alert("Funciona!!!");
				}
				else {
					alert("Error en inicio de sesion");
				}
			}	
		);
	}

}
