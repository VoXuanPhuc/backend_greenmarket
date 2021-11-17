import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHoaDon, HoaDon } from '../hoa-don.model';
import { HoaDonService } from '../service/hoa-don.service';

@Injectable({ providedIn: 'root' })
export class HoaDonRoutingResolveService implements Resolve<IHoaDon> {
  constructor(protected service: HoaDonService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoaDon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hoaDon: HttpResponse<HoaDon>) => {
          if (hoaDon.body) {
            return of(hoaDon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HoaDon());
  }
}
