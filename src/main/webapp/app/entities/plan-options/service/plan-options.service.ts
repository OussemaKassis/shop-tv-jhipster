import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanOptions, NewPlanOptions } from '../plan-options.model';

export type PartialUpdatePlanOptions = Partial<IPlanOptions> & Pick<IPlanOptions, 'id'>;

export type EntityResponseType = HttpResponse<IPlanOptions>;
export type EntityArrayResponseType = HttpResponse<IPlanOptions[]>;

@Injectable({ providedIn: 'root' })
export class PlanOptionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plan-options');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(planOptions: NewPlanOptions): Observable<EntityResponseType> {
    return this.http.post<IPlanOptions>(this.resourceUrl, planOptions, { observe: 'response' });
  }

  update(planOptions: IPlanOptions): Observable<EntityResponseType> {
    return this.http.put<IPlanOptions>(`${this.resourceUrl}/${this.getPlanOptionsIdentifier(planOptions)}`, planOptions, {
      observe: 'response',
    });
  }

  partialUpdate(planOptions: PartialUpdatePlanOptions): Observable<EntityResponseType> {
    return this.http.patch<IPlanOptions>(`${this.resourceUrl}/${this.getPlanOptionsIdentifier(planOptions)}`, planOptions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanOptions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanOptionsIdentifier(planOptions: Pick<IPlanOptions, 'id'>): number {
    return planOptions.id;
  }

  comparePlanOptions(o1: Pick<IPlanOptions, 'id'> | null, o2: Pick<IPlanOptions, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanOptionsIdentifier(o1) === this.getPlanOptionsIdentifier(o2) : o1 === o2;
  }

  addPlanOptionsToCollectionIfMissing<Type extends Pick<IPlanOptions, 'id'>>(
    planOptionsCollection: Type[],
    ...planOptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planOptions: Type[] = planOptionsToCheck.filter(isPresent);
    if (planOptions.length > 0) {
      const planOptionsCollectionIdentifiers = planOptionsCollection.map(
        planOptionsItem => this.getPlanOptionsIdentifier(planOptionsItem)!
      );
      const planOptionsToAdd = planOptions.filter(planOptionsItem => {
        const planOptionsIdentifier = this.getPlanOptionsIdentifier(planOptionsItem);
        if (planOptionsCollectionIdentifiers.includes(planOptionsIdentifier)) {
          return false;
        }
        planOptionsCollectionIdentifiers.push(planOptionsIdentifier);
        return true;
      });
      return [...planOptionsToAdd, ...planOptionsCollection];
    }
    return planOptionsCollection;
  }
}
