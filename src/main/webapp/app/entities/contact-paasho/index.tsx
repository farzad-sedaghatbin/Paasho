import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ContactPaasho from './contact-paasho';
import ContactPaashoDetail from './contact-paasho-detail';
import ContactPaashoUpdate from './contact-paasho-update';
import ContactPaashoDeleteDialog from './contact-paasho-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactPaashoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactPaashoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ContactPaasho} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ContactPaashoDeleteDialog} />
  </>
);

export default Routes;
