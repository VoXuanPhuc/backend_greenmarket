import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHuyenQuan, HuyenQuan } from '../huyen-quan.model';
import { HuyenQuanService } from '../service/huyen-quan.service';

@Injectable({ providedIn: 'root' })
export class HuyenQuanRoutingResolveService implements Resolve<IHuyenQuan> {
  constructor(protected service: HuyenQuanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHuyenQuan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((huyenQuan: HttpResponse<HuyenQuan>) => {
          if (huyenQuan.body) {
            return of(huyenQuan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HuyenQuan());
  }
}
