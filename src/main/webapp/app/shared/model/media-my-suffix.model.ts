export const enum MediaType {
  VIDEO = 'VIDEO',
  PHOTO = 'PHOTO',
  AUDIO = 'AUDIO'
}

export interface IMediaMySuffix {
  id?: number;
  path?: string;
  type?: MediaType;
  idId?: number;
}

export const defaultValue: Readonly<IMediaMySuffix> = {};
