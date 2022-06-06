import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IItemValue, ItemValue } from '../item-value.model';

import { ItemValueService } from './item-value.service';

describe('ItemValue Service', () => {
  let service: ItemValueService;
  let httpMock: HttpTestingController;
  let elemDefault: IItemValue;
  let expectedResult: IItemValue | IItemValue[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ItemValueService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      price: 0,
      priceDate: currentDate,
      availableStockQuantity: 0,
      archivated: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          priceDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ItemValue', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          priceDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          priceDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ItemValue()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ItemValue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          price: 1,
          priceDate: currentDate.format(DATE_FORMAT),
          availableStockQuantity: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          priceDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ItemValue', () => {
      const patchObject = Object.assign(
        {
          availableStockQuantity: 1,
        },
        new ItemValue()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          priceDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ItemValue', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          price: 1,
          priceDate: currentDate.format(DATE_FORMAT),
          availableStockQuantity: 1,
          archivated: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          priceDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ItemValue', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addItemValueToCollectionIfMissing', () => {
      it('should add a ItemValue to an empty array', () => {
        const itemValue: IItemValue = { id: 123 };
        expectedResult = service.addItemValueToCollectionIfMissing([], itemValue);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemValue);
      });

      it('should not add a ItemValue to an array that contains it', () => {
        const itemValue: IItemValue = { id: 123 };
        const itemValueCollection: IItemValue[] = [
          {
            ...itemValue,
          },
          { id: 456 },
        ];
        expectedResult = service.addItemValueToCollectionIfMissing(itemValueCollection, itemValue);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ItemValue to an array that doesn't contain it", () => {
        const itemValue: IItemValue = { id: 123 };
        const itemValueCollection: IItemValue[] = [{ id: 456 }];
        expectedResult = service.addItemValueToCollectionIfMissing(itemValueCollection, itemValue);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemValue);
      });

      it('should add only unique ItemValue to an array', () => {
        const itemValueArray: IItemValue[] = [{ id: 123 }, { id: 456 }, { id: 59484 }];
        const itemValueCollection: IItemValue[] = [{ id: 123 }];
        expectedResult = service.addItemValueToCollectionIfMissing(itemValueCollection, ...itemValueArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const itemValue: IItemValue = { id: 123 };
        const itemValue2: IItemValue = { id: 456 };
        expectedResult = service.addItemValueToCollectionIfMissing([], itemValue, itemValue2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(itemValue);
        expect(expectedResult).toContain(itemValue2);
      });

      it('should accept null and undefined values', () => {
        const itemValue: IItemValue = { id: 123 };
        expectedResult = service.addItemValueToCollectionIfMissing([], null, itemValue, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(itemValue);
      });

      it('should return initial array if no ItemValue is added', () => {
        const itemValueCollection: IItemValue[] = [{ id: 123 }];
        expectedResult = service.addItemValueToCollectionIfMissing(itemValueCollection, undefined, null);
        expect(expectedResult).toEqual(itemValueCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
