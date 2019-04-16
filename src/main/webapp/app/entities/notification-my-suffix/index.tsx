import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NotificationMySuffix from './notification-my-suffix';
import NotificationMySuffixDetail from './notification-my-suffix-detail';
import NotificationMySuffixUpdate from './notification-my-suffix-update';
import NotificationMySuffixDeleteDialog from './notification-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotificationMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotificationMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotificationMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={NotificationMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NotificationMySuffixDeleteDialog} />
  </>
);

export default Routes;
