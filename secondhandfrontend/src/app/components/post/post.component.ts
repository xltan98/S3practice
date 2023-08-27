import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Payload, PayloadResponse } from 'src/app/payload';
import { SecondhandService } from 'src/app/secondhand.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit{
  ngOnInit(): void {
    this.id=this.activatedRoute.snapshot.params['id'];
    this.getPost();
   
  }
  sSvc=inject(SecondhandService)
  activatedRoute=inject(ActivatedRoute)
  router=inject(Router)

  id!:string
 payload!:PayloadResponse

  getPost(){
    this.sSvc.post(this.id).then(result=>{
      this.payload=result
      
    })
  }
  onSubmit(){
    this.router.navigate(['confirm', this.id]);
  }

}
