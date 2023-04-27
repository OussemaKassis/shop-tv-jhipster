import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAfterEffectsTemplate } from '../after-effects-template.model';
import { AfterEffectsTemplateService } from '../service/after-effects-template.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './after-effects-template-delete-dialog.component.html',
})
export class AfterEffectsTemplateDeleteDialogComponent {
  afterEffectsTemplate?: IAfterEffectsTemplate;

  constructor(protected afterEffectsTemplateService: AfterEffectsTemplateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.afterEffectsTemplateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
