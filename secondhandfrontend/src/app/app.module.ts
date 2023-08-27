import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { SecondhandComponent } from './components/secondhand/secondhand.component';
import { PostComponent } from './components/post/post.component';
import { SuccessComponent } from './components/success/success.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
const appRoutes:Routes=[
  {path:'',component:SecondhandComponent},
  {path:'post/:id',component:PostComponent},
  {path:'confirm/:id',component:SuccessComponent}
]
@NgModule({
  declarations: [
    AppComponent,
    SecondhandComponent,
    PostComponent,
    SuccessComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes,{useHash:true}),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
