import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAddress, NewAddress } from '../address.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddress for edit and NewAddressFormGroupInput for create.
 */
type AddressFormGroupInput = IAddress | PartialWithRequiredKeyOf<NewAddress>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAddress | NewAddress> = Omit<T, 'zipCode'> & {
  zipCode?: string | null;
};

type AddressFormRawValue = FormValueOf<IAddress>;

type NewAddressFormRawValue = FormValueOf<NewAddress>;

type AddressFormDefaults = Pick<NewAddress, 'id' | 'zipCode'>;

type AddressFormGroupContent = {
  id: FormControl<AddressFormRawValue['id'] | NewAddress['id']>;
  country: FormControl<AddressFormRawValue['country']>;
  city: FormControl<AddressFormRawValue['city']>;
  zipCode: FormControl<AddressFormRawValue['zipCode']>;
};

export type AddressFormGroup = FormGroup<AddressFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressFormService {
  createAddressFormGroup(address: AddressFormGroupInput = { id: null }): AddressFormGroup {
    const addressRawValue = this.convertAddressToAddressRawValue({
      ...this.getFormDefaults(),
      ...address,
    });
    return new FormGroup<AddressFormGroupContent>({
      id: new FormControl(
        { value: addressRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      country: new FormControl(addressRawValue.country),
      city: new FormControl(addressRawValue.city),
      zipCode: new FormControl(addressRawValue.zipCode),
    });
  }

  getAddress(form: AddressFormGroup): IAddress | NewAddress {
    return this.convertAddressRawValueToAddress(form.getRawValue() as AddressFormRawValue | NewAddressFormRawValue);
  }

  resetForm(form: AddressFormGroup, address: AddressFormGroupInput): void {
    const addressRawValue = this.convertAddressToAddressRawValue({ ...this.getFormDefaults(), ...address });
    form.reset(
      {
        ...addressRawValue,
        id: { value: addressRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddressFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      zipCode: currentTime,
    };
  }

  private convertAddressRawValueToAddress(rawAddress: AddressFormRawValue | NewAddressFormRawValue): IAddress | NewAddress {
    return {
      ...rawAddress,
      zipCode: dayjs(rawAddress.zipCode, DATE_TIME_FORMAT),
    };
  }

  private convertAddressToAddressRawValue(
    address: IAddress | (Partial<NewAddress> & AddressFormDefaults)
  ): AddressFormRawValue | PartialWithRequiredKeyOf<NewAddressFormRawValue> {
    return {
      ...address,
      zipCode: address.zipCode ? address.zipCode.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
