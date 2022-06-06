import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISchoolLevelItem, SchoolLevelItem } from '../school-level-item.model';
import { SchoolLevelItemService } from '../service/school-level-item.service';

import { SchoolLevelItemRoutingResolveService } from './school-level-item-routing-resolve.service';

describe('SchoolLevelItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SchoolLevelItemRoutingResolveService;
  let service: SchoolLevelItemService;
  let resultSchoolLevelItem: ISchoolLevelItem | undefined;

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
    routingResolveService = TestBed.inject(SchoolLevelItemRoutingResolveService);
    service = TestBed.inject(SchoolLevelItemService);
    resultSchoolLevelItem = undefined;
  });

  describe('resolve', () => {
    it('should return ISchoolLevelItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevelItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSchoolLevelItem).toEqual({ id: 123 });
    });

    it('should return new ISchoolLevelItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevelItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSchoolLevelItem).toEqual(new SchoolLevelItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SchoolLevelItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevelItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSchoolLevelItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
