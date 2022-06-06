import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISchoolLevel, SchoolLevel } from '../school-level.model';

import { SchoolLevelService } from './school-level.service';

describe('SchoolLevel Service', () => {
  let service: SchoolLevelService;
  let httpMock: HttpTestingController;
  let elemDefault: ISchoolLevel;
  let expectedResult: ISchoolLevel | ISchoolLevel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SchoolLevelService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      schoolLevel: 'AAAAAAA',
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

    it('should create a SchoolLevel', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SchoolLevel()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SchoolLevel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          schoolLevel: 'BBBBBB',
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

    it('should partial update a SchoolLevel', () => {
      const patchObject = Object.assign({}, new SchoolLevel());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SchoolLevel', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          schoolLevel: 'BBBBBB',
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

    it('should delete a SchoolLevel', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSchoolLevelToCollectionIfMissing', () => {
      it('should add a SchoolLevel to an empty array', () => {
        const schoolLevel: ISchoolLevel = { id: 123 };
        expectedResult = service.addSchoolLevelToCollectionIfMissing([], schoolLevel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schoolLevel);
      });

      it('should not add a SchoolLevel to an array that contains it', () => {
        const schoolLevel: ISchoolLevel = { id: 123 };
        const schoolLevelCollection: ISchoolLevel[] = [
          {
            ...schoolLevel,
          },
          { id: 456 },
        ];
        expectedResult = service.addSchoolLevelToCollectionIfMissing(schoolLevelCollection, schoolLevel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SchoolLevel to an array that doesn't contain it", () => {
        const schoolLevel: ISchoolLevel = { id: 123 };
        const schoolLevelCollection: ISchoolLevel[] = [{ id: 456 }];
        expectedResult = service.addSchoolLevelToCollectionIfMissing(schoolLevelCollection, schoolLevel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schoolLevel);
      });

      it('should add only unique SchoolLevel to an array', () => {
        const schoolLevelArray: ISchoolLevel[] = [{ id: 123 }, { id: 456 }, { id: 33598 }];
        const schoolLevelCollection: ISchoolLevel[] = [{ id: 123 }];
        expectedResult = service.addSchoolLevelToCollectionIfMissing(schoolLevelCollection, ...schoolLevelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const schoolLevel: ISchoolLevel = { id: 123 };
        const schoolLevel2: ISchoolLevel = { id: 456 };
        expectedResult = service.addSchoolLevelToCollectionIfMissing([], schoolLevel, schoolLevel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(schoolLevel);
        expect(expectedResult).toContain(schoolLevel2);
      });

      it('should accept null and undefined values', () => {
        const schoolLevel: ISchoolLevel = { id: 123 };
        expectedResult = service.addSchoolLevelToCollectionIfMissing([], null, schoolLevel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(schoolLevel);
      });

      it('should return initial array if no SchoolLevel is added', () => {
        const schoolLevelCollection: ISchoolLevel[] = [{ id: 123 }];
        expectedResult = service.addSchoolLevelToCollectionIfMissing(schoolLevelCollection, undefined, null);
        expect(expectedResult).toEqual(schoolLevelCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
