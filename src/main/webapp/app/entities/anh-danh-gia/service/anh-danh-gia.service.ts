import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnhDanhGia, getAnhDanhGiaIdentifier } from '../anh-danh-gia.model';

export type EntityResponseType = HttpResponse<IAnhDanhGia>;
export type EntityArrayResponseType = HttpResponse<IAnhDanhGia[]>;

@Injectable({ providedIn: 'root' })
export class AnhDanhGiaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anh-danh-gias');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(anhDanhGia: IAnhDanhGia): Observable<EntityResponseType> {
    return this.http.post<IAnhDanhGia>(this.resourceUrl, anhDanhGia, { observe: 'response' });
  }

  update(anhDanhGia: IAnhDanhGia): Observable<EntityResponseType> {
    return this.http.put<IAnhDanhGia>(`${this.resourceUrl}/${getAnhDanhGiaIdentifier(anhDanhGia) as number}`, anhDanhGia, {
      observe: 'response',
    });
  }

  partialUpdate(anhDanhGia: IAnhDanhGia): Observable<EntityResponseType> {
    return this.http.patch<IAnhDanhGia>(`${this.resourceUrl}/${getAnhDanhGiaIdentifier(anhDanhGia) as number}`, anhDanhGia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnhDanhGia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnhDanhGia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnhDanhGiaToCollectionIfMissing(
    anhDanhGiaCollection: IAnhDanhGia[],
    ...anhDanhGiasToCheck: (IAnhDanhGia | null | undefined)[]
  ): IAnhDanhGia[] {
    const anhDanhGias: IAnhDanhGia[] = anhDanhGiasToCheck.filter(isPresent);
    if (anhDanhGias.length > 0) {
      const anhDanhGiaCollectionIdentifiers = anhDanhGiaCollection.map(anhDanhGiaItem => getAnhDanhGiaIdentifier(anhDanhGiaItem)!);
      const anhDanhGiasToAdd = anhDanhGias.filter(anhDanhGiaItem => {
        const anhDanhGiaIdentifier = getAnhDanhGiaIdentifier(anhDanhGiaItem);
        if (anhDanhGiaIdentifier == null || anhDanhGiaCollectionIdentifiers.includes(anhDanhGiaIdentifier)) {
          return false;
        }
        anhDanhGiaCollectionIdentifiers.push(anhDanhGiaIdentifier);
        return true;
      });
      return [...anhDanhGiasToAdd, ...anhDanhGiaCollection];
    }
    return anhDanhGiaCollection;
  }
}
