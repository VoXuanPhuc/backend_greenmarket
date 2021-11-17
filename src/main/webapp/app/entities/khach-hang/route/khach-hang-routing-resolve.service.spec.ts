jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IKhachHang, KhachHang } from '../khach-hang.model';
import { KhachHangService } from '../service/khach-hang.service';

import { KhachHangRoutingResolveService } from './khach-hang-routing-resolve.service';

describe('Service Tests', () => {
  describe('KhachHang routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: KhachHangRoutingResolveService;
    let service: KhachHangService;
    let resultKhachHang: IKhachHang | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(KhachHangRoutingResolveService);
      service = TestBed.inject(KhachHangService);
      resultKhachHang = undefined;
    });

    describe('resolve', () => {
      it('should return IKhachHang returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKhachHang = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultKhachHang).toEqual({ id: 123 });
      });

      it('should return new IKhachHang if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKhachHang = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultKhachHang).toEqual(new KhachHang());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as KhachHang })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultKhachHang = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultKhachHang).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
