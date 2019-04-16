import { Moment } from 'moment';
import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';
import { IMediaMySuffix } from 'app/shared/model/media-my-suffix.model';
import { ICategoryMySuffix } from 'app/shared/model/category-my-suffix.model';
import { IRatingMySuffix } from 'app/shared/model/rating-my-suffix.model';
import { IFactorMySuffix } from 'app/shared/model/factor-my-suffix.model';

export const enum PriceType {
  FREE = 'FREE',
  DUTCH = 'DUTCH',
  MONETARY = 'MONETARY'
}

export const enum EventStatus {
  OPEN = 'OPEN',
  CLOSE = 'CLOSE'
}

export interface IEventMySuffix {
  id?: number;
  eventTime?: Moment;
  description?: string;
  code?: string;
  title?: string;
  maxAge?: number;
  minAge?: number;
  priceType?: PriceType;
  status?: EventStatus;
  address?: string;
  visitCount?: number;
  latitude?: number;
  longitude?: number;
  likes?: number;
  creatorId?: number;
  participants?: IUser1MySuffix[];
  medias?: IMediaMySuffix[];
  categories?: ICategoryMySuffix[];
  rates?: IRatingMySuffix[];
  factors?: IFactorMySuffix[];
}

export const defaultValue: Readonly<IEventMySuffix> = {};
