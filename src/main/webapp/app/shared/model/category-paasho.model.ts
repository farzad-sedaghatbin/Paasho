import { IEventPaasho } from 'app/shared/model/event-paasho.model';
import { IUser } from 'app/shared/model/user.model';

export interface ICategoryPaasho {
  id?: number;
  icon?: string;
  name?: string;
  code?: string;
  events?: IEventPaasho[];
  users?: IUser[];
}

export const defaultValue: Readonly<ICategoryPaasho> = {};
