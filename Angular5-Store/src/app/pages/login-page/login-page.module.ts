import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule, Routes } from '@angular/router'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { AngularFireAuthModule } from 'angularfire2/auth'

import { LoginPageComponent } from './login-page.component'

const routes: Routes = [
  { path: '', component: LoginPageComponent },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    AngularFireAuthModule
  ],
  declarations: [LoginPageComponent]
})
export class LoginPageModule { }