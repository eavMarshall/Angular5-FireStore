import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { AngularFireAuthModule } from 'angularfire2/auth'
import { AngularFireModule } from 'angularfire2'
import { environment } from '../../../environments/environment'

import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'

import { Store, StoreModule } from '@ngrx/store'
import { reducers, StoreState } from '../../reducers/reducers'
import { LoginPageComponent } from './login-page.component'

describe('LoginPageComponent tests', () => {
  let component: LoginPageComponent
  let fixture: ComponentFixture<LoginPageComponent>
  let store: Store<StoreState>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        StoreModule.forRoot(reducers),
        MatToolbarModule,
        MatButtonModule,
        MatCardModule,
        MatProgressSpinnerModule,
        AngularFireAuthModule,
        AngularFireModule.initializeApp(environment.firebaseConfig)
      ],
      declarations: [LoginPageComponent]
    })
      .compileComponents()
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  });

  it('should create', () => {
    expect(component).toBeTruthy()
  });
});
