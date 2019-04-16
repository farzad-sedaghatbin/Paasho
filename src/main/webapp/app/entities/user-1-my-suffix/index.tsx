import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import User1MySuffix from './user-1-my-suffix';
import User1MySuffixDetail from './user-1-my-suffix-detail';
import User1MySuffixUpdate from './user-1-my-suffix-update';
import User1MySuffixDeleteDialog from './user-1-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={User1MySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={User1MySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={User1MySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={User1MySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={User1MySuffixDeleteDialog} />
  </>
);

export default Routes;
