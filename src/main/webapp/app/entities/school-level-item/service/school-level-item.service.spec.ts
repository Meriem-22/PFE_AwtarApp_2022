import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISchoolLevelItem, SchoolLevelItem } from '../school-level-item.model';

import { SchoolLevelItemService } from './school-level-item.service';

describe('SchoolLevelItem Service', () => {
  let service: SchoolLevelItemService;
  let httpMock: HttpTestingController;
  let elemDefault: ISchoolLevelItem;
  let expectedResult: ISchoolLevelItem | ISchoolLevelItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SchoolLevelItemService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      isSchoolItem: false,
      quantityNeeded: 0,
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

    it('should create a SchoolLevelItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SchoolLevelItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SchoolLevelItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isSchoolItem: true,
          quantityNeeded: 1,
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

    it('should partial update a SchoolLevelItem', () => {
      const patchObject = Object.assign(
        {
          isSchoolItem: true,
        },
        new SchoolLevelItem()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SchoolLevelItem', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          isSchoolItem: true,
          quantityNeeded: 1,
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

    it('should delete a SchoolLevelItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSchoolLevelItemToCollectionIfMissing', () => {
      it('should add a SchoolLevelItem to an empty array', () => {
        const schoolLevelItem: ISchoolLevelItem = { id: 123 };
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing([], schoolLevelItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schoolLevelItem);
      });

      it('should not add a SchoolLevelItem to an array that contains it', () => {
        const schoolLevelItem: ISchoolLevelItem = { id: 123 };
        const schoolLevelItemCollection: ISchoolLevelItem[] = [
          {
            ...schoolLevelItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing(schoolLevelItemCollection, schoolLevelItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SchoolLevelItem to an array that doesn't contain it", () => {
        const schoolLevelItem: ISchoolLevelItem = { id: 123 };
        const schoolLevelItemCollection: ISchoolLevelItem[] = [{ id: 456 }];
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing(schoolLevelItemCollection, schoolLevelItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schoolLevelItem);
      });

      it('should add only unique SchoolLevelItem to an array', () => {
        const schoolLevelItemArray: ISchoolLevelItem[] = [{ id: 123 }, { id: 456 }, { id: 52590 }];
        const schoolLevelItemCollection: ISchoolLevelItem[] = [{ id: 123 }];
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing(schoolLevelItemCollection, ...schoolLevelItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const schoolLevelItem: ISchoolLevelItem = { id: 123 };
        const schoolLevelItem2: ISchoolLevelItem = { id: 456 };
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing([], schoolLevelItem, schoolLevelItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schoolLevelItem);
        expect(expectedResult).toContain(schoolLevelItem2);
      });

      it('should accept null and undefined values', () => {
        const schoolLevelItem: ISchoolLevelItem = { id: 123 };
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing([], null, schoolLevelItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schoolLevelItem);
      });

      it('should return initial array if no SchoolLevelItem is added', () => {
        const schoolLevelItemCollection: ISchoolLevelItem[] = [{ id: 123 }];
        expectedResult = service.addSchoolLevelItemToCollectionIfMissing(schoolLevelItemCollection, undefined, null);
        expect(expectedResult).toEqual(schoolLevelItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
