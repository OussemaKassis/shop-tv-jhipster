import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlanOptionsComponent } from './list/plan-options.component';
import { PlanOptionsDetailComponent } from './detail/plan-options-detail.component';
import { PlanOptionsUpdateComponent } from './update/plan-options-update.component';
import { PlanOptionsDeleteDialogComponent } from './delete/plan-options-delete-dialog.component';
import { PlanOptionsRoutingModule } from './route/plan-options-routing.module';

@NgModule({
  imports: [SharedModule, PlanOptionsRoutingModule],
  declarations: [PlanOptionsComponent, PlanOptionsDetailComponent, PlanOptionsUpdateComponent, PlanOptionsDeleteDialogComponent],
})
export class PlanOptionsModule {}
