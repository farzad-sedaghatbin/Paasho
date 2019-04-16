export const enum MediaType {
  VIDEO = 'VIDEO',
  PHOTO = 'PHOTO',
  AUDIO = 'AUDIO'
}

export interface IMediaMySuffix {
  id?: number;
  path?: string;
  type?: MediaType;
  eventId?: number;
}

export const defaultValue: Readonly<IMediaMySuffix> = {};
