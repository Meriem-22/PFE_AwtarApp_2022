import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDonationsReceived, DonationsReceived } from '../donations-received.model';

import { DonationsReceivedService } from './donations-received.service';

describe('DonationsReceived Service', () => {
  let service: DonationsReceivedService;
  let httpMock: HttpTestingController;
  let elemDefault: IDonationsReceived;
  let expectedResult: IDonationsReceived | IDonationsReceived[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonationsReceivedService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      urlPhotoContentType: 'image/png',
      urlPhoto: 'AAAAAAA',
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

    it('should create a DonationsReceived', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new DonationsReceived()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonationsReceived', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          urlPhoto: 'BBBBBB',
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

    it('should partial update a DonationsReceived', () => {
      const patchObject = Object.assign({}, new DonationsReceived());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DonationsReceived', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          urlPhoto: 'BBBBBB',
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

    it('should delete a DonationsReceived', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonationsReceivedToCollectionIfMissing', () => {
      it('should add a DonationsReceived to an empty array', () => {
        const donationsReceived: IDonationsReceived = { id: 123 };
        expectedResult = service.addDonationsReceivedToCollectionIfMissing([], donationsReceived);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsReceived);
      });

      it('should not add a DonationsReceived to an array that contains it', () => {
        const donationsReceived: IDonationsReceived = { id: 123 };
        const donationsReceivedCollection: IDonationsReceived[] = [
          {
            ...donationsReceived,
          },
          { id: 456 },
        ];
        expectedResult = service.addDonationsReceivedToCollectionIfMissing(donationsReceivedCollection, donationsReceived);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonationsReceived to an array that doesn't contain it", () => {
        const donationsReceived: IDonationsReceived = { id: 123 };
        const donationsReceivedCollection: IDonationsReceived[] = [{ id: 456 }];
        expectedResult = service.addDonationsReceivedToCollectionIfMissing(donationsReceivedCollection, donationsReceived);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsReceived);
      });

      it('should add only unique DonationsReceived to an array', () => {
        const donationsReceivedArray: IDonationsReceived[] = [{ id: 123 }, { id: 456 }, { id: 14726 }];
        const donationsReceivedCollection: IDonationsReceived[] = [{ id: 123 }];
        expectedResult = service.addDonationsReceivedToCollectionIfMissing(donationsReceivedCollection, ...donationsReceivedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donationsReceived: IDonationsReceived = { id: 123 };
        const donationsReceived2: IDonationsReceived = { id: 456 };
        expectedResult = service.addDonationsReceivedToCollectionIfMissing([], donationsReceived, donationsReceived2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsReceived);
        expect(expectedResult).toContain(donationsReceived2);
      });

      it('should accept null and undefined values', () => {
        const donationsReceived: IDonationsReceived = { id: 123 };
        expectedResult = service.addDonationsReceivedToCollectionIfMissing([], null, donationsReceived, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsReceived);
      });

      it('should return initial array if no DonationsReceived is added', () => {
        const donationsReceivedCollection: IDonationsReceived[] = [{ id: 123 }];
        expectedResult = service.addDonationsReceivedToCollectionIfMissing(donationsReceivedCollection, undefined, null);
        expect(expectedResult).toEqual(donationsReceivedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
