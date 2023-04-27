import { Role } from 'app/entities/enumerations/role.model';

import { IAppUser, NewAppUser } from './app-user.model';

export const sampleWithRequiredData: IAppUser = {
  id: 52920,
};

export const sampleWithPartialData: IAppUser = {
  id: 26416,
  status: 'Rest',
  role: Role['CLIENT'],
};

export const sampleWithFullData: IAppUser = {
  id: 78037,
  avatarUrl: 'integrated Outdoors',
  status: 'Spring',
  role: Role['CLIENT'],
};

export const sampleWithNewData: NewAppUser = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
