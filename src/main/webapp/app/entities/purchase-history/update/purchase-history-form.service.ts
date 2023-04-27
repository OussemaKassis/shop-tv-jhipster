import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPurchaseHistory, NewPurchaseHistory } from '../purchase-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPurchaseHistory for edit and NewPurchaseHistoryFormGroupInput for create.
 */
type PurchaseHistoryFormGroupInput = IPurchaseHistory | PartialWithRequiredKeyOf<NewPurchaseHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPurchaseHistory | NewPurchaseHistory> = Omit<T, 'purchaseDate'> & {
  purchaseDate?: string | null;
};

type PurchaseHistoryFormRawValue = FormValueOf<IPurchaseHistory>;

type NewPurchaseHistoryFormRawValue = FormValueOf<NewPurchaseHistory>;

type PurchaseHistoryFormDefaults = Pick<NewPurchaseHistory, 'id' | 'purchaseDate'>;

type PurchaseHistoryFormGroupContent = {
  id: FormControl<PurchaseHistoryFormRawValue['id'] | NewPurchaseHistory['id']>;
  plan: FormControl<PurchaseHistoryFormRawValue['plan']>;
  purchaseDate: FormControl<PurchaseHistoryFormRawValue['purchaseDate']>;
  client: FormControl<PurchaseHistoryFormRawValue['client']>;
};

export type PurchaseHistoryFormGroup = FormGroup<PurchaseHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PurchaseHistoryFormService {
  createPurchaseHistoryFormGroup(purchaseHistory: PurchaseHistoryFormGroupInput = { id: null }): PurchaseHistoryFormGroup {
    const purchaseHistoryRawValue = this.convertPurchaseHistoryToPurchaseHistoryRawValue({
      ...this.getFormDefaults(),
      ...purchaseHistory,
    });
    return new FormGroup<PurchaseHistoryFormGroupContent>({
      id: new FormControl(
        { value: purchaseHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      plan: new FormControl(purchaseHistoryRawValue.plan),
      purchaseDate: new FormControl(purchaseHistoryRawValue.purchaseDate),
      client: new FormControl(purchaseHistoryRawValue.client),
    });
  }

  getPurchaseHistory(form: PurchaseHistoryFormGroup): IPurchaseHistory | NewPurchaseHistory {
    return this.convertPurchaseHistoryRawValueToPurchaseHistory(
      form.getRawValue() as PurchaseHistoryFormRawValue | NewPurchaseHistoryFormRawValue
    );
  }

  resetForm(form: PurchaseHistoryFormGroup, purchaseHistory: PurchaseHistoryFormGroupInput): void {
    const purchaseHistoryRawValue = this.convertPurchaseHistoryToPurchaseHistoryRawValue({ ...this.getFormDefaults(), ...purchaseHistory });
    form.reset(
      {
        ...purchaseHistoryRawValue,
        id: { value: purchaseHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PurchaseHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      purchaseDate: currentTime,
    };
  }

  private convertPurchaseHistoryRawValueToPurchaseHistory(
    rawPurchaseHistory: PurchaseHistoryFormRawValue | NewPurchaseHistoryFormRawValue
  ): IPurchaseHistory | NewPurchaseHistory {
    return {
      ...rawPurchaseHistory,
      purchaseDate: dayjs(rawPurchaseHistory.purchaseDate, DATE_TIME_FORMAT),
    };
  }

  private convertPurchaseHistoryToPurchaseHistoryRawValue(
    purchaseHistory: IPurchaseHistory | (Partial<NewPurchaseHistory> & PurchaseHistoryFormDefaults)
  ): PurchaseHistoryFormRawValue | PartialWithRequiredKeyOf<NewPurchaseHistoryFormRawValue> {
    return {
      ...purchaseHistory,
      purchaseDate: purchaseHistory.purchaseDate ? purchaseHistory.purchaseDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
