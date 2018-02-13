import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { Store, StoreModule } from '@ngrx/store'

import { reducers, StoreState } from './../reducers'
import { AppReducerTestComponent } from './../../../../testComponents/AppReducer.test.component'

import { SET_USER_ACTION, SET_SELECTED_PAGE_ACTION, SET_PAGE_ACTION, SET_DIALOG_ACTION } from './AppReducer'
import UserState from './UserState'
import PageState from './PageState'
import ToolbarState from './ToolbarState'
import DrawState from './DrawState'
import DialogState from './DialogState'

describe('AppReducer tests', () => {
  let component: AppReducerTestComponent
  let fixture: ComponentFixture<AppReducerTestComponent>
  let store: Store<StoreState>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [StoreModule.forRoot(reducers)],
      declarations: [AppReducerTestComponent]
    })
      .compileComponents()
  }))

  beforeEach(() => {
    fixture = TestBed.createComponent(AppReducerTestComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
    expect(component.store).toBeTruthy()
  })

  it('should have default user state', () => {
    let user: UserState
    component.store.select('app').select('user').subscribe(val => user = val)
    expect(user.uid).toBe('')
    expect(user.email).toBe('')
    expect(user.photoUrl).toBe('')
    expect(user.displayName).toBe('Anonymous')
  })

  it('should have page state not undefined', () => {
    let page: PageState
    component.store.select('app').select('page').subscribe(val => page = val)
    expect(page).toBeDefined()
  })

  it('should have isloading = false', () => {
    let isloading: boolean
    component.store.select('app').select('isloading').subscribe(val => isloading = val)
    expect(isloading).toBeFalsy()
  })

  it('should have toolbar state not undefined', () => {
    let toolbar: ToolbarState
    component.store.select('app').select('toolbar').subscribe(val => toolbar = val)
    expect(toolbar).toBeDefined()
  })

  it('should have sideNav state not undefined', () => {
    let draw: DrawState
    component.store.select('app').select('draw').subscribe(val => draw = val)
    expect(draw).toBeDefined()
  })

  it('should update user state', () => {
    component.store.dispatch(new SET_USER_ACTION(new UserState('uid', 'name', 'email', 'url')))
    let user: UserState
    component.store.select('app').select('user').subscribe(val => user = val)
    expect(user.uid).toBe('uid')
    expect(user.email).toBe('email')
    expect(user.photoUrl).toBe('url')
    expect(user.displayName).toBe('name')
  })

  it('should update sideNav.selectedid, toolbar.title state', () => {
    let test1 = 'app/day'
    let test2 = 'app/settings'
    var draw: DrawState
    var toolbar: ToolbarState

    component.store.dispatch(new SET_SELECTED_PAGE_ACTION(test1))
    component.store.select('app').select('draw').subscribe(val => draw = val)
    component.store.select('app').select('toolbar').subscribe(val => toolbar = val)
    expect(draw.selectedid).toEqual(test1)
    expect(toolbar.title).toEqual('Day')

    component.store.dispatch(new SET_SELECTED_PAGE_ACTION(test2))
    component.store.select('app').select('draw').subscribe(val => draw = val)
    component.store.select('app').select('toolbar').subscribe(val => toolbar = val)
    expect(draw.selectedid).toEqual(test2)
    expect(toolbar.title).toEqual('Settings')
  })

  it('should update page state', () => {
    var page: PageState
    var draw: DrawState

    component.store.dispatch(new SET_PAGE_ACTION(new PageState(1001, 1000)))
    component.store.select('app').select('draw').subscribe(val => sideNav = val)
    component.store.select('app').select('page').subscribe(val => page = val)
    expect(draw.isOpen).toBeTruthy()
    expect(page.width).toEqual(1001)
    expect(page.isMobile).toBeFalsy()

    component.store.dispatch(new SET_PAGE_ACTION(new PageState(499, 1000)))
    component.store.select('app').select('draw').subscribe(val => draw = val)
    component.store.select('app').select('page').subscribe(val => page = val)
    expect(page.width).toEqual(499)
    expect(page.isMobile).toBeTruthy()
    expect(draw.isOpen).toBeFalsy()
  })

  it('should set DialogState', () => {
    var dialog: DialogState
    component.store.dispatch(new SET_DIALOG_ACTION(new DialogState('title', 'message', () => { })))
    component.store.select('app').select('dialog').subscribe(val => dialog = val)
    expect(dialog.title).toEqual('title')
    expect(dialog.message).toEqual('message')
    expect(dialog.acceptBtnTxt).toEqual('Ok')
    expect(dialog.cancelBtnTxt).toEqual('Cancel')
  })
})
