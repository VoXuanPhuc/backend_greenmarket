import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnhDanhGia, AnhDanhGia } from '../anh-danh-gia.model';
import { AnhDanhGiaService } from '../service/anh-danh-gia.service';

@Injectable({ providedIn: 'root' })
export class AnhDanhGiaRoutingResolveService implements Resolve<IAnhDanhGia> {
  constructor(protected service: AnhDanhGiaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnhDanhGia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((anhDanhGia: HttpResponse<AnhDanhGia>) => {
          if (anhDanhGia.body) {
            return of(anhDanhGia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnhDanhGia());
  }
}
