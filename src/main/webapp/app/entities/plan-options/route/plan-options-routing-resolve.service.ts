import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanOptions } from '../plan-options.model';
import { PlanOptionsService } from '../service/plan-options.service';

@Injectable({ providedIn: 'root' })
export class PlanOptionsRoutingResolveService implements Resolve<IPlanOptions | null> {
  constructor(protected service: PlanOptionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanOptions | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((planOptions: HttpResponse<IPlanOptions>) => {
          if (planOptions.body) {
            return of(planOptions.body);
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
