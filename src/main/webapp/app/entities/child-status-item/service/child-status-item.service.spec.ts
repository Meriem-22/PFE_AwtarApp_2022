import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChildStatusItem, ChildStatusItem } from '../child-status-item.model';

import { ChildStatusItemService } from './child-status-item.service';

describe('ChildStatusItem Service', () => {
  let service: ChildStatusItemService;
  let httpMock: HttpTestingController;
  let elemDefault: IChildStatusItem;
  let expectedResult: IChildStatusItem | IChildStatusItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChildStatusItemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      affected: false,
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

    it('should create a ChildStatusItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ChildStatusItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChildStatusItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          affected: true,
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

    it('should partial update a ChildStatusItem', () => {
      const patchObject = Object.assign(
        {
          affected: true,
          archivated: true,
        },
        new ChildStatusItem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ChildStatusItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          affected: true,
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

    it('should delete a ChildStatusItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addChildStatusItemToCollectionIfMissing', () => {
      it('should add a ChildStatusItem to an empty array', () => {
        const childStatusItem: IChildStatusItem = { id: 123 };
        expectedResult = service.addChildStatusItemToCollectionIfMissing([], childStatusItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(childStatusItem);
      });

      it('should not add a ChildStatusItem to an array that contains it', () => {
        const childStatusItem: IChildStatusItem = { id: 123 };
        const childStatusItemCollection: IChildStatusItem[] = [
          {
            ...childStatusItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addChildStatusItemToCollectionIfMissing(childStatusItemCollection, childStatusItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChildStatusItem to an array that doesn't contain it", () => {
        const childStatusItem: IChildStatusItem = { id: 123 };
        const childStatusItemCollection: IChildStatusItem[] = [{ id: 456 }];
        expectedResult = service.addChildStatusItemToCollectionIfMissing(childStatusItemCollection, childStatusItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(childStatusItem);
      });

      it('should add only unique ChildStatusItem to an array', () => {
        const childStatusItemArray: IChildStatusItem[] = [{ id: 123 }, { id: 456 }, { id: 82040 }];
        const childStatusItemCollection: IChildStatusItem[] = [{ id: 123 }];
        expectedResult = service.addChildStatusItemToCollectionIfMissing(childStatusItemCollection, ...childStatusItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const childStatusItem: IChildStatusItem = { id: 123 };
        const childStatusItem2: IChildStatusItem = { id: 456 };
        expectedResult = service.addChildStatusItemToCollectionIfMissing([], childStatusItem, childStatusItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(childStatusItem);
        expect(expectedResult).toContain(childStatusItem2);
      });

      it('should accept null and undefined values', () => {
        const childStatusItem: IChildStatusItem = { id: 123 };
        expectedResult = service.addChildStatusItemToCollectionIfMissing([], null, childStatusItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(childStatusItem);
      });

      it('should return initial array if no ChildStatusItem is added', () => {
        const childStatusItemCollection: IChildStatusItem[] = [{ id: 123 }];
        expectedResult = service.addChildStatusItemToCollectionIfMissing(childStatusItemCollection, undefined, null);
        expect(expectedResult).toEqual(childStatusItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
