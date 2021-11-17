jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INhaCungCap, NhaCungCap } from '../nha-cung-cap.model';
import { NhaCungCapService } from '../service/nha-cung-cap.service';

import { NhaCungCapRoutingResolveService } from './nha-cung-cap-routing-resolve.service';

describe('Service Tests', () => {
  describe('NhaCungCap routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NhaCungCapRoutingResolveService;
    let service: NhaCungCapService;
    let resultNhaCungCap: INhaCungCap | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NhaCungCapRoutingResolveService);
      service = TestBed.inject(NhaCungCapService);
      resultNhaCungCap = undefined;
    });

    describe('resolve', () => {
      it('should return INhaCungCap returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhaCungCap = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNhaCungCap).toEqual({ id: 123 });
      });

      it('should return new INhaCungCap if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhaCungCap = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNhaCungCap).toEqual(new NhaCungCap());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NhaCungCap })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNhaCungCap = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNhaCungCap).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
