import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Period } from 'app/entities/enumerations/period.model';
import { IDonationsIssued, DonationsIssued } from '../donations-issued.model';

import { DonationsIssuedService } from './donations-issued.service';

describe('DonationsIssued Service', () => {
  let service: DonationsIssuedService;
  let httpMock: HttpTestingController;
  let elemDefault: IDonationsIssued;
  let expectedResult: IDonationsIssued | IDonationsIssued[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonationsIssuedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      model: 'AAAAAAA',
      isValidated: false,
      validationDate: currentDate,
      validationUser: 0,
      donationsDate: currentDate,
      canceledDonations: false,
      canceledOn: currentDate,
      canceledBy: 0,
      cancellationReason: 'AAAAAAA',
      recurringDonations: false,
      periodicity: Period.PER_WEEK,
      recurrence: 0,
      archivated: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          validationDate: currentDate.format(DATE_FORMAT),
          donationsDate: currentDate.format(DATE_FORMAT),
          canceledOn: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DonationsIssued', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          validationDate: currentDate.format(DATE_FORMAT),
          donationsDate: currentDate.format(DATE_FORMAT),
          canceledOn: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          validationDate: currentDate,
          donationsDate: currentDate,
          canceledOn: currentDate,
        },
        returnedFromService
      );

      service.create(new DonationsIssued()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonationsIssued', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          model: 'BBBBBB',
          isValidated: true,
          validationDate: currentDate.format(DATE_FORMAT),
          validationUser: 1,
          donationsDate: currentDate.format(DATE_FORMAT),
          canceledDonations: true,
          canceledOn: currentDate.format(DATE_FORMAT),
          canceledBy: 1,
          cancellationReason: 'BBBBBB',
          recurringDonations: true,
          periodicity: 'BBBBBB',
          recurrence: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          validationDate: currentDate,
          donationsDate: currentDate,
          canceledOn: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DonationsIssued', () => {
      const patchObject = Object.assign(
        {
          model: 'BBBBBB',
          validationUser: 1,
          donationsDate: currentDate.format(DATE_FORMAT),
          canceledOn: currentDate.format(DATE_FORMAT),
          canceledBy: 1,
          cancellationReason: 'BBBBBB',
        },
        new DonationsIssued()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          validationDate: currentDate,
          donationsDate: currentDate,
          canceledOn: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DonationsIssued', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          model: 'BBBBBB',
          isValidated: true,
          validationDate: currentDate.format(DATE_FORMAT),
          validationUser: 1,
          donationsDate: currentDate.format(DATE_FORMAT),
          canceledDonations: true,
          canceledOn: currentDate.format(DATE_FORMAT),
          canceledBy: 1,
          cancellationReason: 'BBBBBB',
          recurringDonations: true,
          periodicity: 'BBBBBB',
          recurrence: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          validationDate: currentDate,
          donationsDate: currentDate,
          canceledOn: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DonationsIssued', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonationsIssuedToCollectionIfMissing', () => {
      it('should add a DonationsIssued to an empty array', () => {
        const donationsIssued: IDonationsIssued = { id: 123 };
        expectedResult = service.addDonationsIssuedToCollectionIfMissing([], donationsIssued);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsIssued);
      });

      it('should not add a DonationsIssued to an array that contains it', () => {
        const donationsIssued: IDonationsIssued = { id: 123 };
        const donationsIssuedCollection: IDonationsIssued[] = [
          {
            ...donationsIssued,
          },
          { id: 456 },
        ];
        expectedResult = service.addDonationsIssuedToCollectionIfMissing(donationsIssuedCollection, donationsIssued);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonationsIssued to an array that doesn't contain it", () => {
        const donationsIssued: IDonationsIssued = { id: 123 };
        const donationsIssuedCollection: IDonationsIssued[] = [{ id: 456 }];
        expectedResult = service.addDonationsIssuedToCollectionIfMissing(donationsIssuedCollection, donationsIssued);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsIssued);
      });

      it('should add only unique DonationsIssued to an array', () => {
        const donationsIssuedArray: IDonationsIssued[] = [{ id: 123 }, { id: 456 }, { id: 72417 }];
        const donationsIssuedCollection: IDonationsIssued[] = [{ id: 123 }];
        expectedResult = service.addDonationsIssuedToCollectionIfMissing(donationsIssuedCollection, ...donationsIssuedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donationsIssued: IDonationsIssued = { id: 123 };
        const donationsIssued2: IDonationsIssued = { id: 456 };
        expectedResult = service.addDonationsIssuedToCollectionIfMissing([], donationsIssued, donationsIssued2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsIssued);
        expect(expectedResult).toContain(donationsIssued2);
      });

      it('should accept null and undefined values', () => {
        const donationsIssued: IDonationsIssued = { id: 123 };
        expectedResult = service.addDonationsIssuedToCollectionIfMissing([], null, donationsIssued, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsIssued);
      });

      it('should return initial array if no DonationsIssued is added', () => {
        const donationsIssuedCollection: IDonationsIssued[] = [{ id: 123 }];
        expectedResult = service.addDonationsIssuedToCollectionIfMissing(donationsIssuedCollection, undefined, null);
        expect(expectedResult).toEqual(donationsIssuedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
