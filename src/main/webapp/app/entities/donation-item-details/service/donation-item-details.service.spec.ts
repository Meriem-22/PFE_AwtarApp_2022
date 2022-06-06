import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDonationItemDetails, DonationItemDetails } from '../donation-item-details.model';

import { DonationItemDetailsService } from './donation-item-details.service';

describe('DonationItemDetails Service', () => {
  let service: DonationItemDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDonationItemDetails;
  let expectedResult: IDonationItemDetails | IDonationItemDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonationItemDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      quantity: 0,
      date: currentDate,
      archivated: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a DonationItemDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          date: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.create(new DonationItemDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonationItemDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quantity: 1,
          date: currentDate.format(DATE_FORMAT),
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DonationItemDetails', () => {
      const patchObject = Object.assign(
        {
          quantity: 1,
          archivated: true,
        },
        new DonationItemDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DonationItemDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          quantity: 1,
          date: currentDate.format(DATE_FORMAT),
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          date: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a DonationItemDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonationItemDetailsToCollectionIfMissing', () => {
      it('should add a DonationItemDetails to an empty array', () => {
        const donationItemDetails: IDonationItemDetails = { id: 123 };
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing([], donationItemDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationItemDetails);
      });

      it('should not add a DonationItemDetails to an array that contains it', () => {
        const donationItemDetails: IDonationItemDetails = { id: 123 };
        const donationItemDetailsCollection: IDonationItemDetails[] = [
          {
            ...donationItemDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing(donationItemDetailsCollection, donationItemDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonationItemDetails to an array that doesn't contain it", () => {
        const donationItemDetails: IDonationItemDetails = { id: 123 };
        const donationItemDetailsCollection: IDonationItemDetails[] = [{ id: 456 }];
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing(donationItemDetailsCollection, donationItemDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationItemDetails);
      });

      it('should add only unique DonationItemDetails to an array', () => {
        const donationItemDetailsArray: IDonationItemDetails[] = [{ id: 123 }, { id: 456 }, { id: 99913 }];
        const donationItemDetailsCollection: IDonationItemDetails[] = [{ id: 123 }];
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing(donationItemDetailsCollection, ...donationItemDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donationItemDetails: IDonationItemDetails = { id: 123 };
        const donationItemDetails2: IDonationItemDetails = { id: 456 };
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing([], donationItemDetails, donationItemDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationItemDetails);
        expect(expectedResult).toContain(donationItemDetails2);
      });

      it('should accept null and undefined values', () => {
        const donationItemDetails: IDonationItemDetails = { id: 123 };
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing([], null, donationItemDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationItemDetails);
      });

      it('should return initial array if no DonationItemDetails is added', () => {
        const donationItemDetailsCollection: IDonationItemDetails[] = [{ id: 123 }];
        expectedResult = service.addDonationItemDetailsToCollectionIfMissing(donationItemDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(donationItemDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
