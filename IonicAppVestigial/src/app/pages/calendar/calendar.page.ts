import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { CalendarComponentOptions, CalendarModal, CalendarModalOptions, DayConfig } from 'ion2-calendar';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';
import { CalendarModalPage } from '../calendar-modal/calendar-modal.page';
import { EventViewPage } from '../event-view/event-view.page';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.page.html',
  styleUrls: ['./calendar.page.scss'],
})
export class CalendarPage implements OnInit {

	events: Event[];
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
	}*/

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
