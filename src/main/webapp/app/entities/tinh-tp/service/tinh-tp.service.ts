import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITinhTP, getTinhTPIdentifier } from '../tinh-tp.model';

export type EntityResponseType = HttpResponse<ITinhTP>;
export type EntityArrayResponseType = HttpResponse<ITinhTP[]>;

@Injectable({ providedIn: 'root' })
export class TinhTPService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tinh-tps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tinhTP: ITinhTP): Observable<EntityResponseType> {
    return this.http.post<ITinhTP>(this.resourceUrl, tinhTP, { observe: 'response' });
  }

  update(tinhTP: ITinhTP): Observable<EntityResponseType> {
    return this.http.put<ITinhTP>(`${this.resourceUrl}/${getTinhTPIdentifier(tinhTP) as number}`, tinhTP, { observe: 'response' });
  }

  partialUpdate(tinhTP: ITinhTP): Observable<EntityResponseType> {
    return this.http.patch<ITinhTP>(`${this.resourceUrl}/${getTinhTPIdentifier(tinhTP) as number}`, tinhTP, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITinhTP>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITinhTP[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTinhTPToCollectionIfMissing(tinhTPCollection: ITinhTP[], ...tinhTPSToCheck: (ITinhTP | null | undefined)[]): ITinhTP[] {
    const tinhTPS: ITinhTP[] = tinhTPSToCheck.filter(isPresent);
    if (tinhTPS.length > 0) {
      const tinhTPCollectionIdentifiers = tinhTPCollection.map(tinhTPItem => getTinhTPIdentifier(tinhTPItem)!);
      const tinhTPSToAdd = tinhTPS.filter(tinhTPItem => {
        const tinhTPIdentifier = getTinhTPIdentifier(tinhTPItem);
        if (tinhTPIdentifier == null || tinhTPCollectionIdentifiers.includes(tinhTPIdentifier)) {
          return false;
        }
        tinhTPCollectionIdentifiers.push(tinhTPIdentifier);
        return true;
      });
      return [...tinhTPSToAdd, ...tinhTPCollection];
    }
    return tinhTPCollection;
  }
}
