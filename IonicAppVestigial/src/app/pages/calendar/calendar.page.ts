import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CalendarComponentOptions } from 'ion2-calendar';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.page.html',
  styleUrls: ['./calendar.page.scss'],
})
export class CalendarPage {
	dateMulti: string[];
  	type: 'string';

  	optionsMulti: CalendarComponentOptions = {
    	pickMode: 'multi'
  	};

  	constructor() { }
  	
  	onChange($event) {
    	console.log($event);
  	}

}
