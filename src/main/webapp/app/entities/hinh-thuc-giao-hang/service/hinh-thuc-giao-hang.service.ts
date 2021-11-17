import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHinhThucGiaoHang, getHinhThucGiaoHangIdentifier } from '../hinh-thuc-giao-hang.model';

export type EntityResponseType = HttpResponse<IHinhThucGiaoHang>;
export type EntityArrayResponseType = HttpResponse<IHinhThucGiaoHang[]>;

@Injectable({ providedIn: 'root' })
export class HinhThucGiaoHangService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hinh-thuc-giao-hangs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hinhThucGiaoHang: IHinhThucGiaoHang): Observable<EntityResponseType> {
    return this.http.post<IHinhThucGiaoHang>(this.resourceUrl, hinhThucGiaoHang, { observe: 'response' });
  }

  update(hinhThucGiaoHang: IHinhThucGiaoHang): Observable<EntityResponseType> {
    return this.http.put<IHinhThucGiaoHang>(
      `${this.resourceUrl}/${getHinhThucGiaoHangIdentifier(hinhThucGiaoHang) as number}`,
      hinhThucGiaoHang,
      { observe: 'response' }
    );
  }

  partialUpdate(hinhThucGiaoHang: IHinhThucGiaoHang): Observable<EntityResponseType> {
    return this.http.patch<IHinhThucGiaoHang>(
      `${this.resourceUrl}/${getHinhThucGiaoHangIdentifier(hinhThucGiaoHang) as number}`,
      hinhThucGiaoHang,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHinhThucGiaoHang>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHinhThucGiaoHang[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHinhThucGiaoHangToCollectionIfMissing(
    hinhThucGiaoHangCollection: IHinhThucGiaoHang[],
    ...hinhThucGiaoHangsToCheck: (IHinhThucGiaoHang | null | undefined)[]
  ): IHinhThucGiaoHang[] {
    const hinhThucGiaoHangs: IHinhThucGiaoHang[] = hinhThucGiaoHangsToCheck.filter(isPresent);
    if (hinhThucGiaoHangs.length > 0) {
      const hinhThucGiaoHangCollectionIdentifiers = hinhThucGiaoHangCollection.map(
        hinhThucGiaoHangItem => getHinhThucGiaoHangIdentifier(hinhThucGiaoHangItem)!
      );
      const hinhThucGiaoHangsToAdd = hinhThucGiaoHangs.filter(hinhThucGiaoHangItem => {
        const hinhThucGiaoHangIdentifier = getHinhThucGiaoHangIdentifier(hinhThucGiaoHangItem);
        if (hinhThucGiaoHangIdentifier == null || hinhThucGiaoHangCollectionIdentifiers.includes(hinhThucGiaoHangIdentifier)) {
          return false;
        }
        hinhThucGiaoHangCollectionIdentifiers.push(hinhThucGiaoHangIdentifier);
        return true;
      });
      return [...hinhThucGiaoHangsToAdd, ...hinhThucGiaoHangCollection];
    }
    return hinhThucGiaoHangCollection;
  }
}
