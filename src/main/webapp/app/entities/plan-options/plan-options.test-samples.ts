import { IPlanOptions, NewPlanOptions } from './plan-options.model';

export const sampleWithRequiredData: IPlanOptions = {
  id: 17791,
};

export const sampleWithPartialData: IPlanOptions = {
  id: 8172,
  videoSubmittionLimit: 'mesh Programmable',
  emojis: true,
};

export const sampleWithFullData: IPlanOptions = {
  id: 42494,
  aeTemplateLimit: 'Specialist contingency groupware',
  videoSubmittionLimit: 'Assurance program primary',
  emojis: true,
  storage: 'Bike',
};

export const sampleWithNewData: NewPlanOptions = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
