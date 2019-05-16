export interface ICommentPaasho {
  id?: number;
  description?: string;
  userLogin?: string;
  userId?: number;
  eventId?: number;
}

export const defaultValue: Readonly<ICommentPaasho> = {};
