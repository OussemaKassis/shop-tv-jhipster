import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAfterEffectsTemplate, NewAfterEffectsTemplate } from '../after-effects-template.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAfterEffectsTemplate for edit and NewAfterEffectsTemplateFormGroupInput for create.
 */
type AfterEffectsTemplateFormGroupInput = IAfterEffectsTemplate | PartialWithRequiredKeyOf<NewAfterEffectsTemplate>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAfterEffectsTemplate | NewAfterEffectsTemplate> = Omit<T, 'templateRating'> & {
  templateRating?: string | null;
};

type AfterEffectsTemplateFormRawValue = FormValueOf<IAfterEffectsTemplate>;

type NewAfterEffectsTemplateFormRawValue = FormValueOf<NewAfterEffectsTemplate>;

type AfterEffectsTemplateFormDefaults = Pick<NewAfterEffectsTemplate, 'id' | 'templateRating' | 'templateActive'>;

type AfterEffectsTemplateFormGroupContent = {
  id: FormControl<AfterEffectsTemplateFormRawValue['id'] | NewAfterEffectsTemplate['id']>;
  templateName: FormControl<AfterEffectsTemplateFormRawValue['templateName']>;
  templateDuration: FormControl<AfterEffectsTemplateFormRawValue['templateDuration']>;
  templateDescription: FormControl<AfterEffectsTemplateFormRawValue['templateDescription']>;
  templateRating: FormControl<AfterEffectsTemplateFormRawValue['templateRating']>;
  templateActive: FormControl<AfterEffectsTemplateFormRawValue['templateActive']>;
  templateType: FormControl<AfterEffectsTemplateFormRawValue['templateType']>;
  templateExpectedSize: FormControl<AfterEffectsTemplateFormRawValue['templateExpectedSize']>;
  templateCount: FormControl<AfterEffectsTemplateFormRawValue['templateCount']>;
  templateVisibleFor: FormControl<AfterEffectsTemplateFormRawValue['templateVisibleFor']>;
  ratio: FormControl<AfterEffectsTemplateFormRawValue['ratio']>;
  previewUrl: FormControl<AfterEffectsTemplateFormRawValue['previewUrl']>;
  company: FormControl<AfterEffectsTemplateFormRawValue['company']>;
  category: FormControl<AfterEffectsTemplateFormRawValue['category']>;
  afterEffectsTemplateAssets: FormControl<AfterEffectsTemplateFormRawValue['afterEffectsTemplateAssets']>;
};

export type AfterEffectsTemplateFormGroup = FormGroup<AfterEffectsTemplateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AfterEffectsTemplateFormService {
  createAfterEffectsTemplateFormGroup(
    afterEffectsTemplate: AfterEffectsTemplateFormGroupInput = { id: null }
  ): AfterEffectsTemplateFormGroup {
    const afterEffectsTemplateRawValue = this.convertAfterEffectsTemplateToAfterEffectsTemplateRawValue({
      ...this.getFormDefaults(),
      ...afterEffectsTemplate,
    });
    return new FormGroup<AfterEffectsTemplateFormGroupContent>({
      id: new FormControl(
        { value: afterEffectsTemplateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      templateName: new FormControl(afterEffectsTemplateRawValue.templateName),
      templateDuration: new FormControl(afterEffectsTemplateRawValue.templateDuration),
      templateDescription: new FormControl(afterEffectsTemplateRawValue.templateDescription),
      templateRating: new FormControl(afterEffectsTemplateRawValue.templateRating),
      templateActive: new FormControl(afterEffectsTemplateRawValue.templateActive),
      templateType: new FormControl(afterEffectsTemplateRawValue.templateType),
      templateExpectedSize: new FormControl(afterEffectsTemplateRawValue.templateExpectedSize),
      templateCount: new FormControl(afterEffectsTemplateRawValue.templateCount),
      templateVisibleFor: new FormControl(afterEffectsTemplateRawValue.templateVisibleFor),
      ratio: new FormControl(afterEffectsTemplateRawValue.ratio),
      previewUrl: new FormControl(afterEffectsTemplateRawValue.previewUrl),
      company: new FormControl(afterEffectsTemplateRawValue.company),
      category: new FormControl(afterEffectsTemplateRawValue.category),
      afterEffectsTemplateAssets: new FormControl(afterEffectsTemplateRawValue.afterEffectsTemplateAssets),
    });
  }

  getAfterEffectsTemplate(form: AfterEffectsTemplateFormGroup): IAfterEffectsTemplate | NewAfterEffectsTemplate {
    return this.convertAfterEffectsTemplateRawValueToAfterEffectsTemplate(
      form.getRawValue() as AfterEffectsTemplateFormRawValue | NewAfterEffectsTemplateFormRawValue
    );
  }

  resetForm(form: AfterEffectsTemplateFormGroup, afterEffectsTemplate: AfterEffectsTemplateFormGroupInput): void {
    const afterEffectsTemplateRawValue = this.convertAfterEffectsTemplateToAfterEffectsTemplateRawValue({
      ...this.getFormDefaults(),
      ...afterEffectsTemplate,
    });
    form.reset(
      {
        ...afterEffectsTemplateRawValue,
        id: { value: afterEffectsTemplateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AfterEffectsTemplateFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      templateRating: currentTime,
      templateActive: false,
    };
  }

  private convertAfterEffectsTemplateRawValueToAfterEffectsTemplate(
    rawAfterEffectsTemplate: AfterEffectsTemplateFormRawValue | NewAfterEffectsTemplateFormRawValue
  ): IAfterEffectsTemplate | NewAfterEffectsTemplate {
    return {
      ...rawAfterEffectsTemplate,
      templateRating: dayjs(rawAfterEffectsTemplate.templateRating, DATE_TIME_FORMAT),
    };
  }

  private convertAfterEffectsTemplateToAfterEffectsTemplateRawValue(
    afterEffectsTemplate: IAfterEffectsTemplate | (Partial<NewAfterEffectsTemplate> & AfterEffectsTemplateFormDefaults)
  ): AfterEffectsTemplateFormRawValue | PartialWithRequiredKeyOf<NewAfterEffectsTemplateFormRawValue> {
    return {
      ...afterEffectsTemplate,
      templateRating: afterEffectsTemplate.templateRating ? afterEffectsTemplate.templateRating.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
