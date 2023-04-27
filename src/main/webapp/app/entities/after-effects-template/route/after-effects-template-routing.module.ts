import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AfterEffectsTemplateComponent } from '../list/after-effects-template.component';
import { AfterEffectsTemplateDetailComponent } from '../detail/after-effects-template-detail.component';
import { AfterEffectsTemplateUpdateComponent } from '../update/after-effects-template-update.component';
import { AfterEffectsTemplateRoutingResolveService } from './after-effects-template-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const afterEffectsTemplateRoute: Routes = [
  {
    path: '',
    component: AfterEffectsTemplateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AfterEffectsTemplateDetailComponent,
    resolve: {
      afterEffectsTemplate: AfterEffectsTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AfterEffectsTemplateUpdateComponent,
    resolve: {
      afterEffectsTemplate: AfterEffectsTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AfterEffectsTemplateUpdateComponent,
    resolve: {
      afterEffectsTemplate: AfterEffectsTemplateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(afterEffectsTemplateRoute)],
  exports: [RouterModule],
})
export class AfterEffectsTemplateRoutingModule {}
