import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';

export interface ITitle {
  id?: number;
  title?: string;
  category?: ICategoryPaasho;
}

export const defaultValue: Readonly<ITitle> = {};
