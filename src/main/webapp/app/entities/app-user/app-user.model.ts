import { ICompany } from 'app/entities/company/company.model';
import { IUser } from 'app/entities/user/user.model';
import { Role } from 'app/entities/enumerations/role.model';

export interface IAppUser {
  id: number;
  avatarUrl?: string | null;
  status?: string | null;
  role?: Role | null;
  company?: Pick<ICompany, 'id'> | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewAppUser = Omit<IAppUser, 'id'> & { id: null };
