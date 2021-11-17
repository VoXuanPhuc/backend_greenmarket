import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDanhMuc, DanhMuc } from '../danh-muc.model';
import { DanhMucService } from '../service/danh-muc.service';

@Injectable({ providedIn: 'root' })
export class DanhMucRoutingResolveService implements Resolve<IDanhMuc> {
  constructor(protected service: DanhMucService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDanhMuc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((danhMuc: HttpResponse<DanhMuc>) => {
          if (danhMuc.body) {
            return of(danhMuc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DanhMuc());
  }
}
