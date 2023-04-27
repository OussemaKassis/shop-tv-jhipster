import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PlanOptionsFormService, PlanOptionsFormGroup } from './plan-options-form.service';
import { IPlanOptions } from '../plan-options.model';
import { PlanOptionsService } from '../service/plan-options.service';

@Component({
  selector: 'jhi-plan-options-update',
  templateUrl: './plan-options-update.component.html',
})
export class PlanOptionsUpdateComponent implements OnInit {
  isSaving = false;
  planOptions: IPlanOptions | null = null;

  editForm: PlanOptionsFormGroup = this.planOptionsFormService.createPlanOptionsFormGroup();

  constructor(
    protected planOptionsService: PlanOptionsService,
    protected planOptionsFormService: PlanOptionsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planOptions }) => {
      this.planOptions = planOptions;
      if (planOptions) {
        this.updateForm(planOptions);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planOptions = this.planOptionsFormService.getPlanOptions(this.editForm);
    if (planOptions.id !== null) {
      this.subscribeToSaveResponse(this.planOptionsService.update(planOptions));
    } else {
      this.subscribeToSaveResponse(this.planOptionsService.create(planOptions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanOptions>>): void {
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

  protected updateForm(planOptions: IPlanOptions): void {
    this.planOptions = planOptions;
    this.planOptionsFormService.resetForm(this.editForm, planOptions);
  }
}
