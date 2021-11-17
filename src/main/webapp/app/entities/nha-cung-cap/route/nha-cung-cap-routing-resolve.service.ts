import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INhaCungCap, NhaCungCap } from '../nha-cung-cap.model';
import { NhaCungCapService } from '../service/nha-cung-cap.service';

@Injectable({ providedIn: 'root' })
export class NhaCungCapRoutingResolveService implements Resolve<INhaCungCap> {
  constructor(protected service: NhaCungCapService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INhaCungCap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nhaCungCap: HttpResponse<NhaCungCap>) => {
          if (nhaCungCap.body) {
            return of(nhaCungCap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NhaCungCap());
  }
}
