import { IPlanOptions } from 'app/entities/plan-options/plan-options.model';

export interface IPlan {
  id: number;
  planName?: string | null;
  price?: string | null;
  planOptions?: Pick<IPlanOptions, 'id'> | null;
}

export type NewPlan = Omit<IPlan, 'id'> & { id: null };
