import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore

// prettier-ignore
import contact, {
  ContactPaashoState
} from 'app/entities/contact-paasho/contact-paasho.reducer';
// prettier-ignore
import category, {
  CategoryPaashoState
} from 'app/entities/category-paasho/category-paasho.reducer';
// prettier-ignore
import notification, {
  NotificationPaashoState
} from 'app/entities/notification-paasho/notification-paasho.reducer';
// prettier-ignore
import setting, {
  SettingPaashoState
} from 'app/entities/setting-paasho/setting-paasho.reducer';
// prettier-ignore
import media, {
  MediaPaashoState
} from 'app/entities/media-paasho/media-paasho.reducer';
// prettier-ignore
import rating, {
  RatingPaashoState
} from 'app/entities/rating-paasho/rating-paasho.reducer';

// prettier-ignore
import titles, {
  TitlesState
} from 'app/entities/titles/titles.reducer';
// prettier-ignore

// prettier-ignore
import comment, {
  CommentPaashoState
} from 'app/entities/comment-paasho/comment-paasho.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';

// prettier-ignore
import factor, {
  FactorPaashoState
} from 'app/entities/factor-paasho/factor-paasho.reducer';
// prettier-ignore
export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly event: EventPaashoState;
  readonly contact: ContactPaashoState;
  readonly category: CategoryPaashoState;
  readonly notification: NotificationPaashoState;
  readonly setting: SettingPaashoState;
  readonly factor: FactorPaashoState;
  readonly comment: CommentPaashoState;
  readonly media: MediaPaashoState;
  readonly rating: RatingPaashoState;
  readonly titles: TitlesState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  event,
  contact,
  category,
  notification,
  setting,
  factor,
  comment,
  media,
  rating,
  titles,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
