import dayjs from 'dayjs/esm';

export interface ICompany {
  id: number;
  companyName?: string | null;
  companyType?: string | null;
  companyDescription?: string | null;
  companyPicture?: string | null;
  companyCreationDate?: dayjs.Dayjs | null;
  companyLocationAddress?: string | null;
  companyActivityDomaine?: string | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
