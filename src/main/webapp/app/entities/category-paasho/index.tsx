import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CategoryPaasho from './category-paasho';
import CategoryPaashoDetail from './category-paasho-detail';
import CategoryPaashoUpdate from './category-paasho-update';
import CategoryPaashoDeleteDialog from './category-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CategoryPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CategoryPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CategoryPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CategoryPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CategoryPaashoDeleteDialog} />
  </>
);

export default Routes;
