export interface IRatingPaasho {
  id?: number;
  rate?: number;
  eventId?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IRatingPaasho> = {};
