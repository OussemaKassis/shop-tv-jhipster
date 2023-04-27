import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateAssetsRoutingResolveService implements Resolve<IAfterEffectsTemplateAssets | null> {
  constructor(protected service: AfterEffectsTemplateAssetsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAfterEffectsTemplateAssets | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((afterEffectsTemplateAssets: HttpResponse<IAfterEffectsTemplateAssets>) => {
          if (afterEffectsTemplateAssets.body) {
            return of(afterEffectsTemplateAssets.body);
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
