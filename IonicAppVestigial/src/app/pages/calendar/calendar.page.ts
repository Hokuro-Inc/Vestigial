import { formatDate } from '@angular/common';
import { Component, Inject, LOCALE_ID, OnInit, ViewChild } from '@angular/core';
import { AlertController, ModalController } from '@ionic/angular';
import { CalendarComponent, CalendarComponentOptions, CalendarModal, CalendarModalOptions, DayConfig } from 'ion2-calendar';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';
import { EventViewPage } from '../event-view/event-view.page';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.page.html',
  styleUrls: ['./calendar.page.scss'],
})
export class CalendarPage implements OnInit {

	/*events: Event[];
	filteredEvents: Event[];
	date: string;
  	type: 'string';

  	options: CalendarComponentOptions = {
    	pickMode: 'single',
		showMonthPicker: true,
		weekStart: 1,
		weekdays: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
		monthPickerFormat : ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic']
  	};

  	constructor(private calendarService: CalendarService, private modalController: ModalController) { }
  	
	ngOnInit() {
		let curDate = new Date();
		let day = String(curDate.getDate()).padStart(2, '0');
		let month = curDate.getMonth() + 1;
		let year = curDate.getFullYear();
		let curDateStr = year + '-' + month + '-' + day;

		let user = {
			"user": sessionStorage.getItem("user"),
		};

		this.calendarService.getData(JSON.stringify(user)).subscribe(
			(response) => {
				//console.log("Respuesta", response);
				if (response != '') {
					let data = JSON.parse(response).Calendar;
					this.events = [];

					data.forEach((element: any) => {
						this.events.push(new Event(
							element.id,
							element.owner,
							element.name,
							element.description,
							element.start,
							element.end
						));
					});

					this.filteredEvents = this.events.filter(item => {
						return item.start == curDateStr;
					});
         		 	//this.events.forEach(e => console.log(e));
					//this.filteredEvents.forEach(e => console.log(e));
				}
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
		);
	}

  	onChange(event: any) {
    	let curDate = event._d;
		let day = String(curDate.getDate()).padStart(2, '0');
		let month = curDate.getMonth() + 1;
		let year = curDate.getFullYear();
		let curDateStr = year + '-' + month + '-' + day;

		this.filteredEvents = this.events.filter(item => {
			return item.start == curDateStr;
		});
  	}

	async showEvent(event: Event) {
		const modal = await this.modalController.create({
			component: EventViewPage,
			componentProps: {
				event: event
			}
		});
		return await modal.present();
	}

	/*async openCalendar() {
		const modal = await this.modalController.create({
			component: CalendarModalPage
		});
		return await modal.present();
	}

	async openCalendar() {
		let _daysConfig: DayConfig[] = [];
		for (let i = 0; i < 31; i++) {
			_daysConfig.push({
			date: new Date(2017, 0, i + 1),
			subTitle: `$${i + 1}`,
			marked:true
			})
		}
		const options: CalendarModalOptions = {
			title: 'Calendario',
			color:'primary',
			pickMode: 'single',
			weekStart: 1,
			weekdays: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
			
		};
  
		let myCalendar =  await this.modalController.create({
			component: CalendarModal,
			componentProps: { 
				options: options
			}
		});
   
	  	myCalendar.present();

		console.log((await myCalendar.onDidDismiss()).data);
	}*/

	events: Event[] = [];
	filteredEvents: Event[] = [];
	date: string;
  	type: 'string';

	eventSource = [];
  	viewTitle: string;
 
	calendar = {
		mode: 'month',
		currentDate: new Date(),
	};
	
	selectedDate: Date;
	
	@ViewChild(CalendarComponent) myCal: CalendarComponent;
	
	constructor(
		private alertCtrl: AlertController,
		@Inject(LOCALE_ID) private locale: string,
		private modalCtrl: ModalController,
		private calendarService: CalendarService
	) { }
	
	ngOnInit() {
		let curDate = new Date();
		let day = String(curDate.getDate()).padStart(2, '0');
		let month = curDate.getMonth() + 1;
		let year = curDate.getFullYear();
		let curDateStr = year + '-' + month + '-' + day;

		let user = {
			"user": sessionStorage.getItem("user"),
		};

		this.calendarService.getData(JSON.stringify(user)).subscribe(
			(response) => {
				//console.log("Respuesta", response);
				if (response != '') {
					let data = JSON.parse(response).Calendar;
					this.events = [];

					data.forEach((element: any) => {
						this.events.push(new Event(
							element.id,
							element.owner,
							element.name,
							element.description,
							element.start,
							element.end
						));
					});

					this.filteredEvents = this.events.filter(item => {
						return item.start == curDateStr;
					});
         		 	//this.events.forEach(e => console.log(e));
					//this.filteredEvents.forEach(e => console.log(e));
				}
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
		);
	}
	
	// Selected date reange and hence title changed
	onViewTitleChanged(title: string) {
		this.viewTitle = title;
	}
	
	// Calendar event was clicked
	async onEventSelected(event: any) {
		console.log("entra");
		// Use Angular date pipe for conversion
		let start = formatDate(event.startTime, 'medium', this.locale);
		let end = formatDate(event.endTime, 'medium', this.locale);
	
		const alert = await this.alertCtrl.create({
		header: event.title,
		subHeader: event.desc,
		message: 'From: ' + start + '<br><br>To: ' + end,
		buttons: ['OK'],
		});
		alert.present();
	}

	onTimeSelected(ev: any) {
        console.log('Selected time: ' + ev.selectedTime + ', hasEvents: ' +
            (ev.events !== undefined && ev.events.length !== 0) + ', disabled: ' + ev.disabled);
    }
	
	createRandomEvents() {
		var events = [];
		for (var i = 0; i < 50; i += 1) {
		var date = new Date();
		var eventType = Math.floor(Math.random() * 2);
		var startDay = Math.floor(Math.random() * 90) - 45;
		var endDay = Math.floor(Math.random() * 2) + startDay;
		var startTime;
		var endTime;
		if (eventType === 0) {
			startTime = new Date(
			Date.UTC(
				date.getUTCFullYear(),
				date.getUTCMonth(),
				date.getUTCDate() + startDay
			)
			);
			if (endDay === startDay) {
			endDay += 1;
			}
			endTime = new Date(
			Date.UTC(
				date.getUTCFullYear(),
				date.getUTCMonth(),
				date.getUTCDate() + endDay
			)
			);
			events.push({
			title: 'All Day - ' + i,
			startTime: startTime,
			endTime: endTime,
			allDay: true,
			});
		} else {
			var startMinute = Math.floor(Math.random() * 24 * 60);
			var endMinute = Math.floor(Math.random() * 180) + startMinute;
			startTime = new Date(
			date.getFullYear(),
			date.getMonth(),
			date.getDate() + startDay,
			0,
			date.getMinutes() + startMinute
			);
			endTime = new Date(
			date.getFullYear(),
			date.getMonth(),
			date.getDate() + endDay,
			0,
			date.getMinutes() + endMinute
			);
			events.push({
			title: 'Event - ' + i,
			startTime: startTime,
			endTime: endTime,
			allDay: false,
			});
		}
		}
		this.eventSource = events;
	}
	
	removeEvents() {
		this.eventSource = [];
	}


}

export class Event {
	id: string;
	owner: string;
	name: string;
	description: string;
	start: string;
	end: string;

	constructor(id: string, owner: string, name: string, description: string, start: string, end: string) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.start = start;
		this.end = end;
	}
}
