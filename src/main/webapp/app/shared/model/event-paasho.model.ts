import { Moment } from 'moment';
import { IMediaPaasho } from 'app/shared/model/media-paasho.model';
import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';
import { ICategoryPaasho } from 'app/shared/model/category-paasho.model';
import { IRatingPaasho } from 'app/shared/model/rating-paasho.model';
import { IFactorPaasho } from 'app/shared/model/factor-paasho.model';

export const enum PriceType {
  FREE = 'FREE',
  DUTCH = 'DUTCH',
  MONETARY = 'MONETARY'
}

export const enum EventStatus {
  OPEN = 'OPEN',
  CLOSE = 'CLOSE'
}

export interface IEventPaasho {
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
  filesContentType?: string;
  files?: any;
  tel?: string;
  instagram?: string;
  telegram?: string;
  capacity?: number;
  customTitle?: string;
  creatorId?: number;
  medias?: IMediaPaasho[];
  participants?: IUser1Paasho[];
  categories?: ICategoryPaasho[];
  rates?: IRatingPaasho[];
  factors?: IFactorPaasho[];
  titlesId?: number;
}

export const defaultValue: Readonly<IEventPaasho> = {};
