import { IContactMySuffix } from 'app/shared/model/contact-my-suffix.model';
import { IRatingMySuffix } from 'app/shared/model/rating-my-suffix.model';
import { ICommentMySuffix } from 'app/shared/model/comment-my-suffix.model';
import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
import { IFactorMySuffix } from 'app/shared/model/factor-my-suffix.model';

export const enum GenderType {
  FEMALE = 'FEMALE',
  MALE = 'MALE'
}

export interface IUser1MySuffix {
  id?: number;
  name?: string;
  lastName?: string;
  gender?: GenderType;
  birthDate?: string;
  contacts?: IContactMySuffix[];
  rates?: IRatingMySuffix[];
  comments?: ICommentMySuffix[];
  favorits?: ICategoryMySuffix[];
  idId?: number;
  idId?: number;
  idId?: number;
  ids?: IFactorMySuffix[];
}

export const defaultValue: Readonly<IUser1MySuffix> = {};
