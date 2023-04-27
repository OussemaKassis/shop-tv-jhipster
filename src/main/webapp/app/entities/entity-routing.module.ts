import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'app-user',
        data: { pageTitle: 'shopTvJhipsterApp.appUser.home.title' },
        loadChildren: () => import('./app-user/app-user.module').then(m => m.AppUserModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'shopTvJhipsterApp.company.home.title' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'after-effects-template',
        data: { pageTitle: 'shopTvJhipsterApp.afterEffectsTemplate.home.title' },
        loadChildren: () => import('./after-effects-template/after-effects-template.module').then(m => m.AfterEffectsTemplateModule),
      },
      {
        path: 'after-effects-template-assets',
        data: { pageTitle: 'shopTvJhipsterApp.afterEffectsTemplateAssets.home.title' },
        loadChildren: () =>
          import('./after-effects-template-assets/after-effects-template-assets.module').then(m => m.AfterEffectsTemplateAssetsModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'shopTvJhipsterApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'video',
        data: { pageTitle: 'shopTvJhipsterApp.video.home.title' },
        loadChildren: () => import('./video/video.module').then(m => m.VideoModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'shopTvJhipsterApp.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'shopTvJhipsterApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'plan',
        data: { pageTitle: 'shopTvJhipsterApp.plan.home.title' },
        loadChildren: () => import('./plan/plan.module').then(m => m.PlanModule),
      },
      {
        path: 'plan-options',
        data: { pageTitle: 'shopTvJhipsterApp.planOptions.home.title' },
        loadChildren: () => import('./plan-options/plan-options.module').then(m => m.PlanOptionsModule),
      },
      {
        path: 'purchase-history',
        data: { pageTitle: 'shopTvJhipsterApp.purchaseHistory.home.title' },
        loadChildren: () => import('./purchase-history/purchase-history.module').then(m => m.PurchaseHistoryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
