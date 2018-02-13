import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { UnknownPageComponent } from './unknown-page.component'
import { RouterModule, Routes } from '@angular/router'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatCardModule } from '@angular/material/card'

const routes: Routes = [
  { path: '', component: UnknownPageComponent },
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatProgressSpinnerModule,
    MatCardModule
  ],
  declarations: [UnknownPageComponent]
})
export class UnknownPageModule { }
