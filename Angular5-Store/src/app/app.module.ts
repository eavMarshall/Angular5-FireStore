import { BrowserModule } from '@angular/platform-browser'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { FormsModule } from '@angular/forms'
import { HttpModule } from '@angular/http'
import { NgModule } from '@angular/core'
import { APP_BASE_HREF } from '@angular/common'
import { environment } from '../environments/environment'

import { AngularFireModule } from 'angularfire2'
import { AppComponent } from './app.component'
import { AppRoutingModule } from './app-routing.module'

import { StoreRouterConnectingModule } from '@ngrx/router-store'
import { EffectsModule } from '@ngrx/effects'
import { PagesEffects } from './pages/app/pages.effect'
import { StoreModule } from '@ngrx/store'
import { reducers } from './reducers/reducers'

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    StoreRouterConnectingModule,
    StoreModule.forRoot(reducers),
    AngularFireModule.initializeApp(environment.firebaseConfig),
    EffectsModule.forRoot([PagesEffects])
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
