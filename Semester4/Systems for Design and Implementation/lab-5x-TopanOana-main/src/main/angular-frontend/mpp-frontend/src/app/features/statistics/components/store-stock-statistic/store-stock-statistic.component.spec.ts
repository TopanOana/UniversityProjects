import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreStockStatisticComponent } from './store-stock-statistic.component';

describe('StoreStockStatisticComponent', () => {
  let component: StoreStockStatisticComponent;
  let fixture: ComponentFixture<StoreStockStatisticComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoreStockStatisticComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StoreStockStatisticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
