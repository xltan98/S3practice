import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable, inject } from '@angular/core';
import { Payload } from './payload';
import {firstValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SecondhandService {

  http=inject(HttpClient)

  upload(payload:Payload,elemRef:ElementRef):Promise<any>{
    const url="http://localhost:8080/upload"
    const data= new FormData()
    data.append("payload", JSON.stringify(payload));
    // data.set("name",payload.name)
    // data.set("description",payload.description)
    // data.set("email",payload.email)
    // data.set("phone",payload.phone)
    // data.set("title",payload.title)
    data.set("imgFile",elemRef.nativeElement.files[0])

    return firstValueFrom(
      this.http.post<any>(url,data)
    )
  }

  post(id:String):Promise<any>{
    const url1=`http://localhost:8080/posts/${id}`
    return firstValueFrom(this.http.get<any>(url1))
  }

  success(id:String):Promise<any>{
    const url1=`http://localhost:8080/posting/${id}`
    return firstValueFrom(this.http.put<any>(url1,id))
  }
}
