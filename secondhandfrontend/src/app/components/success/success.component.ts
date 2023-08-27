import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SecondhandService } from 'src/app/secondhand.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {
  ngOnInit(): void {
    this.id=this.activatedRoute.snapshot.params['id']
    this.process()
    
    
  }

  activatedRoute=inject(ActivatedRoute)
  sSvc=inject(SecondhandService)

  id!:string
  message!:string

  process(){
    this.sSvc.success(this.id).then(result=>{
      this.message=result.message
    })
  }
}
