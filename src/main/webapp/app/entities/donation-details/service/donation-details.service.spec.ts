import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDonationDetails, DonationDetails } from '../donation-details.model';

import { DonationDetailsService } from './donation-details.service';

describe('DonationDetails Service', () => {
  let service: DonationDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IDonationDetails;
  let expectedResult: IDonationDetails | IDonationDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonationDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      description: 'AAAAAAA',
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

    it('should create a DonationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DonationDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
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

    it('should partial update a DonationDetails', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
        },
        new DonationDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DonationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          description: 'BBBBBB',
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

    it('should delete a DonationDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonationDetailsToCollectionIfMissing', () => {
      it('should add a DonationDetails to an empty array', () => {
        const donationDetails: IDonationDetails = { id: 123 };
        expectedResult = service.addDonationDetailsToCollectionIfMissing([], donationDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationDetails);
      });

      it('should not add a DonationDetails to an array that contains it', () => {
        const donationDetails: IDonationDetails = { id: 123 };
        const donationDetailsCollection: IDonationDetails[] = [
          {
            ...donationDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addDonationDetailsToCollectionIfMissing(donationDetailsCollection, donationDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonationDetails to an array that doesn't contain it", () => {
        const donationDetails: IDonationDetails = { id: 123 };
        const donationDetailsCollection: IDonationDetails[] = [{ id: 456 }];
        expectedResult = service.addDonationDetailsToCollectionIfMissing(donationDetailsCollection, donationDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationDetails);
      });

      it('should add only unique DonationDetails to an array', () => {
        const donationDetailsArray: IDonationDetails[] = [{ id: 123 }, { id: 456 }, { id: 45594 }];
        const donationDetailsCollection: IDonationDetails[] = [{ id: 123 }];
        expectedResult = service.addDonationDetailsToCollectionIfMissing(donationDetailsCollection, ...donationDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donationDetails: IDonationDetails = { id: 123 };
        const donationDetails2: IDonationDetails = { id: 456 };
        expectedResult = service.addDonationDetailsToCollectionIfMissing([], donationDetails, donationDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationDetails);
        expect(expectedResult).toContain(donationDetails2);
      });

      it('should accept null and undefined values', () => {
        const donationDetails: IDonationDetails = { id: 123 };
        expectedResult = service.addDonationDetailsToCollectionIfMissing([], null, donationDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationDetails);
      });

      it('should return initial array if no DonationDetails is added', () => {
        const donationDetailsCollection: IDonationDetails[] = [{ id: 123 }];
        expectedResult = service.addDonationDetailsToCollectionIfMissing(donationDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(donationDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
