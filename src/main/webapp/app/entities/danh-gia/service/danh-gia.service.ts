import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDanhGia, getDanhGiaIdentifier } from '../danh-gia.model';

export type EntityResponseType = HttpResponse<IDanhGia>;
export type EntityArrayResponseType = HttpResponse<IDanhGia[]>;

@Injectable({ providedIn: 'root' })
export class DanhGiaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/danh-gias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(danhGia: IDanhGia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(danhGia);
    return this.http
      .post<IDanhGia>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(danhGia: IDanhGia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(danhGia);
    return this.http
      .put<IDanhGia>(`${this.resourceUrl}/${getDanhGiaIdentifier(danhGia) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(danhGia: IDanhGia): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(danhGia);
    return this.http
      .patch<IDanhGia>(`${this.resourceUrl}/${getDanhGiaIdentifier(danhGia) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDanhGia>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDanhGia[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDanhGiaToCollectionIfMissing(danhGiaCollection: IDanhGia[], ...danhGiasToCheck: (IDanhGia | null | undefined)[]): IDanhGia[] {
    const danhGias: IDanhGia[] = danhGiasToCheck.filter(isPresent);
    if (danhGias.length > 0) {
      const danhGiaCollectionIdentifiers = danhGiaCollection.map(danhGiaItem => getDanhGiaIdentifier(danhGiaItem)!);
      const danhGiasToAdd = danhGias.filter(danhGiaItem => {
        const danhGiaIdentifier = getDanhGiaIdentifier(danhGiaItem);
        if (danhGiaIdentifier == null || danhGiaCollectionIdentifiers.includes(danhGiaIdentifier)) {
          return false;
        }
        danhGiaCollectionIdentifiers.push(danhGiaIdentifier);
        return true;
      });
      return [...danhGiasToAdd, ...danhGiaCollection];
    }
    return danhGiaCollection;
  }

  protected convertDateFromClient(danhGia: IDanhGia): IDanhGia {
    return Object.assign({}, danhGia, {
      ngaydanhgia: danhGia.ngaydanhgia?.isValid() ? danhGia.ngaydanhgia.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngaydanhgia = res.body.ngaydanhgia ? dayjs(res.body.ngaydanhgia) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((danhGia: IDanhGia) => {
        danhGia.ngaydanhgia = danhGia.ngaydanhgia ? dayjs(danhGia.ngaydanhgia) : undefined;
      });
    }
    return res;
  }
}
