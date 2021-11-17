import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKhachHang, getKhachHangIdentifier } from '../khach-hang.model';

export type EntityResponseType = HttpResponse<IKhachHang>;
export type EntityArrayResponseType = HttpResponse<IKhachHang[]>;

@Injectable({ providedIn: 'root' })
export class KhachHangService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/khach-hangs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(khachHang: IKhachHang): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khachHang);
    return this.http
      .post<IKhachHang>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(khachHang: IKhachHang): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khachHang);
    return this.http
      .put<IKhachHang>(`${this.resourceUrl}/${getKhachHangIdentifier(khachHang) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(khachHang: IKhachHang): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khachHang);
    return this.http
      .patch<IKhachHang>(`${this.resourceUrl}/${getKhachHangIdentifier(khachHang) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IKhachHang>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IKhachHang[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addKhachHangToCollectionIfMissing(
    khachHangCollection: IKhachHang[],
    ...khachHangsToCheck: (IKhachHang | null | undefined)[]
  ): IKhachHang[] {
    const khachHangs: IKhachHang[] = khachHangsToCheck.filter(isPresent);
    if (khachHangs.length > 0) {
      const khachHangCollectionIdentifiers = khachHangCollection.map(khachHangItem => getKhachHangIdentifier(khachHangItem)!);
      const khachHangsToAdd = khachHangs.filter(khachHangItem => {
        const khachHangIdentifier = getKhachHangIdentifier(khachHangItem);
        if (khachHangIdentifier == null || khachHangCollectionIdentifiers.includes(khachHangIdentifier)) {
          return false;
        }
        khachHangCollectionIdentifiers.push(khachHangIdentifier);
        return true;
      });
      return [...khachHangsToAdd, ...khachHangCollection];
    }
    return khachHangCollection;
  }

  protected convertDateFromClient(khachHang: IKhachHang): IKhachHang {
    return Object.assign({}, khachHang, {
      ngaySinh: khachHang.ngaySinh?.isValid() ? khachHang.ngaySinh.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngaySinh = res.body.ngaySinh ? dayjs(res.body.ngaySinh) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((khachHang: IKhachHang) => {
        khachHang.ngaySinh = khachHang.ngaySinh ? dayjs(khachHang.ngaySinh) : undefined;
      });
    }
    return res;
  }
}
