import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';

import { AfterEffectsTemplateAssetsRoutingResolveService } from './after-effects-template-assets-routing-resolve.service';

describe('AfterEffectsTemplateAssets routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AfterEffectsTemplateAssetsRoutingResolveService;
  let service: AfterEffectsTemplateAssetsService;
  let resultAfterEffectsTemplateAssets: IAfterEffectsTemplateAssets | null | undefined;

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
    routingResolveService = TestBed.inject(AfterEffectsTemplateAssetsRoutingResolveService);
    service = TestBed.inject(AfterEffectsTemplateAssetsService);
    resultAfterEffectsTemplateAssets = undefined;
  });

  describe('resolve', () => {
    it('should return IAfterEffectsTemplateAssets returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAfterEffectsTemplateAssets = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAfterEffectsTemplateAssets).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAfterEffectsTemplateAssets = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAfterEffectsTemplateAssets).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAfterEffectsTemplateAssets>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAfterEffectsTemplateAssets = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAfterEffectsTemplateAssets).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
