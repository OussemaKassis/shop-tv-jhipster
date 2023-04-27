import dayjs from 'dayjs/esm';

import { Job } from 'app/entities/enumerations/job.model';

import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 5204,
};

export const sampleWithPartialData: IClient = {
  id: 59874,
  phoneNumber: 'primary Virginia Concrete',
  dateOfBirth: dayjs('2023-04-12T12:14'),
};

export const sampleWithFullData: IClient = {
  id: 57208,
  phoneNumber: 'Sleek',
  gender: 'Human Corners Bedfordshire',
  dateOfBirth: dayjs('2023-04-12T23:43'),
  currentPlanOffer: 'Human card',
  job: Job['UI_UX'],
};

export const sampleWithNewData: NewClient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
