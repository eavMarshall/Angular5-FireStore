import { Component, OnInit, Input } from '@angular/core'
import { Store } from '@ngrx/store'
import { StoreState } from '../../reducers/reducers'
import { SET_USER_ACTION, SET_LOADING_ACTION } from '../../reducers/app/AppReducer'
import UserState from '../../reducers/app/UserState'
import { Observable } from 'rxjs/Observable'
import { AngularFireAuth } from 'angularfire2/auth'
import * as firebase from 'firebase/app'
import { auth } from 'firebase/app'
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  loginEvent = () => {
    this.store.dispatch(new SET_LOADING_ACTION(true))
    this.afAuth.auth.signInWithRedirect(new firebase.auth.GoogleAuthProvider())
  }
  subs: Array<Subscription> = []

  isLoading: boolean = false
  authState: any = null

  constructor(private store: Store<StoreState>, private afAuth: AngularFireAuth) { }
  ngOnInit() {
    this.store.dispatch(new SET_LOADING_ACTION(true))
    this.subs.push(this.store.select('app').select('isloading').subscribe(isloading => this.isLoading = isloading))
    this.subs.push(this.afAuth.authState.subscribe((auth) => {
      this.authState = auth
      if (null != this.authState) {
        this.store.dispatch(new SET_USER_ACTION(new UserState(auth.uid, auth.displayName, auth.email, auth.photoURL)))
      } else {
        this.store.dispatch(new SET_LOADING_ACTION(false))
      }
    }))
  }
  ngOnDestroy() { this.subs.map((sub: Subscription) => { sub.unsubscribe() }) }
}
