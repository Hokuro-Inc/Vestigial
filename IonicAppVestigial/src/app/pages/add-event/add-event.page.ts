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

  onSubmit(event: any) {
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": event.name,
      "description" : event.description,
      "start" : this.getDate(event.start),
      "end" : this.getDate(event.end),
    };
    console.log(data);
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

  getDate(dateStr: string) {
    let date = new Date(dateStr);
    let day = String(date.getDate()).padStart(2, '0');
		let month = String(date.getMonth() + 1).padStart(2, '0');
		let year = date.getFullYear();
    let hour = String(date.getHours()).padStart(2, '0');
    let minute = String(date.getMinutes()).padStart(2, '0');
		return year + '-' + month + '-' + day + " " + hour + ":" + minute + ":00";
  }
}
