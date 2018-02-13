import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule, Routes } from '@angular/router'
import { MatButtonModule } from '@angular/material/button'

import { DefaultComponent } from './default.component'

const routes: Routes = [
  { path: '', component: DefaultComponent },
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatButtonModule
  ],
  declarations: [DefaultComponent]
})
export class DefaultModule { }