import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAuthorizingOfficerComponent } from './edit-authorizing-officer.component';

describe('EditAuthorizingOfficerComponent', () => {
  let component: EditAuthorizingOfficerComponent;
  let fixture: ComponentFixture<EditAuthorizingOfficerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditAuthorizingOfficerComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAuthorizingOfficerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
