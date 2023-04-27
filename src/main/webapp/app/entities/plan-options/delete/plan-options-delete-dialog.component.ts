import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanOptions } from '../plan-options.model';
import { PlanOptionsService } from '../service/plan-options.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './plan-options-delete-dialog.component.html',
})
export class PlanOptionsDeleteDialogComponent {
  planOptions?: IPlanOptions;

  constructor(protected planOptionsService: PlanOptionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planOptionsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
