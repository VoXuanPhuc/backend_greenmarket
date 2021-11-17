jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IYeuThich, YeuThich } from '../yeu-thich.model';
import { YeuThichService } from '../service/yeu-thich.service';

import { YeuThichRoutingResolveService } from './yeu-thich-routing-resolve.service';

describe('Service Tests', () => {
  describe('YeuThich routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: YeuThichRoutingResolveService;
    let service: YeuThichService;
    let resultYeuThich: IYeuThich | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(YeuThichRoutingResolveService);
      service = TestBed.inject(YeuThichService);
      resultYeuThich = undefined;
    });

    describe('resolve', () => {
      it('should return IYeuThich returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultYeuThich = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultYeuThich).toEqual({ id: 123 });
      });

      it('should return new IYeuThich if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultYeuThich = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultYeuThich).toEqual(new YeuThich());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as YeuThich })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultYeuThich = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultYeuThich).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
