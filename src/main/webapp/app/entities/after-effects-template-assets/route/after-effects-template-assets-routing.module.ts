import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AfterEffectsTemplateAssetsComponent } from '../list/after-effects-template-assets.component';
import { AfterEffectsTemplateAssetsDetailComponent } from '../detail/after-effects-template-assets-detail.component';
import { AfterEffectsTemplateAssetsUpdateComponent } from '../update/after-effects-template-assets-update.component';
import { AfterEffectsTemplateAssetsRoutingResolveService } from './after-effects-template-assets-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const afterEffectsTemplateAssetsRoute: Routes = [
  {
    path: '',
    component: AfterEffectsTemplateAssetsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AfterEffectsTemplateAssetsDetailComponent,
    resolve: {
      afterEffectsTemplateAssets: AfterEffectsTemplateAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AfterEffectsTemplateAssetsUpdateComponent,
    resolve: {
      afterEffectsTemplateAssets: AfterEffectsTemplateAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AfterEffectsTemplateAssetsUpdateComponent,
    resolve: {
      afterEffectsTemplateAssets: AfterEffectsTemplateAssetsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(afterEffectsTemplateAssetsRoute)],
  exports: [RouterModule],
})
export class AfterEffectsTemplateAssetsRoutingModule {}
