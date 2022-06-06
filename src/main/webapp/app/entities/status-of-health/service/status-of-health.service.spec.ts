import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IStatusOfHealth, StatusOfHealth } from '../status-of-health.model';

import { StatusOfHealthService } from './status-of-health.service';

describe('StatusOfHealth Service', () => {
  let service: StatusOfHealthService;
  let httpMock: HttpTestingController;
  let elemDefault: IStatusOfHealth;
  let expectedResult: IStatusOfHealth | IStatusOfHealth[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StatusOfHealthService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      healthStatusDate: currentDate,
      urlDetailsAttachedContentType: 'image/png',
      urlDetailsAttached: 'AAAAAAA',
      archivated: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          healthStatusDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a StatusOfHealth', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          healthStatusDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          healthStatusDate: currentDate,
        },
        returnedFromService
      );

      service.create(new StatusOfHealth()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StatusOfHealth', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          healthStatusDate: currentDate.format(DATE_FORMAT),
          urlDetailsAttached: 'BBBBBB',
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          healthStatusDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StatusOfHealth', () => {
      const patchObject = Object.assign(
        {
          healthStatusDate: currentDate.format(DATE_FORMAT),
          archivated: true,
        },
        new StatusOfHealth()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          healthStatusDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StatusOfHealth', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          healthStatusDate: currentDate.format(DATE_FORMAT),
          urlDetailsAttached: 'BBBBBB',
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          healthStatusDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a StatusOfHealth', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addStatusOfHealthToCollectionIfMissing', () => {
      it('should add a StatusOfHealth to an empty array', () => {
        const statusOfHealth: IStatusOfHealth = { id: 123 };
        expectedResult = service.addStatusOfHealthToCollectionIfMissing([], statusOfHealth);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statusOfHealth);
      });

      it('should not add a StatusOfHealth to an array that contains it', () => {
        const statusOfHealth: IStatusOfHealth = { id: 123 };
        const statusOfHealthCollection: IStatusOfHealth[] = [
          {
            ...statusOfHealth,
          },
          { id: 456 },
        ];
        expectedResult = service.addStatusOfHealthToCollectionIfMissing(statusOfHealthCollection, statusOfHealth);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StatusOfHealth to an array that doesn't contain it", () => {
        const statusOfHealth: IStatusOfHealth = { id: 123 };
        const statusOfHealthCollection: IStatusOfHealth[] = [{ id: 456 }];
        expectedResult = service.addStatusOfHealthToCollectionIfMissing(statusOfHealthCollection, statusOfHealth);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statusOfHealth);
      });

      it('should add only unique StatusOfHealth to an array', () => {
        const statusOfHealthArray: IStatusOfHealth[] = [{ id: 123 }, { id: 456 }, { id: 66566 }];
        const statusOfHealthCollection: IStatusOfHealth[] = [{ id: 123 }];
        expectedResult = service.addStatusOfHealthToCollectionIfMissing(statusOfHealthCollection, ...statusOfHealthArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const statusOfHealth: IStatusOfHealth = { id: 123 };
        const statusOfHealth2: IStatusOfHealth = { id: 456 };
        expectedResult = service.addStatusOfHealthToCollectionIfMissing([], statusOfHealth, statusOfHealth2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statusOfHealth);
        expect(expectedResult).toContain(statusOfHealth2);
      });

      it('should accept null and undefined values', () => {
        const statusOfHealth: IStatusOfHealth = { id: 123 };
        expectedResult = service.addStatusOfHealthToCollectionIfMissing([], null, statusOfHealth, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statusOfHealth);
      });

      it('should return initial array if no StatusOfHealth is added', () => {
        const statusOfHealthCollection: IStatusOfHealth[] = [{ id: 123 }];
        expectedResult = service.addStatusOfHealthToCollectionIfMissing(statusOfHealthCollection, undefined, null);
        expect(expectedResult).toEqual(statusOfHealthCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
