export const enum ContactType {
  TELEGRAM = 'TELEGRAM',
  INSTAGRAM = 'INSTAGRAM',
  WHATSAPP = 'WHATSAPP',
  EMAIL = 'EMAIL'
}

export interface IContactPaasho {
  id?: number;
  type?: ContactType;
  value?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IContactPaasho> = {};
