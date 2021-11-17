import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IXaPhuong, getXaPhuongIdentifier } from '../xa-phuong.model';

export type EntityResponseType = HttpResponse<IXaPhuong>;
export type EntityArrayResponseType = HttpResponse<IXaPhuong[]>;

@Injectable({ providedIn: 'root' })
export class XaPhuongService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/xa-phuongs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(xaPhuong: IXaPhuong): Observable<EntityResponseType> {
    return this.http.post<IXaPhuong>(this.resourceUrl, xaPhuong, { observe: 'response' });
  }

  update(xaPhuong: IXaPhuong): Observable<EntityResponseType> {
    return this.http.put<IXaPhuong>(`${this.resourceUrl}/${getXaPhuongIdentifier(xaPhuong) as number}`, xaPhuong, { observe: 'response' });
  }

  partialUpdate(xaPhuong: IXaPhuong): Observable<EntityResponseType> {
    return this.http.patch<IXaPhuong>(`${this.resourceUrl}/${getXaPhuongIdentifier(xaPhuong) as number}`, xaPhuong, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IXaPhuong>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IXaPhuong[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addXaPhuongToCollectionIfMissing(xaPhuongCollection: IXaPhuong[], ...xaPhuongsToCheck: (IXaPhuong | null | undefined)[]): IXaPhuong[] {
    const xaPhuongs: IXaPhuong[] = xaPhuongsToCheck.filter(isPresent);
    if (xaPhuongs.length > 0) {
      const xaPhuongCollectionIdentifiers = xaPhuongCollection.map(xaPhuongItem => getXaPhuongIdentifier(xaPhuongItem)!);
      const xaPhuongsToAdd = xaPhuongs.filter(xaPhuongItem => {
        const xaPhuongIdentifier = getXaPhuongIdentifier(xaPhuongItem);
        if (xaPhuongIdentifier == null || xaPhuongCollectionIdentifiers.includes(xaPhuongIdentifier)) {
          return false;
        }
        xaPhuongCollectionIdentifiers.push(xaPhuongIdentifier);
        return true;
      });
      return [...xaPhuongsToAdd, ...xaPhuongCollection];
    }
    return xaPhuongCollection;
  }
}
