export interface IPlanOptions {
  id: number;
  aeTemplateLimit?: string | null;
  videoSubmittionLimit?: string | null;
  emojis?: boolean | null;
  storage?: string | null;
}

export type NewPlanOptions = Omit<IPlanOptions, 'id'> & { id: null };
