import { Component, OnInit, HostListener, Inject } from '@angular/core'
import { Store } from '@ngrx/store'
import { StoreState } from '../../reducers/reducers'
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { AngularFireAuth } from 'angularfire2/auth'
import { Subscription } from 'rxjs/Subscription'
import { Action } from 'rxjs/scheduler/Action';
import 'rxjs/add/operator/skip';

import { AppReducer, SET_USER_ACTION, SET_LOADING_ACTION, TOGGLE_SIDE_NAV_MENU_ACTION, SET_DIALOG_ACTION } from '../../reducers/app/AppReducer'
import { SET_SELECTED_PAGE_ACTION, SET_SIDE_NAV_MENU_ACTION, SET_PAGE_ACTION } from '../../reducers/app/AppReducer'
import UserState from '../../reducers/app/UserState'
import ToolbarState from '../../reducers/app/ToolbarState'
import SideNavState from '../../reducers/app/SideNavState'
import PageState from '../../reducers/app/PageState'
import DialogState from '../../reducers/app/DialogState'

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {
  subs: Array<Subscription> = []
  isMobile: boolean = false
  isLoading: boolean = false
  toolbar: ToolbarState
  user: UserState
  sideNav: SideNavState
  page: PageState

  constructor(private store: Store<StoreState>, private afAuth: AngularFireAuth, public mDialog: MatDialog) {
    this.iconClick = this.iconClick.bind(this)
    this.logout = this.logout.bind(this)
  }
  ngOnInit() {
    this.subs.push(this.store.select('app').select('toolbar').subscribe(toolbar => this.toolbar = toolbar))
    this.subs.push(this.store.select('app').select('isloading').subscribe(isloading => this.isLoading = isloading))
    this.subs.push(this.store.select('app').select('user').subscribe(user => this.user = user))
    this.subs.push(this.store.select('app').select('sideNav').subscribe(draw => this.sideNav = draw))
    this.subs.push(this.store.select('app').select('page').subscribe(page => this.page = page))
    this.subs.push(this.store.select('app').select('dialog').skip(1).subscribe(dialog => { this.showQuestionDialog(dialog) }))
  }
  ngOnDestroy() {
    this.subs.map((sub: Subscription) => { sub.unsubscribe() })
  }

  logout() {
    this.store.dispatch(new SET_DIALOG_ACTION(new DialogState(
      'Logout',
      'Are you sure you want to logout?',
      () => {
        this.afAuth.auth.signOut();
        this.store.dispatch(new SET_USER_ACTION(new UserState()))
      }
    )))
  }

  showQuestionDialog(dialog: DialogState) {
    let dialogRef = this.mDialog.open(QuestionDialog, {
      data: { ...dialog }
    })

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        dialog.callBack()
      }
    })
  }

  iconClick(id) {
    switch (id) {
    }
  }

  toggleMenu() { this.store.dispatch(new TOGGLE_SIDE_NAV_MENU_ACTION()) }
  closeMenu() { this.store.dispatch(new SET_SIDE_NAV_MENU_ACTION(false)) }
  menuItemClickHandler(id, title) { this.store.dispatch(new SET_SELECTED_PAGE_ACTION(id)) }

  ngAfterViewInit() {
    setTimeout(() => {
      this.onResize({ target: window });
    }, 100);
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    setTimeout(() => {
      if (undefined !== event.target)
        this.store.dispatch(new SET_PAGE_ACTION(new PageState(event.target.innerWidth, event.target.innerHeight)))
    })
  }
}

@Component({
  selector: 'question-dialog',
  template: `
  <div>
    <h2 mat-dialog-title>{{data.title}}</h2>
    <mat-dialog-content>{{data.message}}</mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button mat-dialog-close>{{data.cancelBtnTxt}}</button>
      <button mat-button [mat-dialog-close]="true">{{data.acceptBtnTxt}}</button>
    </mat-dialog-actions>
  </div>
  `,
})
export class QuestionDialog {
  constructor(
    public dialogRef: MatDialogRef<QuestionDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }
}