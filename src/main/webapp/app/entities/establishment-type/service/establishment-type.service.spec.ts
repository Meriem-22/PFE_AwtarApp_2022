import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEstablishmentType, EstablishmentType } from '../establishment-type.model';

import { EstablishmentTypeService } from './establishment-type.service';

describe('EstablishmentType Service', () => {
  let service: EstablishmentTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IEstablishmentType;
  let expectedResult: IEstablishmentType | IEstablishmentType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstablishmentTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      typeName: 'AAAAAAA',
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

    it('should create a EstablishmentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EstablishmentType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EstablishmentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeName: 'BBBBBB',
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

    it('should partial update a EstablishmentType', () => {
      const patchObject = Object.assign(
        {
          typeName: 'BBBBBB',
        },
        new EstablishmentType()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EstablishmentType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeName: 'BBBBBB',
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

    it('should delete a EstablishmentType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEstablishmentTypeToCollectionIfMissing', () => {
      it('should add a EstablishmentType to an empty array', () => {
        const establishmentType: IEstablishmentType = { id: 123 };
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing([], establishmentType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establishmentType);
      });

      it('should not add a EstablishmentType to an array that contains it', () => {
        const establishmentType: IEstablishmentType = { id: 123 };
        const establishmentTypeCollection: IEstablishmentType[] = [
          {
            ...establishmentType,
          },
          { id: 456 },
        ];
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing(establishmentTypeCollection, establishmentType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EstablishmentType to an array that doesn't contain it", () => {
        const establishmentType: IEstablishmentType = { id: 123 };
        const establishmentTypeCollection: IEstablishmentType[] = [{ id: 456 }];
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing(establishmentTypeCollection, establishmentType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establishmentType);
      });

      it('should add only unique EstablishmentType to an array', () => {
        const establishmentTypeArray: IEstablishmentType[] = [{ id: 123 }, { id: 456 }, { id: 72500 }];
        const establishmentTypeCollection: IEstablishmentType[] = [{ id: 123 }];
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing(establishmentTypeCollection, ...establishmentTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const establishmentType: IEstablishmentType = { id: 123 };
        const establishmentType2: IEstablishmentType = { id: 456 };
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing([], establishmentType, establishmentType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establishmentType);
        expect(expectedResult).toContain(establishmentType2);
      });

      it('should accept null and undefined values', () => {
        const establishmentType: IEstablishmentType = { id: 123 };
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing([], null, establishmentType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establishmentType);
      });

      it('should return initial array if no EstablishmentType is added', () => {
        const establishmentTypeCollection: IEstablishmentType[] = [{ id: 123 }];
        expectedResult = service.addEstablishmentTypeToCollectionIfMissing(establishmentTypeCollection, undefined, null);
        expect(expectedResult).toEqual(establishmentTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
