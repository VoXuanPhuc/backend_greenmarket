import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhuongThucTT, getPhuongThucTTIdentifier } from '../phuong-thuc-tt.model';

export type EntityResponseType = HttpResponse<IPhuongThucTT>;
export type EntityArrayResponseType = HttpResponse<IPhuongThucTT[]>;

@Injectable({ providedIn: 'root' })
export class PhuongThucTTService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/phuong-thuc-tts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(phuongThucTT: IPhuongThucTT): Observable<EntityResponseType> {
    return this.http.post<IPhuongThucTT>(this.resourceUrl, phuongThucTT, { observe: 'response' });
  }

  update(phuongThucTT: IPhuongThucTT): Observable<EntityResponseType> {
    return this.http.put<IPhuongThucTT>(`${this.resourceUrl}/${getPhuongThucTTIdentifier(phuongThucTT) as number}`, phuongThucTT, {
      observe: 'response',
    });
  }

  partialUpdate(phuongThucTT: IPhuongThucTT): Observable<EntityResponseType> {
    return this.http.patch<IPhuongThucTT>(`${this.resourceUrl}/${getPhuongThucTTIdentifier(phuongThucTT) as number}`, phuongThucTT, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhuongThucTT>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhuongThucTT[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPhuongThucTTToCollectionIfMissing(
    phuongThucTTCollection: IPhuongThucTT[],
    ...phuongThucTTSToCheck: (IPhuongThucTT | null | undefined)[]
  ): IPhuongThucTT[] {
    const phuongThucTTS: IPhuongThucTT[] = phuongThucTTSToCheck.filter(isPresent);
    if (phuongThucTTS.length > 0) {
      const phuongThucTTCollectionIdentifiers = phuongThucTTCollection.map(
        phuongThucTTItem => getPhuongThucTTIdentifier(phuongThucTTItem)!
      );
      const phuongThucTTSToAdd = phuongThucTTS.filter(phuongThucTTItem => {
        const phuongThucTTIdentifier = getPhuongThucTTIdentifier(phuongThucTTItem);
        if (phuongThucTTIdentifier == null || phuongThucTTCollectionIdentifiers.includes(phuongThucTTIdentifier)) {
          return false;
        }
        phuongThucTTCollectionIdentifiers.push(phuongThucTTIdentifier);
        return true;
      });
      return [...phuongThucTTSToAdd, ...phuongThucTTCollection];
    }
    return phuongThucTTCollection;
  }
}
