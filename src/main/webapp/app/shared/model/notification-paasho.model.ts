import { IUser1Paasho } from 'app/shared/model/user-1-paasho.model';

export const enum NotificationType {
  NEWS = 'NEWS'
}

export const enum NotificationStatus {
  READ = 'READ'
}

export interface INotificationPaasho {
  id?: number;
  description?: string;
  type?: NotificationType;
  status?: NotificationStatus;
  users?: IUser1Paasho[];
}

export const defaultValue: Readonly<INotificationPaasho> = {};
