import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    console.log("Service", data);
    let headers = new HttpHeaders();
    headers.append("Accept", 'application/json');
    headers.append('Content-Type', 'application/json');
    let options = { headers: headers, responseType: 'text' }
    return this.httpClient.post('http://localhost:8080/Vestigial/Register', data, {responseType:'text'});
  }
}
