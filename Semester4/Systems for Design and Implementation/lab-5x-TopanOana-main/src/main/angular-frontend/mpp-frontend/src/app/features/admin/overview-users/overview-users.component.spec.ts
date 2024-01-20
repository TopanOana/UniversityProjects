import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewUsersComponent } from './overview-users.component';

describe('OverviewUsersComponent', () => {
  let component: OverviewUsersComponent;
  let fixture: ComponentFixture<OverviewUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverviewUsersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverviewUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
