import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverviewBooksComponent } from './overview-books.component';

describe('OverviewBooksComponent', () => {
  let component: OverviewBooksComponent;
  let fixture: ComponentFixture<OverviewBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OverviewBooksComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverviewBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
