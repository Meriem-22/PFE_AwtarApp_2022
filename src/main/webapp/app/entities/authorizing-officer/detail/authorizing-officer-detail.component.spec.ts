import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AuthorizingOfficerDetailComponent } from './authorizing-officer-detail.component';

describe('AuthorizingOfficer Management Detail Component', () => {
  let comp: AuthorizingOfficerDetailComponent;
  let fixture: ComponentFixture<AuthorizingOfficerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthorizingOfficerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ authorizingOfficer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AuthorizingOfficerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AuthorizingOfficerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load authorizingOfficer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.authorizingOfficer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
