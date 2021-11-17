import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChiTietHoaDon, getChiTietHoaDonIdentifier } from '../chi-tiet-hoa-don.model';

export type EntityResponseType = HttpResponse<IChiTietHoaDon>;
export type EntityArrayResponseType = HttpResponse<IChiTietHoaDon[]>;

@Injectable({ providedIn: 'root' })
export class ChiTietHoaDonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chi-tiet-hoa-dons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chiTietHoaDon: IChiTietHoaDon): Observable<EntityResponseType> {
    return this.http.post<IChiTietHoaDon>(this.resourceUrl, chiTietHoaDon, { observe: 'response' });
  }

  update(chiTietHoaDon: IChiTietHoaDon): Observable<EntityResponseType> {
    return this.http.put<IChiTietHoaDon>(`${this.resourceUrl}/${getChiTietHoaDonIdentifier(chiTietHoaDon) as number}`, chiTietHoaDon, {
      observe: 'response',
    });
  }

  partialUpdate(chiTietHoaDon: IChiTietHoaDon): Observable<EntityResponseType> {
    return this.http.patch<IChiTietHoaDon>(`${this.resourceUrl}/${getChiTietHoaDonIdentifier(chiTietHoaDon) as number}`, chiTietHoaDon, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChiTietHoaDon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChiTietHoaDon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addChiTietHoaDonToCollectionIfMissing(
    chiTietHoaDonCollection: IChiTietHoaDon[],
    ...chiTietHoaDonsToCheck: (IChiTietHoaDon | null | undefined)[]
  ): IChiTietHoaDon[] {
    const chiTietHoaDons: IChiTietHoaDon[] = chiTietHoaDonsToCheck.filter(isPresent);
    if (chiTietHoaDons.length > 0) {
      const chiTietHoaDonCollectionIdentifiers = chiTietHoaDonCollection.map(
        chiTietHoaDonItem => getChiTietHoaDonIdentifier(chiTietHoaDonItem)!
      );
      const chiTietHoaDonsToAdd = chiTietHoaDons.filter(chiTietHoaDonItem => {
        const chiTietHoaDonIdentifier = getChiTietHoaDonIdentifier(chiTietHoaDonItem);
        if (chiTietHoaDonIdentifier == null || chiTietHoaDonCollectionIdentifiers.includes(chiTietHoaDonIdentifier)) {
          return false;
        }
        chiTietHoaDonCollectionIdentifiers.push(chiTietHoaDonIdentifier);
        return true;
      });
      return [...chiTietHoaDonsToAdd, ...chiTietHoaDonCollection];
    }
    return chiTietHoaDonCollection;
  }
}
