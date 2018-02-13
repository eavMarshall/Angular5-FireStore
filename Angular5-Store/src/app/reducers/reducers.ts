import { StoreModule } from '@ngrx/store';
import { routerReducer } from '@ngrx/router-store';
import { AppReducer, AppState } from './app/AppReducer';

export interface StoreState {
  app: AppState
}

export const reducers = {
  router: routerReducer,
  app: AppReducer
}