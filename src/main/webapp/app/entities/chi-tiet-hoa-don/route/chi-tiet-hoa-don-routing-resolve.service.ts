import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChiTietHoaDon, ChiTietHoaDon } from '../chi-tiet-hoa-don.model';
import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';

@Injectable({ providedIn: 'root' })
export class ChiTietHoaDonRoutingResolveService implements Resolve<IChiTietHoaDon> {
  constructor(protected service: ChiTietHoaDonService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChiTietHoaDon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((chiTietHoaDon: HttpResponse<ChiTietHoaDon>) => {
          if (chiTietHoaDon.body) {
            return of(chiTietHoaDon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChiTietHoaDon());
  }
}
