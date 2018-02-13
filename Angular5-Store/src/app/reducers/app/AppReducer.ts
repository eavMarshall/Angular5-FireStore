import { ActionReducer, Action } from '@ngrx/store'
import UserState from './UserState'
import ToolbarState from './ToolbarState'
import SideNavState from './SideNavState'
import PageState from './PageState'
import DialogState from './DialogState'
import FabState from './FabState'

const reducerName = 'AppReducer@@: '
const SET_USER: string = reducerName + 'set user state'
const SET_TOOLBAR: string = reducerName + 'set toolbar state'
const SET_SIDENAV: string = reducerName + 'set side nav state'
const SET_SELECTED_PAGE: string = reducerName + 'set selected page state'
const SET_LOADING: string = reducerName + 'set loading state'
const TOGGLE_SIDE_NAV_MENU: string = reducerName + 'toggle side nav menu'
const SET_SIDE_NAV_MENU: string = reducerName + 'set side nav menu open or close'
const SET_PAGE: string = reducerName + 'set page state'
const SET_DIALOG: string = reducerName + 'set dialog state'

export class SET_USER_ACTION implements Action {
  readonly type: string = SET_USER
  constructor(public payload: UserState) { }
}
export class SET_TOOLBAR_ACTION implements Action {
  readonly type: string = SET_TOOLBAR
  constructor(public payload: ToolbarState) { }
}
export class SET_SIDENAV_ACTION implements Action {
  readonly type: string = SET_SIDENAV
  constructor(public payload: SideNavState) { }
}
export class SET_SIDE_NAV_MENU_ACTION implements Action {
  readonly type: string = SET_SIDE_NAV_MENU
  constructor(public payload: boolean) { }
}
export class SET_SELECTED_PAGE_ACTION implements Action {
  readonly type: string = SET_SELECTED_PAGE
  constructor(public payload: string) { }
}
export class SET_LOADING_ACTION implements Action {
  readonly type: string = SET_LOADING
  constructor(public payload: boolean) { }
}
export class SET_PAGE_ACTION implements Action {
  readonly type: string = SET_PAGE
  constructor(public payload: PageState) { }
}
export class TOGGLE_SIDE_NAV_MENU_ACTION implements Action {
  readonly type: string = TOGGLE_SIDE_NAV_MENU
}
export class SET_DIALOG_ACTION implements Action {
  readonly type: string = SET_DIALOG
  timeStamp: number = new Date().getDate() // give a new time stamp
  constructor(public payload: DialogState) { }
}

export interface AppState {
  dialog: DialogState
  sideNav: SideNavState
  fab: FabState,
  toolbar: ToolbarState
  user: UserState
  page: PageState
  selectedPage: string
  isloading: boolean
}

const defaultState: AppState = {
  dialog: new DialogState(),
  sideNav: new SideNavState(),
  fab: new FabState(),
  toolbar: new ToolbarState('Day'),
  user: new UserState(),
  page: new PageState(),
  selectedPage: '',
  isloading: false
}

export function AppReducer(state: AppState = defaultState, action: Action) {
  switch (action.type) {
    case SET_USER: return { ...state, user: (action as SET_USER_ACTION).payload }
    case SET_TOOLBAR: return { ...state, toolbar: (action as SET_TOOLBAR_ACTION).payload }
    case SET_SIDENAV: return { ...state, sideNav: (action as SET_SIDENAV_ACTION).payload }
    case SET_SELECTED_PAGE:
      var newState = { ...state }
      newState.sideNav.selectedid = (action as SET_SELECTED_PAGE_ACTION).payload
      newState.toolbar.title = state.sideNav.menuList.find(menuItem => menuItem.id == newState.sideNav.selectedid).title
      if (newState.page.isMobile) {
        newState.sideNav.isOpen = false
      }
      return newState
    case SET_LOADING: return { ...state, isloading: (action as SET_LOADING_ACTION).payload }
    case TOGGLE_SIDE_NAV_MENU: return { ...state, sideNav: { ...state.sideNav, isOpen: !state.sideNav.isOpen } }
    case SET_SIDE_NAV_MENU: return { ...state, sideNav: { ...state.sideNav, isOpen: (action as SET_SIDE_NAV_MENU_ACTION).payload } }
    case SET_PAGE:
      var page = (action as SET_PAGE_ACTION).payload
      var newState = { ...state }
      newState.page = page
      newState.sideNav.isOpen = newState.sideNav.isOpen ? page.width < 500 ? false : true : page.width > 1000 && page.width ? true : false
      return newState
    case SET_DIALOG: return { ...state, dialog: (action as SET_DIALOG_ACTION).payload }
    default:
      return state
  }
}
