import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule, Routes } from '@angular/router'

import { DayComponent } from './day.component'

const routes: Routes = [
  { path: '', component: DayComponent },
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  declarations: [DayComponent]
})
export class DayModule { }
