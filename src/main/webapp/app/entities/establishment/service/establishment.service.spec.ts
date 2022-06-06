import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEstablishment, Establishment } from '../establishment.model';

import { EstablishmentService } from './establishment.service';

describe('Establishment Service', () => {
  let service: EstablishmentService;
  let httpMock: HttpTestingController;
  let elemDefault: IEstablishment;
  let expectedResult: IEstablishment | IEstablishment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstablishmentService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      activity: 'AAAAAAA',
      manager: 'AAAAAAA',
      managerCin: 'AAAAAAA',
      contact: 'AAAAAAA',
      adress: 'AAAAAAA',
      cp: 'AAAAAAA',
      region: 'AAAAAAA',
      phone: 'AAAAAAA',
      fax: 'AAAAAAA',
      email: 'AAAAAAA',
      site: 'AAAAAAA',
      remark: 'AAAAAAA',
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

    it('should create a Establishment', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Establishment()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Establishment', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
          contact: 'BBBBBB',
          adress: 'BBBBBB',
          cp: 'BBBBBB',
          region: 'BBBBBB',
          phone: 'BBBBBB',
          fax: 'BBBBBB',
          email: 'BBBBBB',
          site: 'BBBBBB',
          remark: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Establishment', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
          contact: 'BBBBBB',
          adress: 'BBBBBB',
          phone: 'BBBBBB',
          site: 'BBBBBB',
          remark: 'BBBBBB',
        },
        new Establishment()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Establishment', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
          contact: 'BBBBBB',
          adress: 'BBBBBB',
          cp: 'BBBBBB',
          region: 'BBBBBB',
          phone: 'BBBBBB',
          fax: 'BBBBBB',
          email: 'BBBBBB',
          site: 'BBBBBB',
          remark: 'BBBBBB',
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

    it('should delete a Establishment', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEstablishmentToCollectionIfMissing', () => {
      it('should add a Establishment to an empty array', () => {
        const establishment: IEstablishment = { id: 123 };
        expectedResult = service.addEstablishmentToCollectionIfMissing([], establishment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establishment);
      });

      it('should not add a Establishment to an array that contains it', () => {
        const establishment: IEstablishment = { id: 123 };
        const establishmentCollection: IEstablishment[] = [
          {
            ...establishment,
          },
          { id: 456 },
        ];
        expectedResult = service.addEstablishmentToCollectionIfMissing(establishmentCollection, establishment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Establishment to an array that doesn't contain it", () => {
        const establishment: IEstablishment = { id: 123 };
        const establishmentCollection: IEstablishment[] = [{ id: 456 }];
        expectedResult = service.addEstablishmentToCollectionIfMissing(establishmentCollection, establishment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establishment);
      });

      it('should add only unique Establishment to an array', () => {
        const establishmentArray: IEstablishment[] = [{ id: 123 }, { id: 456 }, { id: 74512 }];
        const establishmentCollection: IEstablishment[] = [{ id: 123 }];
        expectedResult = service.addEstablishmentToCollectionIfMissing(establishmentCollection, ...establishmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const establishment: IEstablishment = { id: 123 };
        const establishment2: IEstablishment = { id: 456 };
        expectedResult = service.addEstablishmentToCollectionIfMissing([], establishment, establishment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(establishment);
        expect(expectedResult).toContain(establishment2);
      });

      it('should accept null and undefined values', () => {
        const establishment: IEstablishment = { id: 123 };
        expectedResult = service.addEstablishmentToCollectionIfMissing([], null, establishment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(establishment);
      });

      it('should return initial array if no Establishment is added', () => {
        const establishmentCollection: IEstablishment[] = [{ id: 123 }];
        expectedResult = service.addEstablishmentToCollectionIfMissing(establishmentCollection, undefined, null);
        expect(expectedResult).toEqual(establishmentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
