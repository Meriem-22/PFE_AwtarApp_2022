import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChildStatusItemDetailComponent } from './child-status-item-detail.component';

describe('ChildStatusItem Management Detail Component', () => {
  let comp: ChildStatusItemDetailComponent;
  let fixture: ComponentFixture<ChildStatusItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChildStatusItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ childStatusItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ChildStatusItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChildStatusItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load childStatusItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.childStatusItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
