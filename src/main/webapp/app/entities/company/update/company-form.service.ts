import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICompany, NewCompany } from '../company.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompany for edit and NewCompanyFormGroupInput for create.
 */
type CompanyFormGroupInput = ICompany | PartialWithRequiredKeyOf<NewCompany>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICompany | NewCompany> = Omit<T, 'companyCreationDate'> & {
  companyCreationDate?: string | null;
};

type CompanyFormRawValue = FormValueOf<ICompany>;

type NewCompanyFormRawValue = FormValueOf<NewCompany>;

type CompanyFormDefaults = Pick<NewCompany, 'id' | 'companyCreationDate'>;

type CompanyFormGroupContent = {
  id: FormControl<CompanyFormRawValue['id'] | NewCompany['id']>;
  companyName: FormControl<CompanyFormRawValue['companyName']>;
  companyType: FormControl<CompanyFormRawValue['companyType']>;
  companyDescription: FormControl<CompanyFormRawValue['companyDescription']>;
  companyPicture: FormControl<CompanyFormRawValue['companyPicture']>;
  companyCreationDate: FormControl<CompanyFormRawValue['companyCreationDate']>;
  companyLocationAddress: FormControl<CompanyFormRawValue['companyLocationAddress']>;
  companyActivityDomaine: FormControl<CompanyFormRawValue['companyActivityDomaine']>;
};

export type CompanyFormGroup = FormGroup<CompanyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyFormService {
  createCompanyFormGroup(company: CompanyFormGroupInput = { id: null }): CompanyFormGroup {
    const companyRawValue = this.convertCompanyToCompanyRawValue({
      ...this.getFormDefaults(),
      ...company,
    });
    return new FormGroup<CompanyFormGroupContent>({
      id: new FormControl(
        { value: companyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      companyName: new FormControl(companyRawValue.companyName),
      companyType: new FormControl(companyRawValue.companyType),
      companyDescription: new FormControl(companyRawValue.companyDescription),
      companyPicture: new FormControl(companyRawValue.companyPicture),
      companyCreationDate: new FormControl(companyRawValue.companyCreationDate),
      companyLocationAddress: new FormControl(companyRawValue.companyLocationAddress),
      companyActivityDomaine: new FormControl(companyRawValue.companyActivityDomaine),
    });
  }

  getCompany(form: CompanyFormGroup): ICompany | NewCompany {
    return this.convertCompanyRawValueToCompany(form.getRawValue() as CompanyFormRawValue | NewCompanyFormRawValue);
  }

  resetForm(form: CompanyFormGroup, company: CompanyFormGroupInput): void {
    const companyRawValue = this.convertCompanyToCompanyRawValue({ ...this.getFormDefaults(), ...company });
    form.reset(
      {
        ...companyRawValue,
        id: { value: companyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      companyCreationDate: currentTime,
    };
  }

  private convertCompanyRawValueToCompany(rawCompany: CompanyFormRawValue | NewCompanyFormRawValue): ICompany | NewCompany {
    return {
      ...rawCompany,
      companyCreationDate: dayjs(rawCompany.companyCreationDate, DATE_TIME_FORMAT),
    };
  }

  private convertCompanyToCompanyRawValue(
    company: ICompany | (Partial<NewCompany> & CompanyFormDefaults)
  ): CompanyFormRawValue | PartialWithRequiredKeyOf<NewCompanyFormRawValue> {
    return {
      ...company,
      companyCreationDate: company.companyCreationDate ? company.companyCreationDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
