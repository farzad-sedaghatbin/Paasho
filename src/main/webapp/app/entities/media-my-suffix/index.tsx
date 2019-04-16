import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MediaMySuffix from './media-my-suffix';
import MediaMySuffixDetail from './media-my-suffix-detail';
import MediaMySuffixUpdate from './media-my-suffix-update';
import MediaMySuffixDeleteDialog from './media-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MediaMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MediaMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MediaMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={MediaMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MediaMySuffixDeleteDialog} />
  </>
);

export default Routes;
