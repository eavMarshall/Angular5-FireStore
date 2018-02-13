import { CommonModule } from '@angular/common'
import { NgModule, Optional, SkipSelf } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { AngularFireAuthModule } from 'angularfire2/auth'

import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatExpansionModule } from '@angular/material/expansion'
import { MatListModule } from '@angular/material/list'
import { MatProgressBarModule } from '@angular/material/progress-bar'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { MatCardModule } from '@angular/material/card'
import { MatDialogModule } from '@angular/material/dialog';

import { PagesComponent, QuestionDialog } from './pages.component'

const routes: Routes = [
  {
    path: '', component: PagesComponent,
    children: [
      { path: 'day', loadChildren: './day/day.module#DayModule' },
      { path: 'settings', loadChildren: './settings/settings.module#SettingsModule' },
      { path: '', redirectTo: 'day', pathMatch: 'full' }
    ]
  }
]

@NgModule({
  imports: [
    CommonModule,
    AngularFireAuthModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatExpansionModule,
    MatListModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatDialogModule,
    RouterModule.forChild(routes)
  ],
  declarations: [PagesComponent, QuestionDialog],
  entryComponents: [PagesComponent, QuestionDialog]
})
export class PagesRoutingModule { }