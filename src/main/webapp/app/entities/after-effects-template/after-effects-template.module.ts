import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AfterEffectsTemplateComponent } from './list/after-effects-template.component';
import { AfterEffectsTemplateDetailComponent } from './detail/after-effects-template-detail.component';
import { AfterEffectsTemplateUpdateComponent } from './update/after-effects-template-update.component';
import { AfterEffectsTemplateDeleteDialogComponent } from './delete/after-effects-template-delete-dialog.component';
import { AfterEffectsTemplateRoutingModule } from './route/after-effects-template-routing.module';

@NgModule({
  imports: [SharedModule, AfterEffectsTemplateRoutingModule],
  declarations: [
    AfterEffectsTemplateComponent,
    AfterEffectsTemplateDetailComponent,
    AfterEffectsTemplateUpdateComponent,
    AfterEffectsTemplateDeleteDialogComponent,
  ],
})
export class AfterEffectsTemplateModule {}
