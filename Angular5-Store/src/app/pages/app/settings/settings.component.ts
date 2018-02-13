import { Component, OnInit } from '@angular/core'
import { Observable } from 'rxjs/Observable'
import { Store } from '@ngrx/store'
import { StoreState } from '../../../reducers/reducers'
import { SET_LOADING_ACTION } from '../../../reducers/app/AppReducer'

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  constructor(private store: Store<StoreState>) { }
  ngOnInit() { }
  ngAfterViewInit() {
    setTimeout(() => this.store.dispatch(new SET_LOADING_ACTION(false)))
  }
}