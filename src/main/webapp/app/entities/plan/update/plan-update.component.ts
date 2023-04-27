import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlanFormService, PlanFormGroup } from './plan-form.service';
import { IPlan } from '../plan.model';
import { PlanService } from '../service/plan.service';
import { IPlanOptions } from 'app/entities/plan-options/plan-options.model';
import { PlanOptionsService } from 'app/entities/plan-options/service/plan-options.service';

@Component({
  selector: 'jhi-plan-update',
  templateUrl: './plan-update.component.html',
})
export class PlanUpdateComponent implements OnInit {
  isSaving = false;
  plan: IPlan | null = null;

  planOptionsSharedCollection: IPlanOptions[] = [];

  editForm: PlanFormGroup = this.planFormService.createPlanFormGroup();

  constructor(
    protected planService: PlanService,
    protected planFormService: PlanFormService,
    protected planOptionsService: PlanOptionsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePlanOptions = (o1: IPlanOptions | null, o2: IPlanOptions | null): boolean => this.planOptionsService.comparePlanOptions(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plan }) => {
      this.plan = plan;
      if (plan) {
        this.updateForm(plan);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plan = this.planFormService.getPlan(this.editForm);
    if (plan.id !== null) {
      this.subscribeToSaveResponse(this.planService.update(plan));
    } else {
      this.subscribeToSaveResponse(this.planService.create(plan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlan>>): void {
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

  protected updateForm(plan: IPlan): void {
    this.plan = plan;
    this.planFormService.resetForm(this.editForm, plan);

    this.planOptionsSharedCollection = this.planOptionsService.addPlanOptionsToCollectionIfMissing<IPlanOptions>(
      this.planOptionsSharedCollection,
      plan.planOptions
    );
  }

  protected loadRelationshipsOptions(): void {
    this.planOptionsService
      .query()
      .pipe(map((res: HttpResponse<IPlanOptions[]>) => res.body ?? []))
      .pipe(
        map((planOptions: IPlanOptions[]) =>
          this.planOptionsService.addPlanOptionsToCollectionIfMissing<IPlanOptions>(planOptions, this.plan?.planOptions)
        )
      )
      .subscribe((planOptions: IPlanOptions[]) => (this.planOptionsSharedCollection = planOptions));
  }
}
