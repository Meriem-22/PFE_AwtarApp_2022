import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompositeItemDetailComponent } from './composite-item-detail.component';

describe('CompositeItem Management Detail Component', () => {
  let comp: CompositeItemDetailComponent;
  let fixture: ComponentFixture<CompositeItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompositeItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ compositeItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompositeItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompositeItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load compositeItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.compositeItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
