import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
};

export const sampleWithPartialData: ICategory = {
  id: 98520,
  categoryName: 'Sausages',
  ratio: 'Clothing navigate gold',
};

export const sampleWithFullData: ICategory = {
  id: 97365,
  categoryName: 'transparent task-force Garden',
  ratio: 'Borders bus',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
