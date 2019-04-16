import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FactorMySuffix from './factor-my-suffix';
import FactorMySuffixDetail from './factor-my-suffix-detail';
import FactorMySuffixUpdate from './factor-my-suffix-update';
import FactorMySuffixDeleteDialog from './factor-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FactorMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FactorMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FactorMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={FactorMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FactorMySuffixDeleteDialog} />
  </>
);

export default Routes;
