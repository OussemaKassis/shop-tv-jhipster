import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IClient, NewClient } from '../client.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClient for edit and NewClientFormGroupInput for create.
 */
type ClientFormGroupInput = IClient | PartialWithRequiredKeyOf<NewClient>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IClient | NewClient> = Omit<T, 'dateOfBirth'> & {
  dateOfBirth?: string | null;
};

type ClientFormRawValue = FormValueOf<IClient>;

type NewClientFormRawValue = FormValueOf<NewClient>;

type ClientFormDefaults = Pick<NewClient, 'id' | 'dateOfBirth'>;

type ClientFormGroupContent = {
  id: FormControl<ClientFormRawValue['id'] | NewClient['id']>;
  phoneNumber: FormControl<ClientFormRawValue['phoneNumber']>;
  gender: FormControl<ClientFormRawValue['gender']>;
  dateOfBirth: FormControl<ClientFormRawValue['dateOfBirth']>;
  currentPlanOffer: FormControl<ClientFormRawValue['currentPlanOffer']>;
  job: FormControl<ClientFormRawValue['job']>;
  plan: FormControl<ClientFormRawValue['plan']>;
  address: FormControl<ClientFormRawValue['address']>;
  appUser: FormControl<ClientFormRawValue['appUser']>;
};

export type ClientFormGroup = FormGroup<ClientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientFormService {
  createClientFormGroup(client: ClientFormGroupInput = { id: null }): ClientFormGroup {
    const clientRawValue = this.convertClientToClientRawValue({
      ...this.getFormDefaults(),
      ...client,
    });
    return new FormGroup<ClientFormGroupContent>({
      id: new FormControl(
        { value: clientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      phoneNumber: new FormControl(clientRawValue.phoneNumber),
      gender: new FormControl(clientRawValue.gender),
      dateOfBirth: new FormControl(clientRawValue.dateOfBirth),
      currentPlanOffer: new FormControl(clientRawValue.currentPlanOffer),
      job: new FormControl(clientRawValue.job),
      plan: new FormControl(clientRawValue.plan),
      address: new FormControl(clientRawValue.address),
      appUser: new FormControl(clientRawValue.appUser),
    });
  }

  getClient(form: ClientFormGroup): IClient | NewClient {
    return this.convertClientRawValueToClient(form.getRawValue() as ClientFormRawValue | NewClientFormRawValue);
  }

  resetForm(form: ClientFormGroup, client: ClientFormGroupInput): void {
    const clientRawValue = this.convertClientToClientRawValue({ ...this.getFormDefaults(), ...client });
    form.reset(
      {
        ...clientRawValue,
        id: { value: clientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClientFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateOfBirth: currentTime,
    };
  }

  private convertClientRawValueToClient(rawClient: ClientFormRawValue | NewClientFormRawValue): IClient | NewClient {
    return {
      ...rawClient,
      dateOfBirth: dayjs(rawClient.dateOfBirth, DATE_TIME_FORMAT),
    };
  }

  private convertClientToClientRawValue(
    client: IClient | (Partial<NewClient> & ClientFormDefaults)
  ): ClientFormRawValue | PartialWithRequiredKeyOf<NewClientFormRawValue> {
    return {
      ...client,
      dateOfBirth: client.dateOfBirth ? client.dateOfBirth.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
