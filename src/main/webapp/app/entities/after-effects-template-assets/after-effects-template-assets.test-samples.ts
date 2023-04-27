import { IAfterEffectsTemplateAssets, NewAfterEffectsTemplateAssets } from './after-effects-template-assets.model';

export const sampleWithRequiredData: IAfterEffectsTemplateAssets = {
  id: 38505,
};

export const sampleWithPartialData: IAfterEffectsTemplateAssets = {
  id: 43557,
  assetName: 'Practical',
};

export const sampleWithFullData: IAfterEffectsTemplateAssets = {
  id: 80472,
  assetName: 'transmit Direct intermediate',
  assetType: 'parse SDD SQL',
};

export const sampleWithNewData: NewAfterEffectsTemplateAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
