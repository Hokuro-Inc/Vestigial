import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-notepad',
  templateUrl: './notepad.page.html',
  styleUrls: ['./notepad.page.scss'],
})
export class NotepadPage implements OnInit {


  notes: Observable<any>;

  constructor(private dataService: DataService) { }

  ngOnInit() {

    this.notes = this.dataService.getNotes();
  }

}
