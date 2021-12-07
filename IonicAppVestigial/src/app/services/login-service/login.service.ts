import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    //console.log("Service", data);
    //return this.httpClient.post('https://hokuro.xyz/Vestigial/Login', data, { responseType:'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/Login', data, { responseType:'text' });
  }
}
