import { async, ComponentFixture, TestBed } from '@angular/core/testing'
import { Store, StoreModule } from '@ngrx/store'
import { reducers, StoreState } from '../../../reducers/reducers'
import { DayComponent } from './day.component'

describe('DayComponent tests', () => {
  let component: DayComponent
  let fixture: ComponentFixture<DayComponent>
  let store: Store<StoreState>

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [StoreModule.forRoot(reducers)],
      declarations: [DayComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DayComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  });

  it('should create', () => {
    expect(component).toBeTruthy()
  });
});
