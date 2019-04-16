export const enum MediaType {
  VIDEO = 'VIDEO',
  PHOTO = 'PHOTO',
  AUDIO = 'AUDIO'
}

export interface IMediaPaasho {
  id?: number;
  path?: string;
  type?: MediaType;
  eventId?: number;
}

export const defaultValue: Readonly<IMediaPaasho> = {};
