import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Http } from '@capacitor-community/http';
import { isPlatform } from '@ionic/core';
import { from } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    //console.log("Service", data);
    //return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/Login', data, { responseType: 'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/Login', data, { responseType:'text' });
  }

  request(url: string, data: string) {
    if (isPlatform('capacitor')) {
      return from(Http.request({
        method: 'POST',
        url: url,
        data: data
      })).pipe(map(result => result.data));
    }
    else {
      return this.httpClient.post(`https://proxyhokuro.herokuapp.com/${url}`, data, { responseType:'text' });
    }
  }

}
