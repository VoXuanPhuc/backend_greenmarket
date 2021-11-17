import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IYeuThich, getYeuThichIdentifier } from '../yeu-thich.model';

export type EntityResponseType = HttpResponse<IYeuThich>;
export type EntityArrayResponseType = HttpResponse<IYeuThich[]>;

@Injectable({ providedIn: 'root' })
export class YeuThichService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/yeu-thiches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(yeuThich: IYeuThich): Observable<EntityResponseType> {
    return this.http.post<IYeuThich>(this.resourceUrl, yeuThich, { observe: 'response' });
  }

  update(yeuThich: IYeuThich): Observable<EntityResponseType> {
    return this.http.put<IYeuThich>(`${this.resourceUrl}/${getYeuThichIdentifier(yeuThich) as number}`, yeuThich, { observe: 'response' });
  }

  partialUpdate(yeuThich: IYeuThich): Observable<EntityResponseType> {
    return this.http.patch<IYeuThich>(`${this.resourceUrl}/${getYeuThichIdentifier(yeuThich) as number}`, yeuThich, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IYeuThich>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IYeuThich[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addYeuThichToCollectionIfMissing(yeuThichCollection: IYeuThich[], ...yeuThichesToCheck: (IYeuThich | null | undefined)[]): IYeuThich[] {
    const yeuThiches: IYeuThich[] = yeuThichesToCheck.filter(isPresent);
    if (yeuThiches.length > 0) {
      const yeuThichCollectionIdentifiers = yeuThichCollection.map(yeuThichItem => getYeuThichIdentifier(yeuThichItem)!);
      const yeuThichesToAdd = yeuThiches.filter(yeuThichItem => {
        const yeuThichIdentifier = getYeuThichIdentifier(yeuThichItem);
        if (yeuThichIdentifier == null || yeuThichCollectionIdentifiers.includes(yeuThichIdentifier)) {
          return false;
        }
        yeuThichCollectionIdentifiers.push(yeuThichIdentifier);
        return true;
      });
      return [...yeuThichesToAdd, ...yeuThichCollection];
    }
    return yeuThichCollection;
  }
}
