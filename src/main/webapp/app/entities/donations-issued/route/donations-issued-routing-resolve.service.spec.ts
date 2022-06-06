import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDonationsIssued, DonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';

import { DonationsIssuedRoutingResolveService } from './donations-issued-routing-resolve.service';

describe('DonationsIssued routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DonationsIssuedRoutingResolveService;
  let service: DonationsIssuedService;
  let resultDonationsIssued: IDonationsIssued | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DonationsIssuedRoutingResolveService);
    service = TestBed.inject(DonationsIssuedService);
    resultDonationsIssued = undefined;
  });

  describe('resolve', () => {
    it('should return IDonationsIssued returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsIssued = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsIssued).toEqual({ id: 123 });
    });

    it('should return new IDonationsIssued if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsIssued = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDonationsIssued).toEqual(new DonationsIssued());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DonationsIssued })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsIssued = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsIssued).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
