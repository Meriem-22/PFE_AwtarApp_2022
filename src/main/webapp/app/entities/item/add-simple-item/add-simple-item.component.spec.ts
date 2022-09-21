import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSimpleItemComponent } from './add-simple-item.component';

describe('AddSimpleItemComponent', () => {
  let component: AddSimpleItemComponent;
  let fixture: ComponentFixture<AddSimpleItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSimpleItemComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSimpleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
