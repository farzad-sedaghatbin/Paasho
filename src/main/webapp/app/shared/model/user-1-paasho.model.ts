import { IContactPaasho } from 'app/shared/model/contact-paasho.model';
import { IRatingPaasho } from 'app/shared/model/rating-paasho.model';
import { ICommentPaasho } from 'app/shared/model/comment-paasho.model';
import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';
import { IFactorPaasho } from 'app/shared/model/factor-paasho.model';
import { IEventPaasho } from 'app/shared/model/event-paasho.model';

export const enum GenderType {
  FEMALE = 'FEMALE',
  MALE = 'MALE'
}

export interface IUser1Paasho {
  id?: number;
  name?: string;
  lastName?: string;
  gender?: GenderType;
  birthDate?: string;
  contacts?: IContactPaasho[];
  rates?: IRatingPaasho[];
  comments?: ICommentPaasho[];
  favorits?: ICategoryPaasho[];
  eventId?: number;
  notificationId?: number;
  factors?: IFactorPaasho[];
  events?: IEventPaasho[];
}

export const defaultValue: Readonly<IUser1Paasho> = {};
