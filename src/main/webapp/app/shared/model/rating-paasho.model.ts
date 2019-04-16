export interface IRatingPaasho {
  id?: number;
  rate?: number;
  eventId?: number;
  userId?: number;
}

export const defaultValue: Readonly<IRatingPaasho> = {};
