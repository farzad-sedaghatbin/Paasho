import { IUser1MySuffix } from 'app/shared/model/user-1-my-suffix.model';

export const enum NotificationType {
  NEWS = 'NEWS'
}

export const enum NotificationStatus {
  READ = 'READ'
}

export interface INotificationMySuffix {
  id?: number;
  description?: string;
  type?: NotificationType;
  status?: NotificationStatus;
  notifications?: IUser1MySuffix[];
}

export const defaultValue: Readonly<INotificationMySuffix> = {};
