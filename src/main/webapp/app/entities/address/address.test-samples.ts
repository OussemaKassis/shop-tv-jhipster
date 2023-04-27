import dayjs from 'dayjs/esm';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 34483,
  country: 'Puerto Rico',
  city: 'Paucekport',
};

export const sampleWithFullData: IAddress = {
  id: 14508,
  country: 'Trinidad and Tobago',
  city: 'Lafayette',
  zipCode: dayjs('2023-04-13T07:50'),
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
