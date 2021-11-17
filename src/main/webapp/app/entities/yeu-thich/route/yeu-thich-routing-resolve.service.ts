import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IYeuThich, YeuThich } from '../yeu-thich.model';
import { YeuThichService } from '../service/yeu-thich.service';

@Injectable({ providedIn: 'root' })
export class YeuThichRoutingResolveService implements Resolve<IYeuThich> {
  constructor(protected service: YeuThichService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IYeuThich> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((yeuThich: HttpResponse<YeuThich>) => {
          if (yeuThich.body) {
            return of(yeuThich.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new YeuThich());
  }
}
