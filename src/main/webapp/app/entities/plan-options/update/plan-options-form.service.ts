import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanOptions, NewPlanOptions } from '../plan-options.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanOptions for edit and NewPlanOptionsFormGroupInput for create.
 */
type PlanOptionsFormGroupInput = IPlanOptions | PartialWithRequiredKeyOf<NewPlanOptions>;

type PlanOptionsFormDefaults = Pick<NewPlanOptions, 'id' | 'emojis'>;

type PlanOptionsFormGroupContent = {
  id: FormControl<IPlanOptions['id'] | NewPlanOptions['id']>;
  aeTemplateLimit: FormControl<IPlanOptions['aeTemplateLimit']>;
  videoSubmittionLimit: FormControl<IPlanOptions['videoSubmittionLimit']>;
  emojis: FormControl<IPlanOptions['emojis']>;
  storage: FormControl<IPlanOptions['storage']>;
};

export type PlanOptionsFormGroup = FormGroup<PlanOptionsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanOptionsFormService {
  createPlanOptionsFormGroup(planOptions: PlanOptionsFormGroupInput = { id: null }): PlanOptionsFormGroup {
    const planOptionsRawValue = {
      ...this.getFormDefaults(),
      ...planOptions,
    };
    return new FormGroup<PlanOptionsFormGroupContent>({
      id: new FormControl(
        { value: planOptionsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      aeTemplateLimit: new FormControl(planOptionsRawValue.aeTemplateLimit),
      videoSubmittionLimit: new FormControl(planOptionsRawValue.videoSubmittionLimit),
      emojis: new FormControl(planOptionsRawValue.emojis),
      storage: new FormControl(planOptionsRawValue.storage),
    });
  }

  getPlanOptions(form: PlanOptionsFormGroup): IPlanOptions | NewPlanOptions {
    return form.getRawValue() as IPlanOptions | NewPlanOptions;
  }

  resetForm(form: PlanOptionsFormGroup, planOptions: PlanOptionsFormGroupInput): void {
    const planOptionsRawValue = { ...this.getFormDefaults(), ...planOptions };
    form.reset(
      {
        ...planOptionsRawValue,
        id: { value: planOptionsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlanOptionsFormDefaults {
    return {
      id: null,
      emojis: false,
    };
  }
}
