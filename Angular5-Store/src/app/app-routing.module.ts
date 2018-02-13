import { NgModule, Optional, SkipSelf, Injectable } from '@angular/core'
import { RouterModule, Routes, Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router'
import { environment } from '../environments/environment'
import { Store } from '@ngrx/store'
import { StoreState } from './reducers/reducers'
import { DefaultPageRoute, LoginPageRoute, ApplicationPageRoute, UnknownPageRoute } from './app-routing'

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private store: Store<StoreState>) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let uid: string = ''
    this.store.select(state => state.app.user.uid).subscribe(value => {
      uid = value
    });
    return uid != ""
  }
}

const appRoutes: Routes = [
  { path: DefaultPageRoute, loadChildren: './pages/default/default.module#DefaultModule' },
  { path: LoginPageRoute, loadChildren: './pages/login-page/login-page.module#LoginPageModule' },
  { path: ApplicationPageRoute, loadChildren: './pages/app/pages.module#PagesRoutingModule', canActivate: [AuthGuardService] },
  { path: UnknownPageRoute, loadChildren: './pages/unknown-page/unknown-page.module#UnknownPageModule' },
  { path: '**', redirectTo: UnknownPageRoute, pathMatch: 'full' }
]

@NgModule({
  imports: [
    (environment.production ? RouterModule.forRoot(appRoutes, { useHash: true }) : RouterModule.forRoot(appRoutes, { useHash: true, enableTracing: true }))
  ],
  exports: [RouterModule],
  providers: [AuthGuardService]
})
export class AppRoutingModule {
  constructor( @Optional() @SkipSelf() parentModule: AppRoutingModule) {
    if (parentModule) {
      throw new Error(
        'AppRoutingModule is already loaded. Import it in the AppModule only')
    }
  }
}