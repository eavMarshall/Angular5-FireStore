import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule, Routes } from '@angular/router'

import { MatSlideToggleModule } from '@angular/material/slide-toggle'
import { SettingsComponent } from './settings.component'

const routes: Routes = [
  { path: '', component: SettingsComponent },
]

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatSlideToggleModule
  ],
  declarations: [SettingsComponent]
})
export class SettingsModule { }
