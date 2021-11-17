import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHuyenQuan, getHuyenQuanIdentifier } from '../huyen-quan.model';

export type EntityResponseType = HttpResponse<IHuyenQuan>;
export type EntityArrayResponseType = HttpResponse<IHuyenQuan[]>;

@Injectable({ providedIn: 'root' })
export class HuyenQuanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/huyen-quans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(huyenQuan: IHuyenQuan): Observable<EntityResponseType> {
    return this.http.post<IHuyenQuan>(this.resourceUrl, huyenQuan, { observe: 'response' });
  }

  update(huyenQuan: IHuyenQuan): Observable<EntityResponseType> {
    return this.http.put<IHuyenQuan>(`${this.resourceUrl}/${getHuyenQuanIdentifier(huyenQuan) as number}`, huyenQuan, {
      observe: 'response',
    });
  }

  partialUpdate(huyenQuan: IHuyenQuan): Observable<EntityResponseType> {
    return this.http.patch<IHuyenQuan>(`${this.resourceUrl}/${getHuyenQuanIdentifier(huyenQuan) as number}`, huyenQuan, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHuyenQuan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHuyenQuan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHuyenQuanToCollectionIfMissing(
    huyenQuanCollection: IHuyenQuan[],
    ...huyenQuansToCheck: (IHuyenQuan | null | undefined)[]
  ): IHuyenQuan[] {
    const huyenQuans: IHuyenQuan[] = huyenQuansToCheck.filter(isPresent);
    if (huyenQuans.length > 0) {
      const huyenQuanCollectionIdentifiers = huyenQuanCollection.map(huyenQuanItem => getHuyenQuanIdentifier(huyenQuanItem)!);
      const huyenQuansToAdd = huyenQuans.filter(huyenQuanItem => {
        const huyenQuanIdentifier = getHuyenQuanIdentifier(huyenQuanItem);
        if (huyenQuanIdentifier == null || huyenQuanCollectionIdentifiers.includes(huyenQuanIdentifier)) {
          return false;
        }
        huyenQuanCollectionIdentifiers.push(huyenQuanIdentifier);
        return true;
      });
      return [...huyenQuansToAdd, ...huyenQuanCollection];
    }
    return huyenQuanCollection;
  }
}
