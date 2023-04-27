import dayjs from 'dayjs/esm';

import { IPurchaseHistory, NewPurchaseHistory } from './purchase-history.model';

export const sampleWithRequiredData: IPurchaseHistory = {
  id: 31241,
};

export const sampleWithPartialData: IPurchaseHistory = {
  id: 64015,
  plan: 'Usability payment',
  purchaseDate: dayjs('2023-04-13T00:39'),
};

export const sampleWithFullData: IPurchaseHistory = {
  id: 45832,
  plan: 'Account Antarctica Product',
  purchaseDate: dayjs('2023-04-13T03:06'),
};

export const sampleWithNewData: NewPurchaseHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
