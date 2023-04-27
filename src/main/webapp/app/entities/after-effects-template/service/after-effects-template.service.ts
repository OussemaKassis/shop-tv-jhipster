import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAfterEffectsTemplate, NewAfterEffectsTemplate } from '../after-effects-template.model';

export type PartialUpdateAfterEffectsTemplate = Partial<IAfterEffectsTemplate> & Pick<IAfterEffectsTemplate, 'id'>;

type RestOf<T extends IAfterEffectsTemplate | NewAfterEffectsTemplate> = Omit<T, 'templateRating'> & {
  templateRating?: string | null;
};

export type RestAfterEffectsTemplate = RestOf<IAfterEffectsTemplate>;

export type NewRestAfterEffectsTemplate = RestOf<NewAfterEffectsTemplate>;

export type PartialUpdateRestAfterEffectsTemplate = RestOf<PartialUpdateAfterEffectsTemplate>;

export type EntityResponseType = HttpResponse<IAfterEffectsTemplate>;
export type EntityArrayResponseType = HttpResponse<IAfterEffectsTemplate[]>;

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/after-effects-templates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(afterEffectsTemplate: NewAfterEffectsTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(afterEffectsTemplate);
    return this.http
      .post<RestAfterEffectsTemplate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(afterEffectsTemplate: IAfterEffectsTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(afterEffectsTemplate);
    return this.http
      .put<RestAfterEffectsTemplate>(`${this.resourceUrl}/${this.getAfterEffectsTemplateIdentifier(afterEffectsTemplate)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(afterEffectsTemplate: PartialUpdateAfterEffectsTemplate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(afterEffectsTemplate);
    return this.http
      .patch<RestAfterEffectsTemplate>(`${this.resourceUrl}/${this.getAfterEffectsTemplateIdentifier(afterEffectsTemplate)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAfterEffectsTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAfterEffectsTemplate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAfterEffectsTemplateIdentifier(afterEffectsTemplate: Pick<IAfterEffectsTemplate, 'id'>): number {
    return afterEffectsTemplate.id;
  }

  compareAfterEffectsTemplate(o1: Pick<IAfterEffectsTemplate, 'id'> | null, o2: Pick<IAfterEffectsTemplate, 'id'> | null): boolean {
    return o1 && o2 ? this.getAfterEffectsTemplateIdentifier(o1) === this.getAfterEffectsTemplateIdentifier(o2) : o1 === o2;
  }

  addAfterEffectsTemplateToCollectionIfMissing<Type extends Pick<IAfterEffectsTemplate, 'id'>>(
    afterEffectsTemplateCollection: Type[],
    ...afterEffectsTemplatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const afterEffectsTemplates: Type[] = afterEffectsTemplatesToCheck.filter(isPresent);
    if (afterEffectsTemplates.length > 0) {
      const afterEffectsTemplateCollectionIdentifiers = afterEffectsTemplateCollection.map(
        afterEffectsTemplateItem => this.getAfterEffectsTemplateIdentifier(afterEffectsTemplateItem)!
      );
      const afterEffectsTemplatesToAdd = afterEffectsTemplates.filter(afterEffectsTemplateItem => {
        const afterEffectsTemplateIdentifier = this.getAfterEffectsTemplateIdentifier(afterEffectsTemplateItem);
        if (afterEffectsTemplateCollectionIdentifiers.includes(afterEffectsTemplateIdentifier)) {
          return false;
        }
        afterEffectsTemplateCollectionIdentifiers.push(afterEffectsTemplateIdentifier);
        return true;
      });
      return [...afterEffectsTemplatesToAdd, ...afterEffectsTemplateCollection];
    }
    return afterEffectsTemplateCollection;
  }

  protected convertDateFromClient<T extends IAfterEffectsTemplate | NewAfterEffectsTemplate | PartialUpdateAfterEffectsTemplate>(
    afterEffectsTemplate: T
  ): RestOf<T> {
    return {
      ...afterEffectsTemplate,
      templateRating: afterEffectsTemplate.templateRating?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAfterEffectsTemplate: RestAfterEffectsTemplate): IAfterEffectsTemplate {
    return {
      ...restAfterEffectsTemplate,
      templateRating: restAfterEffectsTemplate.templateRating ? dayjs(restAfterEffectsTemplate.templateRating) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAfterEffectsTemplate>): HttpResponse<IAfterEffectsTemplate> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAfterEffectsTemplate[]>): HttpResponse<IAfterEffectsTemplate[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
