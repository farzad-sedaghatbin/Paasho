import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';

export interface ICategoryPaasho {
  id?: number;
  icon?: string;
  name?: string;
  code?: string;
  categories?: IUser1Paasho[];
  events?: IEventPaasho[];
}

export const defaultValue: Readonly<ICategoryPaasho> = {};
