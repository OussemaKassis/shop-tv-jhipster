import dayjs from 'dayjs/esm';

export interface IAddress {
  id: number;
  country?: string | null;
  city?: string | null;
  zipCode?: dayjs.Dayjs | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
