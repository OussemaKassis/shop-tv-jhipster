import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlanOptionsComponent } from '../list/plan-options.component';
import { PlanOptionsDetailComponent } from '../detail/plan-options-detail.component';
import { PlanOptionsUpdateComponent } from '../update/plan-options-update.component';
import { PlanOptionsRoutingResolveService } from './plan-options-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const planOptionsRoute: Routes = [
  {
    path: '',
    component: PlanOptionsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlanOptionsDetailComponent,
    resolve: {
      planOptions: PlanOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlanOptionsUpdateComponent,
    resolve: {
      planOptions: PlanOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlanOptionsUpdateComponent,
    resolve: {
      planOptions: PlanOptionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(planOptionsRoute)],
  exports: [RouterModule],
})
export class PlanOptionsRoutingModule {}
