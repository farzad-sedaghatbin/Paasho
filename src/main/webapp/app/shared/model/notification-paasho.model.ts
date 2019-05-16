import { IUser } from 'app/shared/model/user.model';

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
  users?: IUser[];
}

export const defaultValue: Readonly<INotificationPaasho> = {};
