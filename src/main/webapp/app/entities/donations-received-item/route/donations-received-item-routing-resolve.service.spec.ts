import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDonationsReceivedItem, DonationsReceivedItem } from '../donations-received-item.model';
import { DonationsReceivedItemService } from '../service/donations-received-item.service';

import { DonationsReceivedItemRoutingResolveService } from './donations-received-item-routing-resolve.service';

describe('DonationsReceivedItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DonationsReceivedItemRoutingResolveService;
  let service: DonationsReceivedItemService;
  let resultDonationsReceivedItem: IDonationsReceivedItem | undefined;

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
    routingResolveService = TestBed.inject(DonationsReceivedItemRoutingResolveService);
    service = TestBed.inject(DonationsReceivedItemService);
    resultDonationsReceivedItem = undefined;
  });

  describe('resolve', () => {
    it('should return IDonationsReceivedItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceivedItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsReceivedItem).toEqual({ id: 123 });
    });

    it('should return new IDonationsReceivedItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceivedItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDonationsReceivedItem).toEqual(new DonationsReceivedItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DonationsReceivedItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDonationsReceivedItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDonationsReceivedItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
