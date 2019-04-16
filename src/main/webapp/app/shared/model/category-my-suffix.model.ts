import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
import { IEventMySuffix } from 'app/shared/model/event-my-suffix.model';

export interface ICategoryMySuffix {
  id?: number;
  icon?: string;
  name?: string;
  code?: string;
  categories?: IUser1MySuffix[];
  categories?: IEventMySuffix[];
}

export const defaultValue: Readonly<ICategoryMySuffix> = {};
