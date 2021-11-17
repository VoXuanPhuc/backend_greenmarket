import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

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
    const copy = this.convertDateFromClient(nongSan);
    return this.http
      .post<INongSan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nongSan: INongSan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nongSan);
    return this.http
      .put<INongSan>(`${this.resourceUrl}/${getNongSanIdentifier(nongSan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(nongSan: INongSan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nongSan);
    return this.http
      .patch<INongSan>(`${this.resourceUrl}/${getNongSanIdentifier(nongSan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INongSan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INongSan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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

  protected convertDateFromClient(nongSan: INongSan): INongSan {
    return Object.assign({}, nongSan, {
      noiSanXuat: nongSan.noiSanXuat?.isValid() ? nongSan.noiSanXuat.toJSON() : undefined,
      moTaNS: nongSan.moTaNS?.isValid() ? nongSan.moTaNS.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.noiSanXuat = res.body.noiSanXuat ? dayjs(res.body.noiSanXuat) : undefined;
      res.body.moTaNS = res.body.moTaNS ? dayjs(res.body.moTaNS) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nongSan: INongSan) => {
        nongSan.noiSanXuat = nongSan.noiSanXuat ? dayjs(nongSan.noiSanXuat) : undefined;
        nongSan.moTaNS = nongSan.moTaNS ? dayjs(nongSan.moTaNS) : undefined;
      });
    }
    return res;
  }
}
