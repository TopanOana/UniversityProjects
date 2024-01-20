import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewStoresComponent } from './overview-stores.component';

describe('OverviewStoresComponent', () => {
  let component: OverviewStoresComponent;
  let fixture: ComponentFixture<OverviewStoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverviewStoresComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverviewStoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
