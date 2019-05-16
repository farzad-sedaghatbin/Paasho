import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventPaasho from './event-paasho';
import ContactPaasho from './contact-paasho';
import CategoryPaasho from './category-paasho';
import NotificationPaasho from './notification-paasho';
import SettingPaasho from './setting-paasho';
import FactorPaasho from './factor-paasho';
import CommentPaasho from './comment-paasho';
import MediaPaasho from './media-paasho';
import RatingPaasho from './rating-paasho';
import Titles from './titles';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/event-paasho`} component={EventPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/contact-paasho`} component={ContactPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/category-paasho`} component={CategoryPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/notification-paasho`} component={NotificationPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/setting-paasho`} component={SettingPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/factor-paasho`} component={FactorPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/comment-paasho`} component={CommentPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/media-paasho`} component={MediaPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/rating-paasho`} component={RatingPaasho} />
      <ErrorBoundaryRoute path={`${match.url}/titles`} component={Titles} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
