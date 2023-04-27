import dayjs from 'dayjs/esm';
import { IPlan } from 'app/entities/plan/plan.model';
import { IAddress } from 'app/entities/address/address.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { Job } from 'app/entities/enumerations/job.model';

export interface IClient {
  id: number;
  phoneNumber?: string | null;
  gender?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  currentPlanOffer?: string | null;
  job?: Job | null;
  plan?: Pick<IPlan, 'id'> | null;
  address?: Pick<IAddress, 'id'> | null;
  appUser?: Pick<IAppUser, 'id'> | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
