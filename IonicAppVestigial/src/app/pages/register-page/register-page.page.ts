import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormGroup, FormBuilder, FormControl, Validators } from'@angular/forms'
import { RegisterService } from '../../services/register-service/register.service'

import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'register-page',
  templateUrl: './register-page.page.html',
  styleUrls: ['./register-page.page.scss'],
})
export class RegisterPagePage implements OnInit {

  email: string;
  password: string;
  phone: string;
  validations_form: FormGroup;

  constructor(public modalController: ModalController, public formBuilder: FormBuilder, private registerService: RegisterService, private httpClient: HttpClient) {

  }

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
      ])),
      prefix: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
    })

  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(values){
    /*this.registerService.getData(values).subscribe(
      next => {
        console.log(next);
        alert(next);
      },
      error => {
        console.log(error)
      },
      () => {
        console.log("Terminado");
      }
    );*/
    console.log("Page", values);
    var formData = new FormData();
    formData.append("email", values.email);
    formData.append("password", values.password);
    formData.append("prefix", values.prefix);
    formData.append("phone", values.phone);

    let headers = new HttpHeaders();
    headers.append("Access-Control-Allow-Origin", "*");
    headers.append('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    headers.append('Access-Control-Allow-Headers', 'X-Requested-With');
    headers.append('Access-Control-Allow-Credentials', 'true');
    headers.append("Accept", 'application/json');
    headers.append('Content-Type', 'application/json');
    let options = { headers: headers }

    console.log(JSON.stringify(values));

    this.httpClient.post('http://localhost:8080/Prueba/Register', {
      "email" : values.email,
      "password" : values.password,
      "prefix" : values.prefix,
      "phone" : values.phone
    }, options).subscribe(
      (response) => console.log(response),
      (error) => console.log(error)
    )
  }

}


