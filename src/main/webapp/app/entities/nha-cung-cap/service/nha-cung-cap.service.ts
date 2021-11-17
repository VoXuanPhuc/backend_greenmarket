import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhaCungCap, getNhaCungCapIdentifier } from '../nha-cung-cap.model';

export type EntityResponseType = HttpResponse<INhaCungCap>;
export type EntityArrayResponseType = HttpResponse<INhaCungCap[]>;

@Injectable({ providedIn: 'root' })
export class NhaCungCapService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nha-cung-caps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nhaCungCap: INhaCungCap): Observable<EntityResponseType> {
    return this.http.post<INhaCungCap>(this.resourceUrl, nhaCungCap, { observe: 'response' });
  }

  update(nhaCungCap: INhaCungCap): Observable<EntityResponseType> {
    return this.http.put<INhaCungCap>(`${this.resourceUrl}/${getNhaCungCapIdentifier(nhaCungCap) as number}`, nhaCungCap, {
      observe: 'response',
    });
  }

  partialUpdate(nhaCungCap: INhaCungCap): Observable<EntityResponseType> {
    return this.http.patch<INhaCungCap>(`${this.resourceUrl}/${getNhaCungCapIdentifier(nhaCungCap) as number}`, nhaCungCap, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INhaCungCap>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INhaCungCap[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNhaCungCapToCollectionIfMissing(
    nhaCungCapCollection: INhaCungCap[],
    ...nhaCungCapsToCheck: (INhaCungCap | null | undefined)[]
  ): INhaCungCap[] {
    const nhaCungCaps: INhaCungCap[] = nhaCungCapsToCheck.filter(isPresent);
    if (nhaCungCaps.length > 0) {
      const nhaCungCapCollectionIdentifiers = nhaCungCapCollection.map(nhaCungCapItem => getNhaCungCapIdentifier(nhaCungCapItem)!);
      const nhaCungCapsToAdd = nhaCungCaps.filter(nhaCungCapItem => {
        const nhaCungCapIdentifier = getNhaCungCapIdentifier(nhaCungCapItem);
        if (nhaCungCapIdentifier == null || nhaCungCapCollectionIdentifiers.includes(nhaCungCapIdentifier)) {
          return false;
        }
        nhaCungCapCollectionIdentifiers.push(nhaCungCapIdentifier);
        return true;
      });
      return [...nhaCungCapsToAdd, ...nhaCungCapCollection];
    }
    return nhaCungCapCollection;
  }
}
