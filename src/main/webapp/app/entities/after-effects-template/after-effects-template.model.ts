import dayjs from 'dayjs/esm';
import { ICompany } from 'app/entities/company/company.model';
import { ICategory } from 'app/entities/category/category.model';
import { IAfterEffectsTemplateAssets } from 'app/entities/after-effects-template-assets/after-effects-template-assets.model';

export interface IAfterEffectsTemplate {
  id: number;
  templateName?: string | null;
  templateDuration?: string | null;
  templateDescription?: string | null;
  templateRating?: dayjs.Dayjs | null;
  templateActive?: boolean | null;
  templateType?: string | null;
  templateExpectedSize?: string | null;
  templateCount?: string | null;
  templateVisibleFor?: string | null;
  ratio?: string | null;
  previewUrl?: string | null;
  company?: Pick<ICompany, 'id'> | null;
  category?: Pick<ICategory, 'id'> | null;
  afterEffectsTemplateAssets?: Pick<IAfterEffectsTemplateAssets, 'id'> | null;
}

export type NewAfterEffectsTemplate = Omit<IAfterEffectsTemplate, 'id'> & { id: null };
