jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IXaPhuong, XaPhuong } from '../xa-phuong.model';
import { XaPhuongService } from '../service/xa-phuong.service';

import { XaPhuongRoutingResolveService } from './xa-phuong-routing-resolve.service';

describe('Service Tests', () => {
  describe('XaPhuong routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: XaPhuongRoutingResolveService;
    let service: XaPhuongService;
    let resultXaPhuong: IXaPhuong | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(XaPhuongRoutingResolveService);
      service = TestBed.inject(XaPhuongService);
      resultXaPhuong = undefined;
    });

    describe('resolve', () => {
      it('should return IXaPhuong returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultXaPhuong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultXaPhuong).toEqual({ id: 123 });
      });

      it('should return new IXaPhuong if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultXaPhuong = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultXaPhuong).toEqual(new XaPhuong());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as XaPhuong })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultXaPhuong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultXaPhuong).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
