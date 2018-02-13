import { Component, OnInit } from '@angular/core'
import { Store } from '@ngrx/store';
import { StoreState } from '../../../reducers/reducers'
import { SET_LOADING_ACTION } from '../../../reducers/app/AppReducer'

@Component({
  selector: 'app-day',
  templateUrl: './day.component.html',
  styleUrls: ['./day.component.css']
})
export class DayComponent implements OnInit {
  constructor(private store: Store<StoreState>) { }
  ngOnInit() { }
  ngAfterViewInit() {
    setTimeout(() => this.store.dispatch(new SET_LOADING_ACTION(false)))
  }
}
