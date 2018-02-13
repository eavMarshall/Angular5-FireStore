import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { StoreState } from '../src/app/reducers/reducers'

/*
  This is a blank component to allow the store to be loaded
*/
@Component({
  selector: 'AppReducer.app-test',
  template: ''
})
export class AppReducerTestComponent implements OnInit {

  constructor(public store: Store<StoreState>) { }

  ngOnInit() {
  }

}
