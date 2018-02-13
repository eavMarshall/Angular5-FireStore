import 'rxjs/add/operator/map'
import 'rxjs/add/operator/mergeMap'
import 'rxjs/add/operator/catch'
import { Injectable } from '@angular/core'
import { Action } from '@ngrx/store'
import { Actions, Effect } from '@ngrx/effects'
import { defer } from 'rxjs/observable/defer'
import { Observable } from 'rxjs/Observable'
import { of } from 'rxjs/observable/of'
import { Router } from '@angular/router'

import { SET_SELECTED_PAGE_ACTION, SET_LOADING_ACTION } from '../../reducers/app/AppReducer'
import { Store } from '@ngrx/store'
import { StoreState } from '../../reducers/reducers';

@Injectable()
export class PagesEffects {
  @Effect() setSelectedPage$: Observable<Action> = this.actions$.ofType(new SET_SELECTED_PAGE_ACTION('').type)
    .mergeMap(action =>
      Observable.create(observer => {
        let id = (action as SET_SELECTED_PAGE_ACTION).payload
        if ('/' + id != this.router.url) {
          this.store.dispatch(new SET_LOADING_ACTION(true))
          this.router.navigate([id])
        }
      })
    )
  constructor(private actions$: Actions, public router: Router, private store: Store<StoreState>) { }
}