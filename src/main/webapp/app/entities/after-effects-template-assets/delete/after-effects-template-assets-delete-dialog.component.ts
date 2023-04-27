import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './after-effects-template-assets-delete-dialog.component.html',
})
export class AfterEffectsTemplateAssetsDeleteDialogComponent {
  afterEffectsTemplateAssets?: IAfterEffectsTemplateAssets;

  constructor(protected afterEffectsTemplateAssetsService: AfterEffectsTemplateAssetsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.afterEffectsTemplateAssetsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
