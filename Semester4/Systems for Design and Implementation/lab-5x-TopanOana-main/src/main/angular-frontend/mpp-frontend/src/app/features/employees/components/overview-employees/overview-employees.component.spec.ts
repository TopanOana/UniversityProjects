import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewEmployeesComponent } from './overview-employees.component';

describe('OverviewEmployeesComponent', () => {
  let component: OverviewEmployeesComponent;
  let fixture: ComponentFixture<OverviewEmployeesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverviewEmployeesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverviewEmployeesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
