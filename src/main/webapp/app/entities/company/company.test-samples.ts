import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
};

export const sampleWithPartialData: ICompany = {
  id: 99996,
  companyType: 'Hawaii',
  companyPicture: 'sensor',
  companyCreationDate: dayjs('2023-04-13T08:45'),
  companyLocationAddress: 'Borders',
};

export const sampleWithFullData: ICompany = {
  id: 69135,
  companyName: 'Belize Estate',
  companyType: 'Devolved',
  companyDescription: 'Australia',
  companyPicture: 'Technician',
  companyCreationDate: dayjs('2023-04-13T04:52'),
  companyLocationAddress: 'Botswana 1080p',
  companyActivityDomaine: 'RAM withdrawal',
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
