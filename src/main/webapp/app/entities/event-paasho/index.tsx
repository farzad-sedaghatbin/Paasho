import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EventPaasho from './event-paasho';
import EventPaashoDetail from './event-paasho-detail';
import EventPaashoUpdate from './event-paasho-update';
import EventPaashoDeleteDialog from './event-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EventPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EventPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EventPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={EventPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EventPaashoDeleteDialog} />
  </>
);

export default Routes;
