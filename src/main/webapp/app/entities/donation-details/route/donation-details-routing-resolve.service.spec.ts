import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDonationDetails, DonationDetails } from '../donation-details.model';
import { DonationDetailsService } from '../service/donation-details.service';

import { DonationDetailsRoutingResolveService } from './donation-details-routing-resolve.service';

describe('DonationDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DonationDetailsRoutingResolveService;
  let service: DonationDetailsService;
  let resultDonationDetails: IDonationDetails | undefined;

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
    routingResolveService = TestBed.inject(DonationDetailsRoutingResolveService);
    service = TestBed.inject(DonationDetailsService);
    resultDonationDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IDonationDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationDetails).toEqual({ id: 123 });
    });

    it('should return new IDonationDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDonationDetails).toEqual(new DonationDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DonationDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
