jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INongSan, NongSan } from '../nong-san.model';
import { NongSanService } from '../service/nong-san.service';

import { NongSanRoutingResolveService } from './nong-san-routing-resolve.service';

describe('Service Tests', () => {
  describe('NongSan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NongSanRoutingResolveService;
    let service: NongSanService;
    let resultNongSan: INongSan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NongSanRoutingResolveService);
      service = TestBed.inject(NongSanService);
      resultNongSan = undefined;
    });

    describe('resolve', () => {
      it('should return INongSan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNongSan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNongSan).toEqual({ id: 123 });
      });

      it('should return new INongSan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNongSan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNongSan).toEqual(new NongSan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NongSan })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNongSan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNongSan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
