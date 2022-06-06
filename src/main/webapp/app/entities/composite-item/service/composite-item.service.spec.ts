import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompositeItem, CompositeItem } from '../composite-item.model';

import { CompositeItemService } from './composite-item.service';

describe('CompositeItem Service', () => {
  let service: CompositeItemService;
  let httpMock: HttpTestingController;
  let elemDefault: ICompositeItem;
  let expectedResult: ICompositeItem | ICompositeItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompositeItemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      quantity: 0,
      archivated: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CompositeItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CompositeItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompositeItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quantity: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompositeItem', () => {
      const patchObject = Object.assign({}, new CompositeItem());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompositeItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quantity: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CompositeItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCompositeItemToCollectionIfMissing', () => {
      it('should add a CompositeItem to an empty array', () => {
        const compositeItem: ICompositeItem = { id: 123 };
        expectedResult = service.addCompositeItemToCollectionIfMissing([], compositeItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(compositeItem);
      });

      it('should not add a CompositeItem to an array that contains it', () => {
        const compositeItem: ICompositeItem = { id: 123 };
        const compositeItemCollection: ICompositeItem[] = [
          {
            ...compositeItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addCompositeItemToCollectionIfMissing(compositeItemCollection, compositeItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompositeItem to an array that doesn't contain it", () => {
        const compositeItem: ICompositeItem = { id: 123 };
        const compositeItemCollection: ICompositeItem[] = [{ id: 456 }];
        expectedResult = service.addCompositeItemToCollectionIfMissing(compositeItemCollection, compositeItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(compositeItem);
      });

      it('should add only unique CompositeItem to an array', () => {
        const compositeItemArray: ICompositeItem[] = [{ id: 123 }, { id: 456 }, { id: 82218 }];
        const compositeItemCollection: ICompositeItem[] = [{ id: 123 }];
        expectedResult = service.addCompositeItemToCollectionIfMissing(compositeItemCollection, ...compositeItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const compositeItem: ICompositeItem = { id: 123 };
        const compositeItem2: ICompositeItem = { id: 456 };
        expectedResult = service.addCompositeItemToCollectionIfMissing([], compositeItem, compositeItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(compositeItem);
        expect(expectedResult).toContain(compositeItem2);
      });

      it('should accept null and undefined values', () => {
        const compositeItem: ICompositeItem = { id: 123 };
        expectedResult = service.addCompositeItemToCollectionIfMissing([], null, compositeItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(compositeItem);
      });

      it('should return initial array if no CompositeItem is added', () => {
        const compositeItemCollection: ICompositeItem[] = [{ id: 123 }];
        expectedResult = service.addCompositeItemToCollectionIfMissing(compositeItemCollection, undefined, null);
        expect(expectedResult).toEqual(compositeItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
