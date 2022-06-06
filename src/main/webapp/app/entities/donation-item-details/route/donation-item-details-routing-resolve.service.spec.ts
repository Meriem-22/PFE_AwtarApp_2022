import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDonationItemDetails, DonationItemDetails } from '../donation-item-details.model';
import { DonationItemDetailsService } from '../service/donation-item-details.service';

import { DonationItemDetailsRoutingResolveService } from './donation-item-details-routing-resolve.service';

describe('DonationItemDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DonationItemDetailsRoutingResolveService;
  let service: DonationItemDetailsService;
  let resultDonationItemDetails: IDonationItemDetails | undefined;

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
    routingResolveService = TestBed.inject(DonationItemDetailsRoutingResolveService);
    service = TestBed.inject(DonationItemDetailsService);
    resultDonationItemDetails = undefined;
  });

  describe('resolve', () => {
    it('should return IDonationItemDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationItemDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationItemDetails).toEqual({ id: 123 });
    });

    it('should return new IDonationItemDetails if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationItemDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDonationItemDetails).toEqual(new DonationItemDetails());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DonationItemDetails })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationItemDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationItemDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
