jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAnhNongSan, AnhNongSan } from '../anh-nong-san.model';
import { AnhNongSanService } from '../service/anh-nong-san.service';

import { AnhNongSanRoutingResolveService } from './anh-nong-san-routing-resolve.service';

describe('Service Tests', () => {
  describe('AnhNongSan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AnhNongSanRoutingResolveService;
    let service: AnhNongSanService;
    let resultAnhNongSan: IAnhNongSan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AnhNongSanRoutingResolveService);
      service = TestBed.inject(AnhNongSanService);
      resultAnhNongSan = undefined;
    });

    describe('resolve', () => {
      it('should return IAnhNongSan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAnhNongSan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAnhNongSan).toEqual({ id: 123 });
      });

      it('should return new IAnhNongSan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAnhNongSan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAnhNongSan).toEqual(new AnhNongSan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AnhNongSan })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAnhNongSan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAnhNongSan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
