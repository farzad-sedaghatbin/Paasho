export const enum ContactType {
  TELEGRAM = 'TELEGRAM',
  INSTAGRAM = 'INSTAGRAM',
  WHATSAPP = 'WHATSAPP',
  EMAIL = 'EMAIL'
}

export interface IContactMySuffix {
  id?: number;
  type?: ContactType;
  value?: string;
  userId?: number;
}

export const defaultValue: Readonly<IContactMySuffix> = {};
