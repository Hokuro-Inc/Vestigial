import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Event } from 'src/app/pages/calendar/calendar.page';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';

@Component({
  selector: 'app-modify-event',
  templateUrl: './modify-event.page.html',
  styleUrls: ['./modify-event.page.scss'],
})
export class ModifyEventPage implements OnInit {

  validations_form: FormGroup;
  event: Event;
  minDate: string;
  maxDate: string;

  constructor(private modalController: ModalController, private formBuilder: FormBuilder, private calendarService: CalendarService) { }

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
    return (group: FormGroup): {[key: string]: any} => {
      let f = group.controls[start];
      let t = group.controls[end];
      if (f.value >= t.value) {
        return {
          dates: "La fecha de inicio debe de ser menor que la de final"
        };
      }
      return {};
    }
  }

  dismiss(event: any, modified: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'event': event,
      'modified': modified
    });
  }

  onSubmit(event: any){
    let data = {
      "user": sessionStorage.getItem("user"),
      "idEvent": this.event.id,
      "name": event.name,
      "description" : event.description,
      "start" : this.getDate(event.start),
      "end" : this.getDate(event.end),
    };

    this.calendarService.updateEvent(JSON.stringify(data)).subscribe(
      (response) => console.log("Respuesta", response),
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(data, true);
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

  getDateFormat(dateStr: string) {
    let date = new Date(dateStr.replace(' CET', ''));
    let day = String(date.getDate()).padStart(2, '0');
		let month = String(date.getMonth() + 1).padStart(2, '0');
		let year = date.getFullYear();
    let hour = String(date.getHours()).padStart(2, '0');
    let minute = String(date.getMinutes()).padStart(2, '0');
		return month + " " + day + " " + year + " " + hour + ":" + minute;
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
