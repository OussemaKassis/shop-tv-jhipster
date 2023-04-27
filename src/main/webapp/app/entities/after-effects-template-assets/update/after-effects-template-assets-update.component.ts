import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AfterEffectsTemplateAssetsFormService, AfterEffectsTemplateAssetsFormGroup } from './after-effects-template-assets-form.service';
import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';
import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';

@Component({
  selector: 'jhi-after-effects-template-assets-update',
  templateUrl: './after-effects-template-assets-update.component.html',
})
export class AfterEffectsTemplateAssetsUpdateComponent implements OnInit {
  isSaving = false;
  afterEffectsTemplateAssets: IAfterEffectsTemplateAssets | null = null;

  editForm: AfterEffectsTemplateAssetsFormGroup = this.afterEffectsTemplateAssetsFormService.createAfterEffectsTemplateAssetsFormGroup();

  constructor(
    protected afterEffectsTemplateAssetsService: AfterEffectsTemplateAssetsService,
    protected afterEffectsTemplateAssetsFormService: AfterEffectsTemplateAssetsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afterEffectsTemplateAssets }) => {
      this.afterEffectsTemplateAssets = afterEffectsTemplateAssets;
      if (afterEffectsTemplateAssets) {
        this.updateForm(afterEffectsTemplateAssets);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const afterEffectsTemplateAssets = this.afterEffectsTemplateAssetsFormService.getAfterEffectsTemplateAssets(this.editForm);
    if (afterEffectsTemplateAssets.id !== null) {
      this.subscribeToSaveResponse(this.afterEffectsTemplateAssetsService.update(afterEffectsTemplateAssets));
    } else {
      this.subscribeToSaveResponse(this.afterEffectsTemplateAssetsService.create(afterEffectsTemplateAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAfterEffectsTemplateAssets>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(afterEffectsTemplateAssets: IAfterEffectsTemplateAssets): void {
    this.afterEffectsTemplateAssets = afterEffectsTemplateAssets;
    this.afterEffectsTemplateAssetsFormService.resetForm(this.editForm, afterEffectsTemplateAssets);
  }
}
