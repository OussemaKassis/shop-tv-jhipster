import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAfterEffectsTemplateAssets, NewAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAfterEffectsTemplateAssets for edit and NewAfterEffectsTemplateAssetsFormGroupInput for create.
 */
type AfterEffectsTemplateAssetsFormGroupInput = IAfterEffectsTemplateAssets | PartialWithRequiredKeyOf<NewAfterEffectsTemplateAssets>;

type AfterEffectsTemplateAssetsFormDefaults = Pick<NewAfterEffectsTemplateAssets, 'id'>;

type AfterEffectsTemplateAssetsFormGroupContent = {
  id: FormControl<IAfterEffectsTemplateAssets['id'] | NewAfterEffectsTemplateAssets['id']>;
  assetName: FormControl<IAfterEffectsTemplateAssets['assetName']>;
  assetType: FormControl<IAfterEffectsTemplateAssets['assetType']>;
};

export type AfterEffectsTemplateAssetsFormGroup = FormGroup<AfterEffectsTemplateAssetsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateAssetsFormService {
  createAfterEffectsTemplateAssetsFormGroup(
    afterEffectsTemplateAssets: AfterEffectsTemplateAssetsFormGroupInput = { id: null }
  ): AfterEffectsTemplateAssetsFormGroup {
    const afterEffectsTemplateAssetsRawValue = {
      ...this.getFormDefaults(),
      ...afterEffectsTemplateAssets,
    };
    return new FormGroup<AfterEffectsTemplateAssetsFormGroupContent>({
      id: new FormControl(
        { value: afterEffectsTemplateAssetsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      assetName: new FormControl(afterEffectsTemplateAssetsRawValue.assetName),
      assetType: new FormControl(afterEffectsTemplateAssetsRawValue.assetType),
    });
  }

  getAfterEffectsTemplateAssets(form: AfterEffectsTemplateAssetsFormGroup): IAfterEffectsTemplateAssets | NewAfterEffectsTemplateAssets {
    return form.getRawValue() as IAfterEffectsTemplateAssets | NewAfterEffectsTemplateAssets;
  }

  resetForm(form: AfterEffectsTemplateAssetsFormGroup, afterEffectsTemplateAssets: AfterEffectsTemplateAssetsFormGroupInput): void {
    const afterEffectsTemplateAssetsRawValue = { ...this.getFormDefaults(), ...afterEffectsTemplateAssets };
    form.reset(
      {
        ...afterEffectsTemplateAssetsRawValue,
        id: { value: afterEffectsTemplateAssetsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AfterEffectsTemplateAssetsFormDefaults {
    return {
      id: null,
    };
  }
}
