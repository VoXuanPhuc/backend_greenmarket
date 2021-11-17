jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDanhGia, DanhGia } from '../danh-gia.model';
import { DanhGiaService } from '../service/danh-gia.service';

import { DanhGiaRoutingResolveService } from './danh-gia-routing-resolve.service';

describe('Service Tests', () => {
  describe('DanhGia routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DanhGiaRoutingResolveService;
    let service: DanhGiaService;
    let resultDanhGia: IDanhGia | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DanhGiaRoutingResolveService);
      service = TestBed.inject(DanhGiaService);
      resultDanhGia = undefined;
    });

    describe('resolve', () => {
      it('should return IDanhGia returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhGia = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDanhGia).toEqual({ id: 123 });
      });

      it('should return new IDanhGia if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhGia = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDanhGia).toEqual(new DanhGia());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DanhGia })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhGia = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDanhGia).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
