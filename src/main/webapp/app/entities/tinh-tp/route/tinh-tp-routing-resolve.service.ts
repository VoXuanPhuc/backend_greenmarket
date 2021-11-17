import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITinhTP, TinhTP } from '../tinh-tp.model';
import { TinhTPService } from '../service/tinh-tp.service';

@Injectable({ providedIn: 'root' })
export class TinhTPRoutingResolveService implements Resolve<ITinhTP> {
  constructor(protected service: TinhTPService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITinhTP> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tinhTP: HttpResponse<TinhTP>) => {
          if (tinhTP.body) {
            return of(tinhTP.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TinhTP());
  }
}
