import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FactorPaasho from './factor-paasho';
import FactorPaashoDetail from './factor-paasho-detail';
import FactorPaashoUpdate from './factor-paasho-update';
import FactorPaashoDeleteDialog from './factor-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FactorPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FactorPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FactorPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={FactorPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FactorPaashoDeleteDialog} />
  </>
);

export default Routes;
