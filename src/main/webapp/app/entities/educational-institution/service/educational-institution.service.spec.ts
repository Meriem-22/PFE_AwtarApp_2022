import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEducationalInstitution, EducationalInstitution } from '../educational-institution.model';

import { EducationalInstitutionService } from './educational-institution.service';

describe('EducationalInstitution Service', () => {
  let service: EducationalInstitutionService;
  let httpMock: HttpTestingController;
  let elemDefault: IEducationalInstitution;
  let expectedResult: IEducationalInstitution | IEducationalInstitution[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EducationalInstitutionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      adress: 'AAAAAAA',
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

    it('should create a EducationalInstitution', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EducationalInstitution()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EducationalInstitution', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          adress: 'BBBBBB',
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

    it('should partial update a EducationalInstitution', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
        },
        new EducationalInstitution()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EducationalInstitution', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          adress: 'BBBBBB',
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

    it('should delete a EducationalInstitution', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEducationalInstitutionToCollectionIfMissing', () => {
      it('should add a EducationalInstitution to an empty array', () => {
        const educationalInstitution: IEducationalInstitution = { id: 123 };
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing([], educationalInstitution);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(educationalInstitution);
      });

      it('should not add a EducationalInstitution to an array that contains it', () => {
        const educationalInstitution: IEducationalInstitution = { id: 123 };
        const educationalInstitutionCollection: IEducationalInstitution[] = [
          {
            ...educationalInstitution,
          },
          { id: 456 },
        ];
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing(educationalInstitutionCollection, educationalInstitution);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EducationalInstitution to an array that doesn't contain it", () => {
        const educationalInstitution: IEducationalInstitution = { id: 123 };
        const educationalInstitutionCollection: IEducationalInstitution[] = [{ id: 456 }];
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing(educationalInstitutionCollection, educationalInstitution);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(educationalInstitution);
      });

      it('should add only unique EducationalInstitution to an array', () => {
        const educationalInstitutionArray: IEducationalInstitution[] = [{ id: 123 }, { id: 456 }, { id: 44274 }];
        const educationalInstitutionCollection: IEducationalInstitution[] = [{ id: 123 }];
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing(
          educationalInstitutionCollection,
          ...educationalInstitutionArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const educationalInstitution: IEducationalInstitution = { id: 123 };
        const educationalInstitution2: IEducationalInstitution = { id: 456 };
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing([], educationalInstitution, educationalInstitution2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(educationalInstitution);
        expect(expectedResult).toContain(educationalInstitution2);
      });

      it('should accept null and undefined values', () => {
        const educationalInstitution: IEducationalInstitution = { id: 123 };
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing([], null, educationalInstitution, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(educationalInstitution);
      });

      it('should return initial array if no EducationalInstitution is added', () => {
        const educationalInstitutionCollection: IEducationalInstitution[] = [{ id: 123 }];
        expectedResult = service.addEducationalInstitutionToCollectionIfMissing(educationalInstitutionCollection, undefined, null);
        expect(expectedResult).toEqual(educationalInstitutionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
