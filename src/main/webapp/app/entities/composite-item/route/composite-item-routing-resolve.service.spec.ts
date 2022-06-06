import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICompositeItem, CompositeItem } from '../composite-item.model';
import { CompositeItemService } from '../service/composite-item.service';

import { CompositeItemRoutingResolveService } from './composite-item-routing-resolve.service';

describe('CompositeItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CompositeItemRoutingResolveService;
  let service: CompositeItemService;
  let resultCompositeItem: ICompositeItem | undefined;

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
    routingResolveService = TestBed.inject(CompositeItemRoutingResolveService);
    service = TestBed.inject(CompositeItemService);
    resultCompositeItem = undefined;
  });

  describe('resolve', () => {
    it('should return ICompositeItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCompositeItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCompositeItem).toEqual({ id: 123 });
    });

    it('should return new ICompositeItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCompositeItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCompositeItem).toEqual(new CompositeItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CompositeItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCompositeItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCompositeItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
