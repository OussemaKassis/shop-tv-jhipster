export interface ICategory {
  id: number;
  categoryName?: string | null;
  ratio?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
