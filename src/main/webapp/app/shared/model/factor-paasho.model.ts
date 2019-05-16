import { Moment } from 'moment';

export const enum FactorStatus {
  PAID = 'PAID',
  INIT = 'INIT'
}

export interface IFactorPaasho {
  id?: number;
  price?: number;
  code?: string;
  status?: FactorStatus;
  completeDate?: Moment;
  issueDate?: Moment;
  eventId?: number;
}

export const defaultValue: Readonly<IFactorPaasho> = {};
