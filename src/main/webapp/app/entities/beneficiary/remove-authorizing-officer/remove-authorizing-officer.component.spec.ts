import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveAuthorizingOfficerComponent } from './remove-authorizing-officer.component';

describe('RemoveAuthorizingOfficerComponent', () => {
  let component: RemoveAuthorizingOfficerComponent;
  let fixture: ComponentFixture<RemoveAuthorizingOfficerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RemoveAuthorizingOfficerComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveAuthorizingOfficerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
