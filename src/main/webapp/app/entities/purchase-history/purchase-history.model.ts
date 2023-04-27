import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';

export interface IPurchaseHistory {
  id: number;
  plan?: string | null;
  purchaseDate?: dayjs.Dayjs | null;
  client?: Pick<IClient, 'id'> | null;
}

export type NewPurchaseHistory = Omit<IPurchaseHistory, 'id'> & { id: null };
