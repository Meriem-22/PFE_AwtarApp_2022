import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCompositeItemComponent } from './add-composite-item.component';

describe('AddCompositeItemComponent', () => {
  let component: AddCompositeItemComponent;
  let fixture: ComponentFixture<AddCompositeItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddCompositeItemComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCompositeItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
