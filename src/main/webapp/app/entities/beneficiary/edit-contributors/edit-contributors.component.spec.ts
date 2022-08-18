import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContributorsComponent } from './edit-contributors.component';

describe('EditContributorsComponent', () => {
  let component: EditContributorsComponent;
  let fixture: ComponentFixture<EditContributorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EditContributorsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditContributorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
