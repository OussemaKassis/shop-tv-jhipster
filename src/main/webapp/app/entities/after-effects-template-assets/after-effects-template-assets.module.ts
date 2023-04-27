import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AfterEffectsTemplateAssetsComponent } from './list/after-effects-template-assets.component';
import { AfterEffectsTemplateAssetsDetailComponent } from './detail/after-effects-template-assets-detail.component';
import { AfterEffectsTemplateAssetsUpdateComponent } from './update/after-effects-template-assets-update.component';
import { AfterEffectsTemplateAssetsDeleteDialogComponent } from './delete/after-effects-template-assets-delete-dialog.component';
import { AfterEffectsTemplateAssetsRoutingModule } from './route/after-effects-template-assets-routing.module';

@NgModule({
  imports: [SharedModule, AfterEffectsTemplateAssetsRoutingModule],
  declarations: [
    AfterEffectsTemplateAssetsComponent,
    AfterEffectsTemplateAssetsDetailComponent,
    AfterEffectsTemplateAssetsUpdateComponent,
    AfterEffectsTemplateAssetsDeleteDialogComponent,
  ],
})
export class AfterEffectsTemplateAssetsModule {}
