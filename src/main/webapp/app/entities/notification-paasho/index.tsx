import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NotificationPaasho from './notification-paasho';
import NotificationPaashoDetail from './notification-paasho-detail';
import NotificationPaashoUpdate from './notification-paasho-update';
import NotificationPaashoDeleteDialog from './notification-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NotificationPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NotificationPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NotificationPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={NotificationPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NotificationPaashoDeleteDialog} />
  </>
);

export default Routes;
