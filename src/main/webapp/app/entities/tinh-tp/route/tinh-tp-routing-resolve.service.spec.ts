jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITinhTP, TinhTP } from '../tinh-tp.model';
import { TinhTPService } from '../service/tinh-tp.service';

import { TinhTPRoutingResolveService } from './tinh-tp-routing-resolve.service';

describe('Service Tests', () => {
  describe('TinhTP routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TinhTPRoutingResolveService;
    let service: TinhTPService;
    let resultTinhTP: ITinhTP | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TinhTPRoutingResolveService);
      service = TestBed.inject(TinhTPService);
      resultTinhTP = undefined;
    });

    describe('resolve', () => {
      it('should return ITinhTP returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTinhTP = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTinhTP).toEqual({ id: 123 });
      });

      it('should return new ITinhTP if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTinhTP = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTinhTP).toEqual(new TinhTP());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TinhTP })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTinhTP = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTinhTP).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
