import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookStockStatisticComponent } from './book-stock-statistic.component';

describe('BookStockStatisticComponent', () => {
  let component: BookStockStatisticComponent;
  let fixture: ComponentFixture<BookStockStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookStockStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookStockStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
