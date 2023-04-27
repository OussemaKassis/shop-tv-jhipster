import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPurchaseHistory, NewPurchaseHistory } from '../purchase-history.model';

export type PartialUpdatePurchaseHistory = Partial<IPurchaseHistory> & Pick<IPurchaseHistory, 'id'>;

type RestOf<T extends IPurchaseHistory | NewPurchaseHistory> = Omit<T, 'purchaseDate'> & {
  purchaseDate?: string | null;
};

export type RestPurchaseHistory = RestOf<IPurchaseHistory>;

export type NewRestPurchaseHistory = RestOf<NewPurchaseHistory>;

export type PartialUpdateRestPurchaseHistory = RestOf<PartialUpdatePurchaseHistory>;

export type EntityResponseType = HttpResponse<IPurchaseHistory>;
export type EntityArrayResponseType = HttpResponse<IPurchaseHistory[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseHistoryService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/purchase-histories');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(purchaseHistory: NewPurchaseHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseHistory);
    return this.http
      .post<RestPurchaseHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(purchaseHistory: IPurchaseHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseHistory);
    return this.http
      .put<RestPurchaseHistory>(`${this.resourceUrl}/${this.getPurchaseHistoryIdentifier(purchaseHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(purchaseHistory: PartialUpdatePurchaseHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(purchaseHistory);
    return this.http
      .patch<RestPurchaseHistory>(`${this.resourceUrl}/${this.getPurchaseHistoryIdentifier(purchaseHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPurchaseHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPurchaseHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPurchaseHistoryIdentifier(purchaseHistory: Pick<IPurchaseHistory, 'id'>): number {
    return purchaseHistory.id;
  }

  comparePurchaseHistory(o1: Pick<IPurchaseHistory, 'id'> | null, o2: Pick<IPurchaseHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getPurchaseHistoryIdentifier(o1) === this.getPurchaseHistoryIdentifier(o2) : o1 === o2;
  }

  addPurchaseHistoryToCollectionIfMissing<Type extends Pick<IPurchaseHistory, 'id'>>(
    purchaseHistoryCollection: Type[],
    ...purchaseHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const purchaseHistories: Type[] = purchaseHistoriesToCheck.filter(isPresent);
    if (purchaseHistories.length > 0) {
      const purchaseHistoryCollectionIdentifiers = purchaseHistoryCollection.map(
        purchaseHistoryItem => this.getPurchaseHistoryIdentifier(purchaseHistoryItem)!
      );
      const purchaseHistoriesToAdd = purchaseHistories.filter(purchaseHistoryItem => {
        const purchaseHistoryIdentifier = this.getPurchaseHistoryIdentifier(purchaseHistoryItem);
        if (purchaseHistoryCollectionIdentifiers.includes(purchaseHistoryIdentifier)) {
          return false;
        }
        purchaseHistoryCollectionIdentifiers.push(purchaseHistoryIdentifier);
        return true;
      });
      return [...purchaseHistoriesToAdd, ...purchaseHistoryCollection];
    }
    return purchaseHistoryCollection;
  }

  protected convertDateFromClient<T extends IPurchaseHistory | NewPurchaseHistory | PartialUpdatePurchaseHistory>(
    purchaseHistory: T
  ): RestOf<T> {
    return {
      ...purchaseHistory,
      purchaseDate: purchaseHistory.purchaseDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPurchaseHistory: RestPurchaseHistory): IPurchaseHistory {
    return {
      ...restPurchaseHistory,
      purchaseDate: restPurchaseHistory.purchaseDate ? dayjs(restPurchaseHistory.purchaseDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPurchaseHistory>): HttpResponse<IPurchaseHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPurchaseHistory[]>): HttpResponse<IPurchaseHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
