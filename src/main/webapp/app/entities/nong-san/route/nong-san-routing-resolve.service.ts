import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INongSan, NongSan } from '../nong-san.model';
import { NongSanService } from '../service/nong-san.service';

@Injectable({ providedIn: 'root' })
export class NongSanRoutingResolveService implements Resolve<INongSan> {
  constructor(protected service: NongSanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INongSan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nongSan: HttpResponse<NongSan>) => {
          if (nongSan.body) {
            return of(nongSan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NongSan());
  }
}
