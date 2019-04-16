export const enum SettingKey {
  APP_VERSION = 'APP_VERSION'
}

export interface ISettingPaasho {
  id?: number;
  key?: SettingKey;
  value?: string;
}

export const defaultValue: Readonly<ISettingPaasho> = {};
