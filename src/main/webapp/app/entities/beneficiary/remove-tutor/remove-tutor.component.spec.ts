import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveTutorComponent } from './remove-tutor.component';

describe('RemoveTutorComponent', () => {
  let component: RemoveTutorComponent;
  let fixture: ComponentFixture<RemoveTutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RemoveTutorComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveTutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
