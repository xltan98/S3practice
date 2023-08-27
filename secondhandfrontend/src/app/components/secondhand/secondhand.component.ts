import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Payload } from 'src/app/payload';
import { SecondhandService } from 'src/app/secondhand.service';

@Component({
  selector: 'app-secondhand',
  templateUrl: './secondhand.component.html',
  styleUrls: ['./secondhand.component.css']
})
export class SecondhandComponent implements OnInit{
  ngOnInit(): void {
   this.form=this.createForm()
  }
  @ViewChild('toUpload')
  toUpload!:ElementRef

  fb=inject(FormBuilder)
  http=inject(HttpClient)
  sSvc=inject(SecondhandService)
  router=inject(Router)

  form!:FormGroup
  payload!:Payload
  id!:string

  createForm(){
    return this.fb.group({
      name: this.fb.control<string>('',[Validators.required,Validators.minLength(3)]),
      email: this.fb.control<string>('',[Validators.email,Validators.required,Validators.maxLength(128)]),
      phone: this.fb.control<string>('',),
      title: this.fb.control<string>('',[Validators.required,Validators.minLength(5),Validators.maxLength(128)]),
      description: this.fb.control<string>('',[Validators.required])
    })
  }
  processForm() {
    this.payload = this.form.value;
    this.sSvc.upload(this.payload, this.toUpload)
        .then(result => {
            this.id = result.id;
            console.log(">>>>", this.id);

            if (this.id) {
                this.router.navigate(['post', this.id]);
            }
        })
        .catch(error => {
            // Handle any errors that occurred during the upload process
            console.error("Error uploading:", error);
        });
}
  clear(){
    this.form.reset()
  }
}
