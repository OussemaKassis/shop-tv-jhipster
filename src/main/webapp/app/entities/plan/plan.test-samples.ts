import { IPlan, NewPlan } from './plan.model';

export const sampleWithRequiredData: IPlan = {
  id: 5214,
};

export const sampleWithPartialData: IPlan = {
  id: 73825,
  price: 'European cross-platform Small',
};

export const sampleWithFullData: IPlan = {
  id: 54244,
  planName: 'digital Versatile silver',
  price: 'Bedfordshire EXE',
};

export const sampleWithNewData: NewPlan = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
