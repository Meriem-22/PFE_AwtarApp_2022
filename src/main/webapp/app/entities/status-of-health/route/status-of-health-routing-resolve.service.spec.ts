import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IStatusOfHealth, StatusOfHealth } from '../status-of-health.model';
import { StatusOfHealthService } from '../service/status-of-health.service';

import { StatusOfHealthRoutingResolveService } from './status-of-health-routing-resolve.service';

describe('StatusOfHealth routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: StatusOfHealthRoutingResolveService;
  let service: StatusOfHealthService;
  let resultStatusOfHealth: IStatusOfHealth | undefined;

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
    routingResolveService = TestBed.inject(StatusOfHealthRoutingResolveService);
    service = TestBed.inject(StatusOfHealthService);
    resultStatusOfHealth = undefined;
  });

  describe('resolve', () => {
    it('should return IStatusOfHealth returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusOfHealth = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusOfHealth).toEqual({ id: 123 });
    });

    it('should return new IStatusOfHealth if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusOfHealth = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStatusOfHealth).toEqual(new StatusOfHealth());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StatusOfHealth })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultStatusOfHealth = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultStatusOfHealth).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
