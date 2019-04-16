import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import User1Paasho from './user-1-paasho';
import User1PaashoDetail from './user-1-paasho-detail';
import User1PaashoUpdate from './user-1-paasho-update';
import User1PaashoDeleteDialog from './user-1-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={User1PaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={User1PaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={User1PaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={User1Paasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={User1PaashoDeleteDialog} />
  </>
);

export default Routes;
