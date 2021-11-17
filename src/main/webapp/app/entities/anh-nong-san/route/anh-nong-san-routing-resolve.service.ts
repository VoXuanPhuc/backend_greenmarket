import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnhNongSan, AnhNongSan } from '../anh-nong-san.model';
import { AnhNongSanService } from '../service/anh-nong-san.service';

@Injectable({ providedIn: 'root' })
export class AnhNongSanRoutingResolveService implements Resolve<IAnhNongSan> {
  constructor(protected service: AnhNongSanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnhNongSan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((anhNongSan: HttpResponse<AnhNongSan>) => {
          if (anhNongSan.body) {
            return of(anhNongSan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnhNongSan());
  }
}
