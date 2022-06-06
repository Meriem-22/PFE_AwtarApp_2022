import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IChildStatusItem, ChildStatusItem } from '../child-status-item.model';
import { ChildStatusItemService } from '../service/child-status-item.service';

import { ChildStatusItemRoutingResolveService } from './child-status-item-routing-resolve.service';

describe('ChildStatusItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ChildStatusItemRoutingResolveService;
  let service: ChildStatusItemService;
  let resultChildStatusItem: IChildStatusItem | undefined;

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
    routingResolveService = TestBed.inject(ChildStatusItemRoutingResolveService);
    service = TestBed.inject(ChildStatusItemService);
    resultChildStatusItem = undefined;
  });

  describe('resolve', () => {
    it('should return IChildStatusItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChildStatusItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultChildStatusItem).toEqual({ id: 123 });
    });

    it('should return new IChildStatusItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChildStatusItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultChildStatusItem).toEqual(new ChildStatusItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ChildStatusItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultChildStatusItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultChildStatusItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
