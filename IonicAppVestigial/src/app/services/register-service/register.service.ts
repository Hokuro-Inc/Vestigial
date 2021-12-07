import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    //console.log("Service", data);
    //return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/Register', data, { responseType:'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/Register', data, { responseType:'text' });
  }
}
