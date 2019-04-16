import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MediaPaasho from './media-paasho';
import MediaPaashoDetail from './media-paasho-detail';
import MediaPaashoUpdate from './media-paasho-update';
import MediaPaashoDeleteDialog from './media-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MediaPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MediaPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MediaPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={MediaPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MediaPaashoDeleteDialog} />
  </>
);

export default Routes;
