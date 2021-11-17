import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDanhMuc, getDanhMucIdentifier } from '../danh-muc.model';

export type EntityResponseType = HttpResponse<IDanhMuc>;
export type EntityArrayResponseType = HttpResponse<IDanhMuc[]>;

@Injectable({ providedIn: 'root' })
export class DanhMucService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/danh-mucs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(danhMuc: IDanhMuc): Observable<EntityResponseType> {
    return this.http.post<IDanhMuc>(this.resourceUrl, danhMuc, { observe: 'response' });
  }

  update(danhMuc: IDanhMuc): Observable<EntityResponseType> {
    return this.http.put<IDanhMuc>(`${this.resourceUrl}/${getDanhMucIdentifier(danhMuc) as number}`, danhMuc, { observe: 'response' });
  }

  partialUpdate(danhMuc: IDanhMuc): Observable<EntityResponseType> {
    return this.http.patch<IDanhMuc>(`${this.resourceUrl}/${getDanhMucIdentifier(danhMuc) as number}`, danhMuc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDanhMuc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDanhMuc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDanhMucToCollectionIfMissing(danhMucCollection: IDanhMuc[], ...danhMucsToCheck: (IDanhMuc | null | undefined)[]): IDanhMuc[] {
    const danhMucs: IDanhMuc[] = danhMucsToCheck.filter(isPresent);
    if (danhMucs.length > 0) {
      const danhMucCollectionIdentifiers = danhMucCollection.map(danhMucItem => getDanhMucIdentifier(danhMucItem)!);
      const danhMucsToAdd = danhMucs.filter(danhMucItem => {
        const danhMucIdentifier = getDanhMucIdentifier(danhMucItem);
        if (danhMucIdentifier == null || danhMucCollectionIdentifiers.includes(danhMucIdentifier)) {
          return false;
        }
        danhMucCollectionIdentifiers.push(danhMucIdentifier);
        return true;
      });
      return [...danhMucsToAdd, ...danhMucCollection];
    }
    return danhMucCollection;
  }
}
