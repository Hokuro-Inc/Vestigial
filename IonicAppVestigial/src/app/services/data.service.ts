import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor( private http: HttpClient) { }
  getNotes(){
    return this.http.get("https://raw.githubusercontent.com/LepreprechauN/fakedatabase/main/notes.json");
  }
}
