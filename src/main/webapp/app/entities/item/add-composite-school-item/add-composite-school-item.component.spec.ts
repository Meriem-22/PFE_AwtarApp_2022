import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCompositeSchoolItemComponent } from './add-composite-school-item.component';

describe('AddCompositeSchoolItemComponent', () => {
  let component: AddCompositeSchoolItemComponent;
  let fixture: ComponentFixture<AddCompositeSchoolItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddCompositeSchoolItemComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCompositeSchoolItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
