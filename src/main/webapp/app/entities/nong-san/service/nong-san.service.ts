import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INongSan, getNongSanIdentifier } from '../nong-san.model';

export type EntityResponseType = HttpResponse<INongSan>;
export type EntityArrayResponseType = HttpResponse<INongSan[]>;

@Injectable({ providedIn: 'root' })
export class NongSanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nong-sans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nongSan: INongSan): Observable<EntityResponseType> {
    return this.http.post<INongSan>(this.resourceUrl, nongSan, { observe: 'response' });
  }

  update(nongSan: INongSan): Observable<EntityResponseType> {
    return this.http.put<INongSan>(`${this.resourceUrl}/${getNongSanIdentifier(nongSan) as number}`, nongSan, { observe: 'response' });
  }

  partialUpdate(nongSan: INongSan): Observable<EntityResponseType> {
    return this.http.patch<INongSan>(`${this.resourceUrl}/${getNongSanIdentifier(nongSan) as number}`, nongSan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INongSan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INongSan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNongSanToCollectionIfMissing(nongSanCollection: INongSan[], ...nongSansToCheck: (INongSan | null | undefined)[]): INongSan[] {
    const nongSans: INongSan[] = nongSansToCheck.filter(isPresent);
    if (nongSans.length > 0) {
      const nongSanCollectionIdentifiers = nongSanCollection.map(nongSanItem => getNongSanIdentifier(nongSanItem)!);
      const nongSansToAdd = nongSans.filter(nongSanItem => {
        const nongSanIdentifier = getNongSanIdentifier(nongSanItem);
        if (nongSanIdentifier == null || nongSanCollectionIdentifiers.includes(nongSanIdentifier)) {
          return false;
        }
        nongSanCollectionIdentifiers.push(nongSanIdentifier);
        return true;
      });
      return [...nongSansToAdd, ...nongSanCollection];
    }
    return nongSanCollection;
  }
}
