import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Status } from 'app/entities/enumerations/status.model';
import { IChildStatus, ChildStatus } from '../child-status.model';

import { ChildStatusService } from './child-status.service';

describe('ChildStatus Service', () => {
  let service: ChildStatusService;
  let httpMock: HttpTestingController;
  let elemDefault: IChildStatus;
  let expectedResult: IChildStatus | IChildStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChildStatusService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      staus: Status.ADMITTED,
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

    it('should create a ChildStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ChildStatus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChildStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          staus: 'BBBBBB',
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

    it('should partial update a ChildStatus', () => {
      const patchObject = Object.assign(
        {
          staus: 'BBBBBB',
          archivated: true,
        },
        new ChildStatus()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ChildStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          staus: 'BBBBBB',
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

    it('should delete a ChildStatus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addChildStatusToCollectionIfMissing', () => {
      it('should add a ChildStatus to an empty array', () => {
        const childStatus: IChildStatus = { id: 123 };
        expectedResult = service.addChildStatusToCollectionIfMissing([], childStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(childStatus);
      });

      it('should not add a ChildStatus to an array that contains it', () => {
        const childStatus: IChildStatus = { id: 123 };
        const childStatusCollection: IChildStatus[] = [
          {
            ...childStatus,
          },
          { id: 456 },
        ];
        expectedResult = service.addChildStatusToCollectionIfMissing(childStatusCollection, childStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChildStatus to an array that doesn't contain it", () => {
        const childStatus: IChildStatus = { id: 123 };
        const childStatusCollection: IChildStatus[] = [{ id: 456 }];
        expectedResult = service.addChildStatusToCollectionIfMissing(childStatusCollection, childStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(childStatus);
      });

      it('should add only unique ChildStatus to an array', () => {
        const childStatusArray: IChildStatus[] = [{ id: 123 }, { id: 456 }, { id: 12808 }];
        const childStatusCollection: IChildStatus[] = [{ id: 123 }];
        expectedResult = service.addChildStatusToCollectionIfMissing(childStatusCollection, ...childStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const childStatus: IChildStatus = { id: 123 };
        const childStatus2: IChildStatus = { id: 456 };
        expectedResult = service.addChildStatusToCollectionIfMissing([], childStatus, childStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(childStatus);
        expect(expectedResult).toContain(childStatus2);
      });

      it('should accept null and undefined values', () => {
        const childStatus: IChildStatus = { id: 123 };
        expectedResult = service.addChildStatusToCollectionIfMissing([], null, childStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(childStatus);
      });

      it('should return initial array if no ChildStatus is added', () => {
        const childStatusCollection: IChildStatus[] = [{ id: 123 }];
        expectedResult = service.addChildStatusToCollectionIfMissing(childStatusCollection, undefined, null);
        expect(expectedResult).toEqual(childStatusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
