jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDanhMuc, DanhMuc } from '../danh-muc.model';
import { DanhMucService } from '../service/danh-muc.service';

import { DanhMucRoutingResolveService } from './danh-muc-routing-resolve.service';

describe('Service Tests', () => {
  describe('DanhMuc routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DanhMucRoutingResolveService;
    let service: DanhMucService;
    let resultDanhMuc: IDanhMuc | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DanhMucRoutingResolveService);
      service = TestBed.inject(DanhMucService);
      resultDanhMuc = undefined;
    });

    describe('resolve', () => {
      it('should return IDanhMuc returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhMuc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDanhMuc).toEqual({ id: 123 });
      });

      it('should return new IDanhMuc if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhMuc = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDanhMuc).toEqual(new DanhMuc());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as DanhMuc })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDanhMuc = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDanhMuc).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
