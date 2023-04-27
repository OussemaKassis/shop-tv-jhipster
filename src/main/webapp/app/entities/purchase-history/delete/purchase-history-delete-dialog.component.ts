import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPurchaseHistory } from '../purchase-history.model';
import { PurchaseHistoryService } from '../service/purchase-history.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './purchase-history-delete-dialog.component.html',
})
export class PurchaseHistoryDeleteDialogComponent {
  purchaseHistory?: IPurchaseHistory;

  constructor(protected purchaseHistoryService: PurchaseHistoryService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.purchaseHistoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
