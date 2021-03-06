import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from 'src/app/pages/calendar/calendar.page';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.page.html',
  styleUrls: ['./add-event.page.scss'],
})
export class AddEventPage implements OnInit {

  validations_form: FormGroup;
  minDate: string;
  maxDate: string;

  constructor(private modalController: ModalController, public formBuilder: FormBuilder, private calendarService: CalendarService) { }

  ngOnInit() {
    this.validations_form = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      start: new FormControl('', Validators.required),
      end: new FormControl('', Validators.required)
    }, { validator: this.dateLessThan('start', 'end') });
    this.getMinDate();
    this.getMaxDate();
  }

  dateLessThan(start: string, end: string) {
    return (formGroup: FormGroup): {[key: string]: any} => {
      let f = formGroup.controls[start];
      let t = formGroup.controls[end];

      if (f.value >= t.value) {
        return {
          dates: "La fecha de inicio debe de ser menor que la de final"
        };
      }

      return {};
    }
  }

  dismiss(event: Event, id: string) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'event': event,
      'id': id
    });
  }

  onSubmit(event: Event) {
    let data = {
      "user": sessionStorage.getItem("user"),
      "name": event.name,
      "description": event.description,
      "start": this.getDate(event.start),
      "end": this.getDate(event.end),
    };

    let id = '0';
    this.calendarService.addEvent(JSON.stringify(data)).subscribe(
      (response) => {
        console.log("Respuesta", response);
        id = JSON.parse(response).idEvent;
      },
      (error) => console.log("Error", error),
      () => {
        this.dismiss(event, id);
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

  getMinDate() {
    let date = new Date();
    let day = String(date.getDate()).padStart(2, '0');
		let month = String(date.getMonth() + 1).padStart(2, '0');
		let year = date.getFullYear();
    this.minDate = year + '-' + month + '-' + day
  }

  getMaxDate() {
    let date = new Date();
    let year = date.getFullYear() + 100;
    this.maxDate = String(year);
  }

}
