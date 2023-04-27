import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAfterEffectsTemplate } from '../after-effects-template.model';
import { AfterEffectsTemplateService } from '../service/after-effects-template.service';

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateRoutingResolveService implements Resolve<IAfterEffectsTemplate | null> {
  constructor(protected service: AfterEffectsTemplateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAfterEffectsTemplate | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((afterEffectsTemplate: HttpResponse<IAfterEffectsTemplate>) => {
          if (afterEffectsTemplate.body) {
            return of(afterEffectsTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
