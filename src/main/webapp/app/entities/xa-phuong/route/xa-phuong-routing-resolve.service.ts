import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IXaPhuong, XaPhuong } from '../xa-phuong.model';
import { XaPhuongService } from '../service/xa-phuong.service';

@Injectable({ providedIn: 'root' })
export class XaPhuongRoutingResolveService implements Resolve<IXaPhuong> {
  constructor(protected service: XaPhuongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IXaPhuong> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((xaPhuong: HttpResponse<XaPhuong>) => {
          if (xaPhuong.body) {
            return of(xaPhuong.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new XaPhuong());
  }
}
