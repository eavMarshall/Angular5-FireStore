import { Component } from '@angular/core'
import { Router } from '@angular/router'

import { Store } from '@ngrx/store'
import { StoreState } from './reducers/reducers'
import { AppReducer } from './reducers/app/AppReducer'
import { LoginPageRoute, ApplicationPageRoute } from './app-routing'
import { Subscription } from 'rxjs/Subscription'

@Component({
  selector: 'app-root',
  template: "<router-outlet></router-outlet>"
})
export class AppComponent {
  subs: Array<Subscription> = []
  isLoginSub: any
  constructor(private store: Store<StoreState>, private router: Router) { }
  ngOnInit() {
    this.subs.push(this.store.select('app').select('user').subscribe(user => {
      if (user.uid == '') {
        this.router.navigate([LoginPageRoute])
      } else {
        this.router.navigate([ApplicationPageRoute])
      }
    }))
  }
  ngOnDestroy() { this.subs.map((sub: Subscription) => { sub.unsubscribe() }) }
}