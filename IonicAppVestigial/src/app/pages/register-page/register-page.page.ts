import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormGroup, FormBuilder, FormControl, Validators } from'@angular/forms'
import { RegisterService } from '../../services/register-service/register.service'


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

  constructor(public modalController: ModalController, public formBuilder: FormBuilder, private registerService: RegisterService) {

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
      phone:  new FormControl('', Validators.required)
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
    console.log("Page");
    console.log(values);
    this.registerService.getData(values);
    //this.router.navigate(["/user"]);
  }

}


