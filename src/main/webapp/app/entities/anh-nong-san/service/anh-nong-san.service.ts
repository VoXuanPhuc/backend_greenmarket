import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnhNongSan, getAnhNongSanIdentifier } from '../anh-nong-san.model';

export type EntityResponseType = HttpResponse<IAnhNongSan>;
export type EntityArrayResponseType = HttpResponse<IAnhNongSan[]>;

@Injectable({ providedIn: 'root' })
export class AnhNongSanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anh-nong-sans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anhNongSan: IAnhNongSan): Observable<EntityResponseType> {
    return this.http.post<IAnhNongSan>(this.resourceUrl, anhNongSan, { observe: 'response' });
  }

  update(anhNongSan: IAnhNongSan): Observable<EntityResponseType> {
    return this.http.put<IAnhNongSan>(`${this.resourceUrl}/${getAnhNongSanIdentifier(anhNongSan) as number}`, anhNongSan, {
      observe: 'response',
    });
  }

  partialUpdate(anhNongSan: IAnhNongSan): Observable<EntityResponseType> {
    return this.http.patch<IAnhNongSan>(`${this.resourceUrl}/${getAnhNongSanIdentifier(anhNongSan) as number}`, anhNongSan, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnhNongSan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnhNongSan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnhNongSanToCollectionIfMissing(
    anhNongSanCollection: IAnhNongSan[],
    ...anhNongSansToCheck: (IAnhNongSan | null | undefined)[]
  ): IAnhNongSan[] {
    const anhNongSans: IAnhNongSan[] = anhNongSansToCheck.filter(isPresent);
    if (anhNongSans.length > 0) {
      const anhNongSanCollectionIdentifiers = anhNongSanCollection.map(anhNongSanItem => getAnhNongSanIdentifier(anhNongSanItem)!);
      const anhNongSansToAdd = anhNongSans.filter(anhNongSanItem => {
        const anhNongSanIdentifier = getAnhNongSanIdentifier(anhNongSanItem);
        if (anhNongSanIdentifier == null || anhNongSanCollectionIdentifiers.includes(anhNongSanIdentifier)) {
          return false;
        }
        anhNongSanCollectionIdentifiers.push(anhNongSanIdentifier);
        return true;
      });
      return [...anhNongSansToAdd, ...anhNongSanCollection];
    }
    return anhNongSanCollection;
  }
}
