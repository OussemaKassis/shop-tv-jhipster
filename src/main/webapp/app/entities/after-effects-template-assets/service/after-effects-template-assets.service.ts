import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAfterEffectsTemplateAssets, NewAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';

export type PartialUpdateAfterEffectsTemplateAssets = Partial<IAfterEffectsTemplateAssets> & Pick<IAfterEffectsTemplateAssets, 'id'>;

export type EntityResponseType = HttpResponse<IAfterEffectsTemplateAssets>;
export type EntityArrayResponseType = HttpResponse<IAfterEffectsTemplateAssets[]>;

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateAssetsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/after-effects-template-assets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(afterEffectsTemplateAssets: NewAfterEffectsTemplateAssets): Observable<EntityResponseType> {
    return this.http.post<IAfterEffectsTemplateAssets>(this.resourceUrl, afterEffectsTemplateAssets, { observe: 'response' });
  }

  update(afterEffectsTemplateAssets: IAfterEffectsTemplateAssets): Observable<EntityResponseType> {
    return this.http.put<IAfterEffectsTemplateAssets>(
      `${this.resourceUrl}/${this.getAfterEffectsTemplateAssetsIdentifier(afterEffectsTemplateAssets)}`,
      afterEffectsTemplateAssets,
      { observe: 'response' }
    );
  }

  partialUpdate(afterEffectsTemplateAssets: PartialUpdateAfterEffectsTemplateAssets): Observable<EntityResponseType> {
    return this.http.patch<IAfterEffectsTemplateAssets>(
      `${this.resourceUrl}/${this.getAfterEffectsTemplateAssetsIdentifier(afterEffectsTemplateAssets)}`,
      afterEffectsTemplateAssets,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAfterEffectsTemplateAssets>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAfterEffectsTemplateAssets[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAfterEffectsTemplateAssetsIdentifier(afterEffectsTemplateAssets: Pick<IAfterEffectsTemplateAssets, 'id'>): number {
    return afterEffectsTemplateAssets.id;
  }

  compareAfterEffectsTemplateAssets(
    o1: Pick<IAfterEffectsTemplateAssets, 'id'> | null,
    o2: Pick<IAfterEffectsTemplateAssets, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getAfterEffectsTemplateAssetsIdentifier(o1) === this.getAfterEffectsTemplateAssetsIdentifier(o2) : o1 === o2;
  }

  addAfterEffectsTemplateAssetsToCollectionIfMissing<Type extends Pick<IAfterEffectsTemplateAssets, 'id'>>(
    afterEffectsTemplateAssetsCollection: Type[],
    ...afterEffectsTemplateAssetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const afterEffectsTemplateAssets: Type[] = afterEffectsTemplateAssetsToCheck.filter(isPresent);
    if (afterEffectsTemplateAssets.length > 0) {
      const afterEffectsTemplateAssetsCollectionIdentifiers = afterEffectsTemplateAssetsCollection.map(
        afterEffectsTemplateAssetsItem => this.getAfterEffectsTemplateAssetsIdentifier(afterEffectsTemplateAssetsItem)!
      );
      const afterEffectsTemplateAssetsToAdd = afterEffectsTemplateAssets.filter(afterEffectsTemplateAssetsItem => {
        const afterEffectsTemplateAssetsIdentifier = this.getAfterEffectsTemplateAssetsIdentifier(afterEffectsTemplateAssetsItem);
        if (afterEffectsTemplateAssetsCollectionIdentifiers.includes(afterEffectsTemplateAssetsIdentifier)) {
          return false;
        }
        afterEffectsTemplateAssetsCollectionIdentifiers.push(afterEffectsTemplateAssetsIdentifier);
        return true;
      });
      return [...afterEffectsTemplateAssetsToAdd, ...afterEffectsTemplateAssetsCollection];
    }
    return afterEffectsTemplateAssetsCollection;
  }
}
