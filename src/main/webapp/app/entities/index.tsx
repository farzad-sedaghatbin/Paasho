import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventMySuffix from './event-my-suffix';
import User1MySuffix from './user-1-my-suffix';
import ContactMySuffix from './contact-my-suffix';
import CategoryMySuffix from './category-my-suffix';
import NotificationMySuffix from './notification-my-suffix';
import SettingMySuffix from './setting-my-suffix';
import FactorMySuffix from './factor-my-suffix';
import CommentMySuffix from './comment-my-suffix';
import MediaMySuffix from './media-my-suffix';
import RatingMySuffix from './rating-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/event-my-suffix`} component={EventMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/user-1-my-suffix`} component={User1MySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/contact-my-suffix`} component={ContactMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/category-my-suffix`} component={CategoryMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/notification-my-suffix`} component={NotificationMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/setting-my-suffix`} component={SettingMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/factor-my-suffix`} component={FactorMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/comment-my-suffix`} component={CommentMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/media-my-suffix`} component={MediaMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/rating-my-suffix`} component={RatingMySuffix} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
