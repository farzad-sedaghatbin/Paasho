import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import User from './user';
import UserDetail from './user-detail';
import UserUpdate from './user-update';
import UserDeleteDialog from './user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserDetail} />
      <ErrorBoundaryRoute path={match.url} component={User} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserDeleteDialog} />
  </>
);

export default Routes;
