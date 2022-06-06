import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAuthorizingOfficer, AuthorizingOfficer } from '../authorizing-officer.model';

import { AuthorizingOfficerService } from './authorizing-officer.service';

describe('AuthorizingOfficer Service', () => {
  let service: AuthorizingOfficerService;
  let httpMock: HttpTestingController;
  let elemDefault: IAuthorizingOfficer;
  let expectedResult: IAuthorizingOfficer | IAuthorizingOfficer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AuthorizingOfficerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      abbreviation: 'AAAAAAA',
      activity: 'AAAAAAA',
      manager: 'AAAAAAA',
      managerCin: 'AAAAAAA',
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

    it('should create a AuthorizingOfficer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AuthorizingOfficer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AuthorizingOfficer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          abbreviation: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AuthorizingOfficer', () => {
      const patchObject = Object.assign(
        {
          abbreviation: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
        },
        new AuthorizingOfficer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AuthorizingOfficer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          abbreviation: 'BBBBBB',
          activity: 'BBBBBB',
          manager: 'BBBBBB',
          managerCin: 'BBBBBB',
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

    it('should delete a AuthorizingOfficer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAuthorizingOfficerToCollectionIfMissing', () => {
      it('should add a AuthorizingOfficer to an empty array', () => {
        const authorizingOfficer: IAuthorizingOfficer = { id: 123 };
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing([], authorizingOfficer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authorizingOfficer);
      });

      it('should not add a AuthorizingOfficer to an array that contains it', () => {
        const authorizingOfficer: IAuthorizingOfficer = { id: 123 };
        const authorizingOfficerCollection: IAuthorizingOfficer[] = [
          {
            ...authorizingOfficer,
          },
          { id: 456 },
        ];
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing(authorizingOfficerCollection, authorizingOfficer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AuthorizingOfficer to an array that doesn't contain it", () => {
        const authorizingOfficer: IAuthorizingOfficer = { id: 123 };
        const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 456 }];
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing(authorizingOfficerCollection, authorizingOfficer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authorizingOfficer);
      });

      it('should add only unique AuthorizingOfficer to an array', () => {
        const authorizingOfficerArray: IAuthorizingOfficer[] = [{ id: 123 }, { id: 456 }, { id: 57985 }];
        const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 123 }];
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing(authorizingOfficerCollection, ...authorizingOfficerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const authorizingOfficer: IAuthorizingOfficer = { id: 123 };
        const authorizingOfficer2: IAuthorizingOfficer = { id: 456 };
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing([], authorizingOfficer, authorizingOfficer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(authorizingOfficer);
        expect(expectedResult).toContain(authorizingOfficer2);
      });

      it('should accept null and undefined values', () => {
        const authorizingOfficer: IAuthorizingOfficer = { id: 123 };
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing([], null, authorizingOfficer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(authorizingOfficer);
      });

      it('should return initial array if no AuthorizingOfficer is added', () => {
        const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 123 }];
        expectedResult = service.addAuthorizingOfficerToCollectionIfMissing(authorizingOfficerCollection, undefined, null);
        expect(expectedResult).toEqual(authorizingOfficerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
