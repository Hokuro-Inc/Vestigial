import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    console.log("Service");
    console.log(data);
    //return this.httpClient.post()
  }

}
