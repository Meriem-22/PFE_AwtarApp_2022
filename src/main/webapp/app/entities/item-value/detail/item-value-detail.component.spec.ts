import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ItemValueDetailComponent } from './item-value-detail.component';

describe('ItemValue Management Detail Component', () => {
  let comp: ItemValueDetailComponent;
  let fixture: ComponentFixture<ItemValueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ItemValueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ itemValue: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ItemValueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ItemValueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load itemValue on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.itemValue).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
