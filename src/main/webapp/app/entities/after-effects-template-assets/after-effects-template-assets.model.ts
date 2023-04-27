export interface IAfterEffectsTemplateAssets {
  id: number;
  assetName?: string | null;
  assetType?: string | null;
}

export type NewAfterEffectsTemplateAssets = Omit<IAfterEffectsTemplateAssets, 'id'> & { id: null };
