import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDonationsReceivedItem, DonationsReceivedItem } from '../donations-received-item.model';

import { DonationsReceivedItemService } from './donations-received-item.service';

describe('DonationsReceivedItem Service', () => {
  let service: DonationsReceivedItemService;
  let httpMock: HttpTestingController;
  let elemDefault: IDonationsReceivedItem;
  let expectedResult: IDonationsReceivedItem | IDonationsReceivedItem[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DonationsReceivedItemService);
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

    it('should create a DonationsReceivedItem', () => {
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

      service.create(new DonationsReceivedItem()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DonationsReceivedItem', () => {
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

    it('should partial update a DonationsReceivedItem', () => {
      const patchObject = Object.assign(
        {
          quantity: 1,
          date: currentDate.format(DATE_FORMAT),
        },
        new DonationsReceivedItem()
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

    it('should return a list of DonationsReceivedItem', () => {
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

    it('should delete a DonationsReceivedItem', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDonationsReceivedItemToCollectionIfMissing', () => {
      it('should add a DonationsReceivedItem to an empty array', () => {
        const donationsReceivedItem: IDonationsReceivedItem = { id: 123 };
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing([], donationsReceivedItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsReceivedItem);
      });

      it('should not add a DonationsReceivedItem to an array that contains it', () => {
        const donationsReceivedItem: IDonationsReceivedItem = { id: 123 };
        const donationsReceivedItemCollection: IDonationsReceivedItem[] = [
          {
            ...donationsReceivedItem,
          },
          { id: 456 },
        ];
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing(donationsReceivedItemCollection, donationsReceivedItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DonationsReceivedItem to an array that doesn't contain it", () => {
        const donationsReceivedItem: IDonationsReceivedItem = { id: 123 };
        const donationsReceivedItemCollection: IDonationsReceivedItem[] = [{ id: 456 }];
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing(donationsReceivedItemCollection, donationsReceivedItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsReceivedItem);
      });

      it('should add only unique DonationsReceivedItem to an array', () => {
        const donationsReceivedItemArray: IDonationsReceivedItem[] = [{ id: 123 }, { id: 456 }, { id: 8947 }];
        const donationsReceivedItemCollection: IDonationsReceivedItem[] = [{ id: 123 }];
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing(
          donationsReceivedItemCollection,
          ...donationsReceivedItemArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const donationsReceivedItem: IDonationsReceivedItem = { id: 123 };
        const donationsReceivedItem2: IDonationsReceivedItem = { id: 456 };
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing([], donationsReceivedItem, donationsReceivedItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(donationsReceivedItem);
        expect(expectedResult).toContain(donationsReceivedItem2);
      });

      it('should accept null and undefined values', () => {
        const donationsReceivedItem: IDonationsReceivedItem = { id: 123 };
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing([], null, donationsReceivedItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(donationsReceivedItem);
      });

      it('should return initial array if no DonationsReceivedItem is added', () => {
        const donationsReceivedItemCollection: IDonationsReceivedItem[] = [{ id: 123 }];
        expectedResult = service.addDonationsReceivedItemToCollectionIfMissing(donationsReceivedItemCollection, undefined, null);
        expect(expectedResult).toEqual(donationsReceivedItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
