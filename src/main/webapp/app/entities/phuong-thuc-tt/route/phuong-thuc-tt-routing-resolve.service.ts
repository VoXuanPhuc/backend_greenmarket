import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPhuongThucTT, PhuongThucTT } from '../phuong-thuc-tt.model';
import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';

@Injectable({ providedIn: 'root' })
export class PhuongThucTTRoutingResolveService implements Resolve<IPhuongThucTT> {
  constructor(protected service: PhuongThucTTService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhuongThucTT> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((phuongThucTT: HttpResponse<PhuongThucTT>) => {
          if (phuongThucTT.body) {
            return of(phuongThucTT.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhuongThucTT());
  }
}
