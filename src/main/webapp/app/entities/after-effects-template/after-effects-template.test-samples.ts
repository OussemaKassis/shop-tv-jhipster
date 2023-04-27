import dayjs from 'dayjs/esm';

import { IAfterEffectsTemplate, NewAfterEffectsTemplate } from './after-effects-template.model';

export const sampleWithRequiredData: IAfterEffectsTemplate = {
  id: 13397,
};

export const sampleWithPartialData: IAfterEffectsTemplate = {
  id: 2106,
  templateDescription: 'alarm Synchronised',
  templateRating: dayjs('2023-04-12T10:29'),
  templateVisibleFor: 'impactful',
};

export const sampleWithFullData: IAfterEffectsTemplate = {
  id: 79777,
  templateName: 'Plastic',
  templateDuration: 'copy',
  templateDescription: 'Account content Philippine',
  templateRating: dayjs('2023-04-12T12:00'),
  templateActive: true,
  templateType: 'Marketing Toys',
  templateExpectedSize: 'Creative Madagascar matrix',
  templateCount: 'Sweden',
  templateVisibleFor: 'calculate sensor',
  ratio: 'Fresh sexy District',
  previewUrl: 'index Track Marketing',
};

export const sampleWithNewData: NewAfterEffectsTemplate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
