import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from 'src/app/pages/calendar/calendar.page';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.page.html',
  styleUrls: ['./add-event.page.scss'],
})
export class AddEventPage implements OnInit {

  validations_form: FormGroup;

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private calendarService: CalendarService, private navController: NavController) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      start: new FormControl('', Validators.required),
      end: new FormControl('', Validators.required)
    })
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  onSubmit(note: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": note.name,
      "description" : note.description,
      "start" : note.start,
      "end" : note.end,
    };
    console.log(data)
    this.calendarService.addEvent(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        this.dismiss();
        this.navController.navigateBack(['/calendar']);
        //alert("Funciona!!!!");
      }
    );
  }
}
