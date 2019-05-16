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
import event, {
  EventMySuffixState
} from 'app/entities/event-my-suffix/event-my-suffix.reducer';
// prettier-ignore
import user1, {
  User1MySuffixState
} from 'app/entities/user-1-my-suffix/user-1-my-suffix.reducer';
// prettier-ignore
import contact, {
  ContactMySuffixState
} from 'app/entities/contact-my-suffix/contact-my-suffix.reducer';
// prettier-ignore
import category, {
  CategoryMySuffixState
} from 'app/entities/category-my-suffix/category-my-suffix.reducer';
// prettier-ignore
import notification, {
  NotificationMySuffixState
} from 'app/entities/notification-my-suffix/notification-my-suffix.reducer';
// prettier-ignore
import setting, {
  SettingMySuffixState
} from 'app/entities/setting-my-suffix/setting-my-suffix.reducer';
// prettier-ignore
import factor, {
  FactorMySuffixState
} from 'app/entities/factor-my-suffix/factor-my-suffix.reducer';
// prettier-ignore
import comment, {
  CommentMySuffixState
} from 'app/entities/comment-my-suffix/comment-my-suffix.reducer';
// prettier-ignore
import media, {
  MediaMySuffixState
} from 'app/entities/media-my-suffix/media-my-suffix.reducer';
// prettier-ignore
import rating, {
  RatingMySuffixState
} from 'app/entities/rating-my-suffix/rating-my-suffix.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
// prettier-ignore
import user1, {
  User1PaashoState
} from 'app/entities/user-1-paasho/user-1-paasho.reducer';
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
import factor, {
  FactorPaashoState
} from 'app/entities/factor-paasho/factor-paasho.reducer';
// prettier-ignore
import comment, {
  CommentPaashoState
} from 'app/entities/comment-paasho/comment-paasho.reducer';
// prettier-ignore
import media, {
  MediaPaashoState
} from 'app/entities/media-paasho/media-paasho.reducer';
// prettier-ignore
import rating, {
  RatingPaashoState
} from 'app/entities/rating-paasho/rating-paasho.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
// prettier-ignore
import titles, {
  TitlesState
} from 'app/entities/titles/titles.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
// prettier-ignore
import user1, {
  User1PaashoState
} from 'app/entities/user-1-paasho/user-1-paasho.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
// prettier-ignore
import event, {
  EventPaashoState
} from 'app/entities/event-paasho/event-paasho.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

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
  readonly event: EventMySuffixState;
  readonly user1: User1MySuffixState;
  readonly contact: ContactMySuffixState;
  readonly category: CategoryMySuffixState;
  readonly notification: NotificationMySuffixState;
  readonly setting: SettingMySuffixState;
  readonly factor: FactorMySuffixState;
  readonly comment: CommentMySuffixState;
  readonly media: MediaMySuffixState;
  readonly rating: RatingMySuffixState;
  readonly event: EventPaashoState;
  readonly user1: User1PaashoState;
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
  user1,
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
