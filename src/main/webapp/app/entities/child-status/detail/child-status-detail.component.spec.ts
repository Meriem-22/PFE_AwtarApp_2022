import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChildStatusDetailComponent } from './child-status-detail.component';

describe('ChildStatus Management Detail Component', () => {
  let comp: ChildStatusDetailComponent;
  let fixture: ComponentFixture<ChildStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChildStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ childStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ChildStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ChildStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load childStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.childStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
