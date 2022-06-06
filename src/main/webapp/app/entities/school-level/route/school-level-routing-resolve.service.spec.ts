import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISchoolLevel, SchoolLevel } from '../school-level.model';
import { SchoolLevelService } from '../service/school-level.service';

import { SchoolLevelRoutingResolveService } from './school-level-routing-resolve.service';

describe('SchoolLevel routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SchoolLevelRoutingResolveService;
  let service: SchoolLevelService;
  let resultSchoolLevel: ISchoolLevel | undefined;

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
    routingResolveService = TestBed.inject(SchoolLevelRoutingResolveService);
    service = TestBed.inject(SchoolLevelService);
    resultSchoolLevel = undefined;
  });

  describe('resolve', () => {
    it('should return ISchoolLevel returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSchoolLevel).toEqual({ id: 123 });
    });

    it('should return new ISchoolLevel if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevel = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSchoolLevel).toEqual(new SchoolLevel());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SchoolLevel })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSchoolLevel = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSchoolLevel).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
