import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Status } from 'app/entities/enumerations/status.model';
import { ITeachingCurriculum, TeachingCurriculum } from '../teaching-curriculum.model';

import { TeachingCurriculumService } from './teaching-curriculum.service';

describe('TeachingCurriculum Service', () => {
  let service: TeachingCurriculumService;
  let httpMock: HttpTestingController;
  let elemDefault: ITeachingCurriculum;
  let expectedResult: ITeachingCurriculum | ITeachingCurriculum[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TeachingCurriculumService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      beginningYear: 'AAAAAAA',
      endYear: 'AAAAAAA',
      annualResult: 0,
      result: Status.ADMITTED,
      remarks: 'AAAAAAA',
      attachedFileContentType: 'image/png',
      attachedFile: 'AAAAAAA',
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

    it('should create a TeachingCurriculum', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TeachingCurriculum()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TeachingCurriculum', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          beginningYear: 'BBBBBB',
          endYear: 'BBBBBB',
          annualResult: 1,
          result: 'BBBBBB',
          remarks: 'BBBBBB',
          attachedFile: 'BBBBBB',
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

    it('should partial update a TeachingCurriculum', () => {
      const patchObject = Object.assign(
        {
          endYear: 'BBBBBB',
          annualResult: 1,
          attachedFile: 'BBBBBB',
        },
        new TeachingCurriculum()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TeachingCurriculum', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          beginningYear: 'BBBBBB',
          endYear: 'BBBBBB',
          annualResult: 1,
          result: 'BBBBBB',
          remarks: 'BBBBBB',
          attachedFile: 'BBBBBB',
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

    it('should delete a TeachingCurriculum', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTeachingCurriculumToCollectionIfMissing', () => {
      it('should add a TeachingCurriculum to an empty array', () => {
        const teachingCurriculum: ITeachingCurriculum = { id: 123 };
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing([], teachingCurriculum);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teachingCurriculum);
      });

      it('should not add a TeachingCurriculum to an array that contains it', () => {
        const teachingCurriculum: ITeachingCurriculum = { id: 123 };
        const teachingCurriculumCollection: ITeachingCurriculum[] = [
          {
            ...teachingCurriculum,
          },
          { id: 456 },
        ];
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing(teachingCurriculumCollection, teachingCurriculum);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TeachingCurriculum to an array that doesn't contain it", () => {
        const teachingCurriculum: ITeachingCurriculum = { id: 123 };
        const teachingCurriculumCollection: ITeachingCurriculum[] = [{ id: 456 }];
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing(teachingCurriculumCollection, teachingCurriculum);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teachingCurriculum);
      });

      it('should add only unique TeachingCurriculum to an array', () => {
        const teachingCurriculumArray: ITeachingCurriculum[] = [{ id: 123 }, { id: 456 }, { id: 78251 }];
        const teachingCurriculumCollection: ITeachingCurriculum[] = [{ id: 123 }];
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing(teachingCurriculumCollection, ...teachingCurriculumArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const teachingCurriculum: ITeachingCurriculum = { id: 123 };
        const teachingCurriculum2: ITeachingCurriculum = { id: 456 };
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing([], teachingCurriculum, teachingCurriculum2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(teachingCurriculum);
        expect(expectedResult).toContain(teachingCurriculum2);
      });

      it('should accept null and undefined values', () => {
        const teachingCurriculum: ITeachingCurriculum = { id: 123 };
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing([], null, teachingCurriculum, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(teachingCurriculum);
      });

      it('should return initial array if no TeachingCurriculum is added', () => {
        const teachingCurriculumCollection: ITeachingCurriculum[] = [{ id: 123 }];
        expectedResult = service.addTeachingCurriculumToCollectionIfMissing(teachingCurriculumCollection, undefined, null);
        expect(expectedResult).toEqual(teachingCurriculumCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
