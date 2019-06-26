export const enum MediaType {
  VIDEO = 'VIDEO',
  PHOTO = 'PHOTO',
  AUDIO = 'AUDIO',
  AVATAR='AVATAR',
  DEFAULT_CATEGORY='DEFAULT_CATEGORY'
}

export interface IMediaPaasho {
  id?: number;
  path?: string;
  type?: MediaType;
  eventId?: number;
}

export const defaultValue: Readonly<IMediaPaasho> = {};
