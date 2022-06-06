import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDonationsReceived, DonationsReceived } from '../donations-received.model';
import { DonationsReceivedService } from '../service/donations-received.service';

import { DonationsReceivedRoutingResolveService } from './donations-received-routing-resolve.service';

describe('DonationsReceived routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DonationsReceivedRoutingResolveService;
  let service: DonationsReceivedService;
  let resultDonationsReceived: IDonationsReceived | undefined;

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
    routingResolveService = TestBed.inject(DonationsReceivedRoutingResolveService);
    service = TestBed.inject(DonationsReceivedService);
    resultDonationsReceived = undefined;
  });

  describe('resolve', () => {
    it('should return IDonationsReceived returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceived = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsReceived).toEqual({ id: 123 });
    });

    it('should return new IDonationsReceived if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceived = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDonationsReceived).toEqual(new DonationsReceived());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DonationsReceived })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceived = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsReceived).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
