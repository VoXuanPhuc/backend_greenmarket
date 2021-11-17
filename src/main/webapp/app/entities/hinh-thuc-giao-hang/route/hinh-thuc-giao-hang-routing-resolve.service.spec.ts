jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHinhThucGiaoHang, HinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';

import { HinhThucGiaoHangRoutingResolveService } from './hinh-thuc-giao-hang-routing-resolve.service';

describe('Service Tests', () => {
  describe('HinhThucGiaoHang routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HinhThucGiaoHangRoutingResolveService;
    let service: HinhThucGiaoHangService;
    let resultHinhThucGiaoHang: IHinhThucGiaoHang | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HinhThucGiaoHangRoutingResolveService);
      service = TestBed.inject(HinhThucGiaoHangService);
      resultHinhThucGiaoHang = undefined;
    });

    describe('resolve', () => {
      it('should return IHinhThucGiaoHang returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHinhThucGiaoHang = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHinhThucGiaoHang).toEqual({ id: 123 });
      });

      it('should return new IHinhThucGiaoHang if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHinhThucGiaoHang = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHinhThucGiaoHang).toEqual(new HinhThucGiaoHang());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as HinhThucGiaoHang })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHinhThucGiaoHang = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHinhThucGiaoHang).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
