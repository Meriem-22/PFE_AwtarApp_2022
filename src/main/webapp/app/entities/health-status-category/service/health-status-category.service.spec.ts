import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHealthStatusCategory, HealthStatusCategory } from '../health-status-category.model';

import { HealthStatusCategoryService } from './health-status-category.service';

describe('HealthStatusCategory Service', () => {
  let service: HealthStatusCategoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IHealthStatusCategory;
  let expectedResult: IHealthStatusCategory | IHealthStatusCategory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HealthStatusCategoryService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
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

    it('should create a HealthStatusCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new HealthStatusCategory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HealthStatusCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should partial update a HealthStatusCategory', () => {
      const patchObject = Object.assign(
        {
          archivated: true,
        },
        new HealthStatusCategory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HealthStatusCategory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
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

    it('should delete a HealthStatusCategory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHealthStatusCategoryToCollectionIfMissing', () => {
      it('should add a HealthStatusCategory to an empty array', () => {
        const healthStatusCategory: IHealthStatusCategory = { id: 123 };
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing([], healthStatusCategory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(healthStatusCategory);
      });

      it('should not add a HealthStatusCategory to an array that contains it', () => {
        const healthStatusCategory: IHealthStatusCategory = { id: 123 };
        const healthStatusCategoryCollection: IHealthStatusCategory[] = [
          {
            ...healthStatusCategory,
          },
          { id: 456 },
        ];
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing(healthStatusCategoryCollection, healthStatusCategory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HealthStatusCategory to an array that doesn't contain it", () => {
        const healthStatusCategory: IHealthStatusCategory = { id: 123 };
        const healthStatusCategoryCollection: IHealthStatusCategory[] = [{ id: 456 }];
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing(healthStatusCategoryCollection, healthStatusCategory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(healthStatusCategory);
      });

      it('should add only unique HealthStatusCategory to an array', () => {
        const healthStatusCategoryArray: IHealthStatusCategory[] = [{ id: 123 }, { id: 456 }, { id: 96234 }];
        const healthStatusCategoryCollection: IHealthStatusCategory[] = [{ id: 123 }];
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing(healthStatusCategoryCollection, ...healthStatusCategoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const healthStatusCategory: IHealthStatusCategory = { id: 123 };
        const healthStatusCategory2: IHealthStatusCategory = { id: 456 };
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing([], healthStatusCategory, healthStatusCategory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(healthStatusCategory);
        expect(expectedResult).toContain(healthStatusCategory2);
      });

      it('should accept null and undefined values', () => {
        const healthStatusCategory: IHealthStatusCategory = { id: 123 };
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing([], null, healthStatusCategory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(healthStatusCategory);
      });

      it('should return initial array if no HealthStatusCategory is added', () => {
        const healthStatusCategoryCollection: IHealthStatusCategory[] = [{ id: 123 }];
        expectedResult = service.addHealthStatusCategoryToCollectionIfMissing(healthStatusCategoryCollection, undefined, null);
        expect(expectedResult).toEqual(healthStatusCategoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
