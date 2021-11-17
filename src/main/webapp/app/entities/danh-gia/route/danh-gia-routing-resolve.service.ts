import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDanhGia, DanhGia } from '../danh-gia.model';
import { DanhGiaService } from '../service/danh-gia.service';

@Injectable({ providedIn: 'root' })
export class DanhGiaRoutingResolveService implements Resolve<IDanhGia> {
  constructor(protected service: DanhGiaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDanhGia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((danhGia: HttpResponse<DanhGia>) => {
          if (danhGia.body) {
            return of(danhGia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DanhGia());
  }
}
