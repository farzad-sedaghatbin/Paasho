export const enum SettingKey {
  APP_VERSION = 'APP_VERSION'
}

export interface ISettingMySuffix {
  id?: number;
  key?: SettingKey;
  value?: string;
}

export const defaultValue: Readonly<ISettingMySuffix> = {};
