import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITeachingCurriculum, TeachingCurriculum } from '../teaching-curriculum.model';
import { TeachingCurriculumService } from '../service/teaching-curriculum.service';

import { TeachingCurriculumRoutingResolveService } from './teaching-curriculum-routing-resolve.service';

describe('TeachingCurriculum routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TeachingCurriculumRoutingResolveService;
  let service: TeachingCurriculumService;
  let resultTeachingCurriculum: ITeachingCurriculum | undefined;

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
    routingResolveService = TestBed.inject(TeachingCurriculumRoutingResolveService);
    service = TestBed.inject(TeachingCurriculumService);
    resultTeachingCurriculum = undefined;
  });

  describe('resolve', () => {
    it('should return ITeachingCurriculum returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeachingCurriculum = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTeachingCurriculum).toEqual({ id: 123 });
    });

    it('should return new ITeachingCurriculum if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeachingCurriculum = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTeachingCurriculum).toEqual(new TeachingCurriculum());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TeachingCurriculum })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTeachingCurriculum = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTeachingCurriculum).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
